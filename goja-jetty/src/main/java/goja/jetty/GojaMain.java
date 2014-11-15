/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.jetty;

import goja.GojaConfig;
import goja.jetty.config.GojaServerConfig;

import static goja.jetty.ServerConfigPool.DEFAULT_PORT;
import static goja.jetty.ServerConfigPool.PORT;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-11-15 17:13
 * @since JDK 1.6
 */
public class GojaMain {

    public static void main(String... anArgs) throws Exception {
        // Initialization
        GojaConfig.getConfigProps();
        new GojaMain().start();
    }


    private final GojaServer server;

    public GojaMain() {
        server = new GojaServer(GojaServerConfig.Factory.newDevelopmentConfig("happy", GojaConfig.getPropertyToInt(PORT, DEFAULT_PORT), "localhost"));
    }

    public void start() throws Exception {
        server.start();
        server.join();
    }
}
