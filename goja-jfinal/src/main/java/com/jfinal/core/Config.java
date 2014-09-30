/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package com.jfinal.core;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.IPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class Config {

    private static final Logger logger = LoggerFactory.getLogger(Config.class);


    private static final Constants    constants    = new Constants();
    private static final Routes       routes       = new Routes() {
        public void config() {
        }
    };
    private static final Plugins      plugins      = new Plugins();
    private static final Interceptors interceptors = new Interceptors();
    private static final Handlers     handlers     = new Handlers();

    // prevent new Config();
    private Config() {
    }

    /*
     * Config order: constant, route, plugin, interceptor, handler
     */
    static void configJFinal(JFinalConfig jfinalConfig) {
        jfinalConfig.configConstant(constants);
        initLoggerFactory();
        jfinalConfig.configRoute(routes);
        jfinalConfig.configPlugin(plugins);
        startPlugins();    // very important!!!
        jfinalConfig.configInterceptor(interceptors);
        jfinalConfig.configHandler(handlers);
    }

    public static Constants getConstants() {
        return constants;
    }

    public static final Routes getRoutes() {
        return routes;
    }

    public static final Plugins getPlugins() {
        return plugins;
    }

    public static final Interceptors getInterceptors() {
        return interceptors;
    }

    public static Handlers getHandlers() {
        return handlers;
    }

    private static void startPlugins() {
        List<IPlugin> pluginList = plugins.getPluginList();
        if (pluginList != null) {
            for (IPlugin plugin : pluginList) {
                try {
                    // process ActiveRecordPlugin devMode
                    if (plugin instanceof com.jfinal.plugin.activerecord.ActiveRecordPlugin) {
                        com.jfinal.plugin.activerecord.ActiveRecordPlugin arp = (com.jfinal.plugin.activerecord.ActiveRecordPlugin) plugin;
                        if (arp.getDevMode() == null)
                            arp.setDevMode(constants.getDevMode());
                    }

                    boolean success = plugin.start();
                    if (!success) {
                        String message = "Plugin start error: " + plugin.getClass().getName();
                        logger.error(message);
                        throw new RuntimeException(message);
                    }
                } catch (Exception e) {
                    String message = "Plugin start error: " + plugin.getClass().getName() + ". \n" + e.getMessage();
                    logger.error(message, e);
                    throw new RuntimeException(message, e);
                }
            }
        }
    }

    private static void initLoggerFactory() {
        JFinalFilter.initLogger();
    }
}
