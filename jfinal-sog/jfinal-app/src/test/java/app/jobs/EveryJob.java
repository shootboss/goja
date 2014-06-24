/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package app.jobs;

import japp.jobs.Every;
import japp.jobs.Job;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-06-24 22:55
 * @since JDK 1.6
 */
@Every("1s")
public class EveryJob extends Job<String> {
    @Override
    public String doJobWithResult() throws Exception {
        System.out.println("on sencong with run.");
        return super.doJobWithResult();
    }
}
