/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package japp.jobs;

import app.jobs.EveryJob;
import japp.JApp;
import japp.init.ConfigProperties;
import japp.init.ctxbox.ClassFinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JobsPluginTest {

    static JobsPlugin jobsPlugin;


    @Before
    public void setUp() throws Exception {
        ClassFinder.findWithTest();
        ConfigProperties.getConfigProps();
        JApp.configuration = ConfigProperties.getConfigProps();
        jobsPlugin = new JobsPlugin();
    }

    @Test
    public void testApplicationStart() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {

                jobsPlugin.start();
            }
        }).start();
        Thread.currentThread().sleep(1000000);
    }

    @Test
    public void testNowRun() throws Exception {
        new EveryJob().run();

    }

    @After
    public void tearDown() throws Exception {
        jobsPlugin.stop();

    }
}