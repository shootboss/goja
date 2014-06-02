/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package japp.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.status.InfoStatus;
import ch.qos.logback.core.status.StatusManager;
import japp.JApp;
import japp.init.ConfigProperties;
import japp.init.InitConst;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-06-02 21:13
 * @since JDK 1.6
 */
public class AppLogConfigurator {

    final static AppLogConfigurator SINGLETON_LOGGER = new AppLogConfigurator();

    private AppLogConfigurator() {
    }

    public static void configure(LoggerContext lc) {
        StatusManager sm = lc.getStatusManager();
        if (sm != null) {
            sm.add(new InfoStatus("Setting up default configuration.", lc));
        }
        ConsoleAppender<ILoggingEvent> ca = new ConsoleAppender<ILoggingEvent>();
        ca.setContext(lc);
        ca.setName("console");

        final RollingFileAppender rfa = new RollingFileAppender();
        final String filename = ConfigProperties.getProperty(InitConst.LOGGER_PATH, "../logs/" + JApp.appName + ".log");
        rfa.setFile(filename);
        final TimeBasedRollingPolicy rollingPolicy = new TimeBasedRollingPolicy();
        rollingPolicy.setParent(rfa);
        rollingPolicy.setFileNamePattern("../logs/" + JApp.appName + ".%d{yyyy-MM-dd}.log");
        rollingPolicy.setContext(lc);
        rfa.setRollingPolicy(rollingPolicy);
        rfa.setContext(lc);
        rfa.setName("app_log_file");

        PatternLayoutEncoder pl = new PatternLayoutEncoder();
        pl.setContext(lc);
        pl.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        pl.start();

        ca.setEncoder(pl);
        ca.start();

        rfa.setEncoder(pl);
        rfa.start();


        Logger rootLogger = lc.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.INFO);
        rootLogger.addAppender(ca);
        rootLogger.addAppender(rfa);

        Logger appLogger = lc.getLogger("app");
        appLogger.setLevel(Level.toLevel(ConfigProperties.getProperty(InitConst.LOGGER_LEVEL), Level.INFO));
        appLogger.addAppender(ca);
        appLogger.addAppender(rfa);
    }

    public static void configureDefaultContext() {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        configure(lc);
    }
}
