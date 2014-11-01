package com.jfinal.plugin.activerecord;


import com.jfinal.plugin.activerecord.util.SqlFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * SqlReporter.
 */
public class SqlReporter implements InvocationHandler {

    private final Connection conn;

    private static final Logger logger = LoggerFactory.getLogger(SqlReporter.class);

    SqlReporter(Connection conn) {
        this.conn = conn;
    }


    @SuppressWarnings("rawtypes")
    Connection getConnection() {
        Class clazz = conn.getClass();
        return (Connection) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{Connection.class}, this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (method.getName().equals("prepareStatement")) {
                /* # edit by sogyf. */
                /* @description: add sql format print. */
                if (logger.isDebugEnabled() && DbKit.getConfig().isShowSql()) {
                    logger.debug("The Exec Sql is: \r\n {} ", SqlFormatter.format(String.valueOf(args[0])));
                }
                /* # end edited. */

            }
            return method.invoke(conn, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }
}




