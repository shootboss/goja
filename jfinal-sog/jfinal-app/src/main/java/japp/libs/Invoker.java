/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package japp.libs;

import japp.JApp;
import japp.exceptions.UnexpectedException;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-06-02 23:39
 * @since JDK 1.6
 */
public class Invoker {


    /**
     * The class/method that will be invoked by the current operation
     */
    public static class InvocationContext {

        public static ThreadLocal<InvocationContext> current = new ThreadLocal<InvocationContext>();
        private final List<Annotation> annotations;
        private final String           invocationType;

        public static InvocationContext current() {
            return current.get();
        }

        public InvocationContext(String invocationType) {
            this.invocationType = invocationType;
            this.annotations = new ArrayList<Annotation>();
        }

        public InvocationContext(String invocationType, List<Annotation> annotations) {
            this.invocationType = invocationType;
            this.annotations = annotations;
        }

        public InvocationContext(String invocationType, Annotation[] annotations) {
            this.invocationType = invocationType;
            this.annotations = Arrays.asList(annotations);
        }

        public InvocationContext(String invocationType, Annotation[]... annotations) {
            this.invocationType = invocationType;
            this.annotations = new ArrayList<Annotation>();
            for (Annotation[] some : annotations) {
                this.annotations.addAll(Arrays.asList(some));
            }
        }

        public List<Annotation> getAnnotations() {
            return annotations;
        }

        @SuppressWarnings("unchecked")
        public <T extends Annotation> T getAnnotation(Class<T> clazz) {
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().isAssignableFrom(clazz)) {
                    return (T) annotation;
                }
            }
            return null;
        }

        public <T extends Annotation> boolean isAnnotationPresent(Class<T> clazz) {
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().isAssignableFrom(clazz)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Returns the InvocationType for this invocation - Ie: A plugin can use this to
         * find out if it runs in the context of a background Job
         */
        public String getInvocationType() {
            return invocationType;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("InvocationType: ");
            builder.append(invocationType);
            builder.append(". annotations: ");
            for (Annotation annotation : annotations) {
                builder.append(annotation.toString()).append(",");
            }
            return builder.toString();
        }
    }

    /**
     * An Invocation in something to run in a Play! context
     */
    public static abstract class Invocation implements Runnable {

        /**
         * If set, monitor the time the invocation waited in the queue
         */
//        Monitor waitInQueue;

        /**
         * Override this method
         * @throws java.lang.Exception
         */
        public abstract void execute() throws Exception;


        /**
         * Needs this method to do stuff *before* init() is executed.
         * The different Invocation-implementations does a lot of stuff in init()
         * and they might do it before calling super.init()
         */
        protected void preInit() {

        }

        /**
         * Init the call (especially usefull in DEV mode to detect changes)
         */
        public boolean init() {
            Thread.currentThread().setContextClassLoader(JApp.class.getClassLoader());

            InvocationContext.current.set(getInvocationContext());
            return true;
        }


        public abstract InvocationContext getInvocationContext();

        /**
         * Things to do before an Invocation
         */
        public void before() {
            Thread.currentThread().setContextClassLoader(JApp.class.getClassLoader());
            Play.pluginCollection.beforeInvocation();
        }

        /**
         * Things to do after an Invocation.
         * (if the Invocation code has not thrown any exception)
         */
        public void after() {
            Play.pluginCollection.afterInvocation();
            LocalVariablesNamesTracer.checkEmpty(); // detect bugs ....
        }

        /**
         * Things to do when the whole invocation has succeeded (before + execute + after)
         */
        public void onSuccess() throws Exception {
            Play.pluginCollection.onInvocationSuccess();
        }

        /**
         * Things to do if the Invocation code thrown an exception
         */
        public void onException(Throwable e) {
            Play.pluginCollection.onInvocationException(e);
            if (e instanceof PlayException) {
                throw (PlayException) e;
            }
            throw new UnexpectedException(e);
        }

        /**
         * The request is suspended
         * @param suspendRequest
         */
        public void suspend(Suspend suspendRequest) {
            if (suspendRequest.task != null) {
                WaitForTasksCompletion.waitFor(suspendRequest.task, this);
            } else {
                Invoker.invoke(this, suspendRequest.timeout);
            }
        }

        /**
         * Things to do in all cases after the invocation.
         */
        public void _finally() {
            Play.pluginCollection.invocationFinally();
            InvocationContext.current.remove();
        }

        /**
         * It's time to execute.
         */
        public void run() {
            if (waitInQueue != null) {
                waitInQueue.stop();
            }
            try {
                preInit();
                if (init()) {
                    before();
                    execute();
                    after();
                    onSuccess();
                }
            } catch (Suspend e) {
                suspend(e);
                after();
            } catch (Throwable e) {
                onException(e);
            } finally {
                _finally();
            }
        }
    }
}
