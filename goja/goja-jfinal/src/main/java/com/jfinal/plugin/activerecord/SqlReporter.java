/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package com.jfinal.plugin.activerecord;

import japp.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * SqlReporter.
 */
public class SqlReporter implements InvocationHandler {

    private Connection conn;
    private static boolean loggerOn = false;

    SqlReporter(Connection conn) {
        this.conn = conn;
    }

    public static void setLogger(boolean on) {
        SqlReporter.loggerOn = on;
    }

    @SuppressWarnings("rawtypes")
    Connection getConnection() {
        Class clazz = conn.getClass();
        return (Connection) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{Connection.class}, this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (method.getName().equals("prepareStatement")) {
                String info = "Sql: " + args[0];
                if (loggerOn)
                    Logger.info(info);
                else
                    System.out.println(info);
            }
            return method.invoke(conn, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }
}




