/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package app.jobs;

import goja.job.Job;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-11-12 20:48
 * @since JDK 1.6
 */
public class ApplicationEndJob extends Job {

    @Override
    public void doJob() throws Exception {
        System.out.println("The Application End!");
        super.doJob();
    }
}
