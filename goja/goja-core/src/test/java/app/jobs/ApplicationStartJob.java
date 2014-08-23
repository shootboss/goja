/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package app.jobs;

import goja.jobs.Job;
import goja.jobs.OnApplicationStart;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-06-24 22:37
 * @since JDK 1.6
 */
@OnApplicationStart
public class ApplicationStartJob extends Job<String> {
    @Override
    public void doJob() throws Exception {
        System.out.println("xxx");
        super.doJob();
    }
}
