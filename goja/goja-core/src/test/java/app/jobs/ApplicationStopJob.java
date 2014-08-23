/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package app.jobs;

import goja.jobs.Job;
import goja.jobs.OnApplicationStop;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-06-24 22:54
 * @since JDK 1.6
 */
@OnApplicationStop
public class ApplicationStopJob extends Job<String> {
    @Override
    public void doJob() throws Exception {
        System.out.println("do Application stop job");
        super.doJob();
    }
}
