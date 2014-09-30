/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.job;

import com.google.common.collect.Lists;
import com.jfinal.plugin.IPlugin;
import goja.Goja;
import goja.GojaConfig;
import goja.Logger;
import goja.annotation.Every;
import goja.annotation.On;
import goja.annotation.OnApplicationStart;
import goja.annotation.OnApplicationStop;
import goja.exceptions.GojaException;
import goja.exceptions.UnexpectedException;
import goja.init.InitConst;
import goja.init.ctxbox.ClassBox;
import goja.init.ctxbox.ClassType;
import goja.libs.PThreadFactory;
import goja.libs.Expression;
import goja.libs.Time;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class JobsPlugin implements IPlugin {

    public static ScheduledThreadPoolExecutor executor      = null;
    public static List<Job>                   scheduledJobs = null;

    public JobsPlugin() {
        int core = Integer.parseInt(GojaConfig.getProperty(InitConst.JOB_POOL_SIZE, "10"));
        executor = new ScheduledThreadPoolExecutor(core, new PThreadFactory("goja-jobs"), new ThreadPoolExecutor.AbortPolicy());

    }


    public static <V> void scheduleForCRON(Job<V> job) {
        if (!job.getClass().isAnnotationPresent(On.class)) {
            return;
        }
        String cron = job.getClass().getAnnotation(On.class).value();
        if (cron.startsWith("cron.")) {
            cron = Goja.configuration.getProperty(cron);
        }
        cron = Expression.evaluate(cron, cron).toString();
        if (cron == null || "".equals(cron) || "never".equalsIgnoreCase(cron)) {
            Logger.info("Skipping job %s, cron expression is not defined", job.getClass().getName());
            return;
        }
        try {
            Date now = new Date();
            cron = Expression.evaluate(cron, cron).toString();
            Time.CronExpression cronExp = new Time.CronExpression(cron);
            Date nextDate = cronExp.getNextValidTimeAfter(now);
            if (nextDate == null) {
                Logger.warn("The cron expression for job %s doesn't have any match in the future, will never be executed", job.getClass().getName());
                return;
            }
            if (nextDate.equals(job.nextPlannedExecution)) {
                // Bug #13: avoid running the job twice for the same time
                // (happens when we end up running the job a few minutes before the planned time)
                Date nextInvalid = cronExp.getNextInvalidTimeAfter(nextDate);
                nextDate = cronExp.getNextValidTimeAfter(nextInvalid);
            }
            job.nextPlannedExecution = nextDate;
            executor.schedule((Callable<V>) job, nextDate.getTime() - now.getTime(), TimeUnit.MILLISECONDS);
            job.executor = executor;
        } catch (Exception ex) {
            throw new UnexpectedException(ex);
        }
    }


    @Override
    public boolean start() {
        List<Class<?>> jobs = Lists.newArrayList();
        for (Class clazz : ClassBox.getInstance().getClasses(ClassType.JOB)) {
            if (Job.class.isAssignableFrom(clazz)) {
                jobs.add(clazz);
            }
        }
        scheduledJobs = Lists.newArrayList();
        for (final Class<?> clazz : jobs) {
            // @OnApplicationStart
            if (clazz.isAnnotationPresent(OnApplicationStart.class)) {
                //check if we're going to run the job sync or async
                OnApplicationStart appStartAnnotation = clazz.getAnnotation(OnApplicationStart.class);
                if (!appStartAnnotation.async()) {
                    //run job sync
                    try {
                        Job<?> job = ((Job<?>) clazz.newInstance());
                        scheduledJobs.add(job);
                        job.run();
                        if (job.wasError) {
                            if (job.lastException != null) {
                                throw job.lastException;
                            }
                            throw new RuntimeException("@OnApplicationStart Job has failed");
                        }
                    } catch (InstantiationException e) {
                        throw new UnexpectedException("Job could not be instantiated", e);
                    } catch (IllegalAccessException e) {
                        throw new UnexpectedException("Job could not be instantiated", e);
                    } catch (Throwable ex) {
                        if (ex instanceof GojaException) {
                            throw (GojaException) ex;
                        }
                        throw new UnexpectedException(ex);
                    }
                } else {
                    //run job async
                    try {
                        Job<?> job = ((Job<?>) clazz.newInstance());
                        scheduledJobs.add(job);
                        //start running job now in the background
                        @SuppressWarnings("unchecked")
                        Callable<Job> callable = (Callable<Job>) job;
                        executor.submit(callable);
                    } catch (InstantiationException ex) {
                        throw new UnexpectedException("Cannot instanciate Job " + clazz.getName());
                    } catch (IllegalAccessException ex) {
                        throw new UnexpectedException("Cannot instanciate Job " + clazz.getName());
                    }
                }
            }

            // @On
            if (clazz.isAnnotationPresent(On.class)) {
                try {
                    Job<?> job = ((Job<?>) clazz.newInstance());
                    scheduledJobs.add(job);
                    scheduleForCRON(job);
                } catch (InstantiationException ex) {
                    throw new UnexpectedException("Cannot instanciate Job " + clazz.getName());
                } catch (IllegalAccessException ex) {
                    throw new UnexpectedException("Cannot instanciate Job " + clazz.getName());
                }
            }
            // @Every
            if (clazz.isAnnotationPresent(Every.class)) {
                try {
                    Job job = (Job) clazz.newInstance();
                    scheduledJobs.add(job);
                    String value = job.getClass().getAnnotation(Every.class).value();
                    if (value.startsWith("cron.")) {
                        value = Goja.configuration.getProperty(value);
                    }
                    value = Expression.evaluate(value, value).toString();
                    if (!"never".equalsIgnoreCase(value)) {
                        executor.scheduleWithFixedDelay(job, Time.parseDuration(value), Time.parseDuration(value), TimeUnit.SECONDS);
                    }
                } catch (InstantiationException ex) {
                    throw new UnexpectedException("Cannot instanciate Job " + clazz.getName());
                } catch (IllegalAccessException ex) {
                    throw new UnexpectedException("Cannot instanciate Job " + clazz.getName());
                }
            }
        }

        return true;
    }

    @Override
    public boolean stop() {

        List<Class> jobs = ClassBox.getInstance().getClasses(ClassType.JOB);
        if(scheduledJobs == null){
            scheduledJobs = Lists.newArrayList();
        }
        for (final Class clazz : jobs) {
            // @OnApplicationStop
            if (clazz.isAnnotationPresent(OnApplicationStop.class)) {
                try {
                    Job<?> job = ((Job<?>) clazz.newInstance());
                    scheduledJobs.add(job);
                    job.run();
                    if (job.wasError) {
                        if (job.lastException != null) {
                            throw job.lastException;
                        }
                        throw new RuntimeException("@OnApplicationStop Job has failed");
                    }
                } catch (InstantiationException e) {
                    throw new UnexpectedException("Job could not be instantiated", e);
                } catch (IllegalAccessException e) {
                    throw new UnexpectedException("Job could not be instantiated", e);
                } catch (Throwable ex) {
                    if (ex instanceof GojaException) {
                        throw (GojaException) ex;
                    }
                    throw new UnexpectedException(ex);
                }
            }
        }

        executor.shutdownNow();
        executor.getQueue().clear();
        return true;
    }
}
