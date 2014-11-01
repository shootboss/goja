/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.logging;

import ch.qos.logback.classic.AsyncAppender;
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
import goja.Goja;
import goja.GojaConfig;
import goja.init.InitConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import static goja.init.InitConst.*;

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


    private AppLogConfigurator() {
    }

    public static void configure(final LoggerContext lc) {
        StatusManager sm = lc.getStatusManager();
        if (sm != null) {
            sm.add(new InfoStatus("Setting up default configuration.", lc));
        }
        ConsoleAppender<ILoggingEvent> ca = new ConsoleAppender<ILoggingEvent>();
        ca.setContext(lc);
        ca.setName("console");

        final RollingFileAppender rfa = new RollingFileAppender();
        final String filename = GojaConfig.getProperty(LOGGER_PATH, "../logs/" + Goja.appName + ".log");
        rfa.setFile(filename);
        final TimeBasedRollingPolicy rollingPolicy = new TimeBasedRollingPolicy();
        rollingPolicy.setParent(rfa);
        rollingPolicy.setFileNamePattern(StringUtils.replace(filename, ".log", ".%d{yyyy-MM-dd}.log"));
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

        // init async loggin
        AsyncAppender asyncAppender = new AsyncAppender();
        asyncAppender.setContext(lc);
        asyncAppender.addAppender(rfa);
        asyncAppender.setQueueSize(512);
        asyncAppender.setDiscardingThreshold(0);

        final Level config_level = Level.toLevel(GojaConfig.getProperty(LOGGER_LEVEL), Level.INFO);
        final boolean mode = GojaConfig.getPropertyToBoolean(DEV_MODE, false);
        final Level default_level = mode ? Level.DEBUG : config_level;
        Logger rootLogger = lc.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(default_level);
        rootLogger.addAppender(ca);
        rootLogger.addAppender(asyncAppender);

        Logger appLogger = lc.getLogger("app");
        appLogger.setLevel(default_level);
        appLogger.addAppender(ca);
        appLogger.addAppender(asyncAppender);

        if (mode) {
            Logger gojaLogger = lc.getLogger("goja");
            gojaLogger.setLevel(Level.DEBUG);
            gojaLogger.addAppender(ca);
            Logger jfinalLogger = lc.getLogger("com.jfinal");
            jfinalLogger.setLevel(Level.DEBUG);
            jfinalLogger.addAppender(ca);
        }
    }

    public static void configureDefaultContext() {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        configure(lc);
    }
}
