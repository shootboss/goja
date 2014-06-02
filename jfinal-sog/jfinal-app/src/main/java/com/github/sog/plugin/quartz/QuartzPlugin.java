/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package com.github.sog.plugin.quartz;

import japp.Logger;
import com.github.sog.annotation.On;
import japp.init.ctxbox.ClassBox;
import japp.init.ctxbox.ClassType;
import com.jfinal.plugin.IPlugin;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.List;

import static com.google.common.base.Throwables.propagate;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class QuartzPlugin implements IPlugin {
    private static final String JOB = "job";

    private final Scheduler sched;
    private boolean autoScan = true;

    public QuartzPlugin() {
        Scheduler tmp_sched = null;
        try {
            tmp_sched = StdSchedulerFactory.getDefaultScheduler();
        } catch (SchedulerException e) {
            propagate(e);
        }
        this.sched = tmp_sched;
    }


    @Override
    public boolean start() {
        List<Class> jobClasses = ClassBox.getInstance().getClasses(ClassType.JOB);
        if (jobClasses != null && !jobClasses.isEmpty()) {
            On on;
            for (Class jobClass : jobClasses) {
                on = (On) jobClass.getAnnotation(On.class);
                if (on == null) {
                    if (!autoScan) {
                        continue;
                    }
                    Logger.warn("the job class [" + jobClass + "] not config on annotion!");
                } else {
                    if (!on.enabled()) {
                        continue;
                    }
                    String jobCronExp = on.value();
                    addJob(jobClass, jobCronExp, on.name());
                }
            }
        }
        return true;
    }

    private void addJob(Class<Job> jobClass, String jobCronExp, String jobName) {
        JobDetail job = newJob(jobClass)
                .withIdentity(jobName, jobName + "group")
                .build();
        Trigger trigger = newTrigger()
                .withIdentity(jobName, jobName + "group")
                .withSchedule(cronSchedule(jobCronExp))
                .startNow()
                .build();

        Date ft = null;
        try {
            ft = sched.scheduleJob(job, trigger);
            sched.start();
        } catch (SchedulerException e) {
            propagate(e);
        }
        if (Logger.isDebugEnabled()) {
            Logger.debug(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: "
                    + jobCronExp);
        }
    }


    @Override
    public boolean stop() {
        try {
            sched.shutdown();
        } catch (SchedulerException e) {
            propagate(e);
        }
        return true;
    }

}
