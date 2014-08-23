/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package com.jfinal.core;

import com.jfinal.config.Constants;
import com.jfinal.config.JFinalConfig;
import com.jfinal.handler.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.SessionCookieConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JFinal framework filter
 */
public final class JFinalFilter implements Filter {

    private Handler      handler;
    private String       encoding;
    private JFinalConfig jfinalConfig;
    private Constants    constants;

    private static final JFinal jfinal = JFinal.me();
    private static Logger log;

    private int contextPathLength;

    public void init(FilterConfig filterConfig) throws ServletException {
        createJFinalConfig(filterConfig.getInitParameter("configClass"));

        final ServletContext servletContext = filterConfig.getServletContext();
        if (!jfinal.init(jfinalConfig, servletContext))
            throw new RuntimeException("JFinal init error!");

        handler = jfinal.getHandler();
        constants = Config.getConstants();
        encoding = constants.getEncoding();
        jfinalConfig.afterJFinalStart();

        String contextPath = servletContext.getContextPath();
        contextPathLength = (contextPath == null || "/".equals(contextPath) ? 0 : contextPath.length());
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        request.setCharacterEncoding(encoding);
        // set cookie http only.
        SessionCookieConfig config = request.getServletContext(). getSessionCookieConfig();
        config.setHttpOnly(true);

        String target = request.getRequestURI();
        if (contextPathLength != 0)
            target = target.substring(contextPathLength);

        boolean[] isHandled = {false};
        try {
            handler.handle(target, request, response, isHandled);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                String qs = request.getQueryString();
                log.error(qs == null ? target : target + "?" + qs, e);
            }
        }

        if (!isHandled[0])
            chain.doFilter(request, response);
    }

    public void destroy() {
        jfinalConfig.beforeJFinalStop();
        jfinal.stopPlugins();
    }

    private void createJFinalConfig(String configClass) {
        if (configClass == null)
            throw new RuntimeException("Please set configClass parameter of JFinalFilter in web.xml");

        try {
            Object temp = Class.forName(configClass).newInstance();
            if (temp instanceof JFinalConfig)
                jfinalConfig = (JFinalConfig) temp;
            else
                throw new RuntimeException("Can not create instance of class: " + configClass + ". Please check the config in web.xml");
        } catch (InstantiationException e) {
            throw new RuntimeException("Can not create instance of class: " + configClass, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Can not create instance of class: " + configClass, e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found: " + configClass + ". Please config it in web.xml", e);
        }
    }

    static void initLogger() {
        log = LoggerFactory.getLogger(JFinalFilter.class);
    }
}
