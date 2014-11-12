/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package app.jobs;

import goja.annotation.OnApplicationStart;
import goja.job.Job;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-11-12 20:47
 * @since JDK 1.6
 */
@OnApplicationStart
public class ApplicationStartJob extends Job {
    @Override
    public void doJob() throws Exception {
        System.out.println("Start Event");
        super.doJob();
    }
}
