/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.test;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.util.JdbcUtils;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.druid.DruidPlugin;
import goja.Goja;
import goja.GojaConfig;
import goja.db.dialect.DB2Dialect;
import goja.db.dialect.H2Dialect;
import goja.db.dialect.OracleDialect;
import goja.db.dialect.PostgreSqlDialect;
import goja.db.dialect.Sqlite3Dialect;
import goja.exceptions.DatabaseException;
import goja.init.InitConst;
import goja.init.ctxbox.ClassFinder;
import goja.kits.Reflect;
import goja.plugins.sqlinxml.SqlInXmlPlugin;
import goja.plugins.tablebind.AutoTableBindPlugin;
import goja.plugins.tablebind.SimpleNameStyles;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.BeforeClass;

import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import static goja.init.InitConst.DB_SQLINXML;

/**
 * <p>
 * Model 的测试超类.
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-10-03 20:30
 * @since JDK 1.6
 */
public abstract class ModelTestCase {
    protected static AutoTableBindPlugin activeRecord;

    protected static DruidPlugin dp;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        GojaConfig.getConfigProps();
        ClassFinder.findWithTest();
        Reflect.on(Goja.class).call("initWithTest");
        final Map<String, Properties> dbConfig = GojaConfig.getDbConfig();
        for (String db_config : dbConfig.keySet()) {
            final Properties db_props = dbConfig.get(db_config);
            if (db_props != null && !db_props.isEmpty()) {
                configDatabasePlugins(db_config,
                        db_props.getProperty(InitConst.DB_URL),
                        db_props.getProperty(InitConst.DB_USERNAME),
                        db_props.getProperty(InitConst.DB_PASSWORD));
            }
        }


        if (GojaConfig.getPropertyToBoolean(DB_SQLINXML, false)) {
            new SqlInXmlPlugin().start();
        }

    }

    /**
     * 配置数据库插件，用于测试用例的测试方法启动前
     *
     * @param db_config   配置名称
     * @param db_url      数据库链接地址
     * @param db_username 数据库链接用户
     * @param db_password 数据库链接密码
     */
    private static void configDatabasePlugins(String db_config, String db_url, String db_username, String db_password) {
        String dbtype = JdbcUtils.getDbType(db_url, StringUtils.EMPTY);
        String driverClassName;
        try {
            driverClassName = JdbcUtils.getDriverClassName(db_url);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        dp = new DruidPlugin(db_url
                , db_username
                , db_password
                , driverClassName);

        dp.addFilter(new StatFilter());

        dp.setInitialSize(GojaConfig.getPropertyToInt(InitConst.DB_INITIAL_SIZE, 10));
        dp.setMinIdle(GojaConfig.getPropertyToInt(InitConst.DB_INITIAL_MINIDLE, 10));
        dp.setMaxActive(GojaConfig.getPropertyToInt(InitConst.DB_INITIAL_ACTIVE, 100));
        dp.setMaxWait(GojaConfig.getPropertyToInt(InitConst.DB_INITIAL_MAXWAIT, 60000));
        dp.setTimeBetweenEvictionRunsMillis(GojaConfig.getPropertyToInt(InitConst.DB_TIMEBETWEENEVICTIONRUNSMILLIS, 120000));
        dp.setMinEvictableIdleTimeMillis(GojaConfig.getPropertyToInt(InitConst.DB_MINEVICTABLEIDLETIMEMILLIS, 120000));

        WallFilter wall = new WallFilter();
        wall.setDbType(dbtype);
        dp.addFilter(wall);

        dp.getDataSource();
        dp.start();

        activeRecord = new AutoTableBindPlugin(db_config, dp, SimpleNameStyles.LOWER_UNDERLINE);
        if (!StringUtils.equals(dbtype, JdbcConstants.MYSQL)) {
            if (StringUtils.equals(dbtype, JdbcConstants.ORACLE)) {
                activeRecord.setDialect(new OracleDialect());
            } else if (StringUtils.equals(dbtype, JdbcConstants.POSTGRESQL)) {
                activeRecord.setDialect(new PostgreSqlDialect());
            } else if (StringUtils.equals(dbtype, JdbcConstants.DB2)) {
                activeRecord.setDialect(new DB2Dialect());
            } else if (StringUtils.equals(dbtype, JdbcConstants.H2)) {
                activeRecord.setDialect(new H2Dialect());
            } else if (StringUtils.equals(dbtype, "sqlite")) {
                activeRecord.setDialect(new Sqlite3Dialect());
            } else {
                System.err.println("database type is use mysql.");
            }
        }

        activeRecord.start();

    }

    @After
    public void tearDown() throws Exception {
        activeRecord.stop();
        dp.stop();
    }
}
