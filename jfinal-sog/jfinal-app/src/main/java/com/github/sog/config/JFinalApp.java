/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package com.github.sog.config;

import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.util.JdbcUtils;
import com.alibaba.druid.wall.WallFilter;
import com.github.sog.controller.flash.EhCacheFlashManager;
import com.github.sog.controller.flash.FlashManager;
import com.github.sog.controller.flash.SessionFlashManager;
import com.github.sog.db.dialect.DB2Dialect;
import com.github.sog.db.dialect.H2Dialect;
import com.github.sog.exceptions.DatabaseException;
import com.github.sog.initalizer.AppLoadEvent;
import com.github.sog.initalizer.ConfigProperties;
import com.github.sog.initalizer.ctxbox.ClassBox;
import com.github.sog.initalizer.ctxbox.ClassType;
import com.github.sog.interceptor.SystemLogProcessor;
import com.github.sog.interceptor.autoscan.AutoOnLoadInterceptor;
import com.github.sog.interceptor.syslog.SysLogInterceptor;
import com.github.sog.plugin.log.LogbackLoggerFactory;
import com.github.sog.plugin.monogodb.MongodbPlugin;
import com.github.sog.plugin.quartz.QuartzPlugin;
import com.github.sog.plugin.redis.JedisPlugin;
import com.github.sog.plugin.shiro.ShiroPlugin;
import com.github.sog.plugin.sqlinxml.SqlInXmlPlugin;
import com.github.sog.plugin.tablebind.AutoTableBindPlugin;
import com.github.sog.plugin.tablebind.SimpleNameStyles;
import com.github.sog.render.ftl.BlockDirective;
import com.github.sog.render.ftl.ExtendsDirective;
import com.github.sog.render.ftl.OverrideDirective;
import com.github.sog.render.ftl.PrettyTimeDirective;
import com.github.sog.render.ftl.SuperDirective;
import com.github.sog.route.AutoBindRoutes;
import com.google.common.base.Strings;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.activerecord.dialect.Sqlite3Dialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.druid.IDruidStatViewAuth;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.ViewType;
import freemarker.template.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static com.github.sog.initalizer.InitConst.*;

/**
 * <p>
 * .
 * </p>
 *
 * @author walter yang
 * @version 1.0 2013-12-12 13:50
 * @since JDK 1.5
 */
public class JFinalApp extends JFinalConfig {

    private static final Logger  logger         = LoggerFactory.getLogger(JFinalApp.class);
    private static final String  DEFAULT_DOMAIN = "http://127.0.0.1:8080/app";
    public static        boolean initlization   = false;
    public static  Properties   configuration;
    /**
     * The application mode
     */
    public static  Mode         mode;
    public static  String       viewPath;
    public static  String       domain;
    public static  boolean      setViewPath;
    public static  String       appName;
    private static FlashManager flashManager;
    /**
     * Global routing system configuration.
     */
    private        Routes       routes;

    public static FlashManager flashManager() {
        if (flashManager == null) {
            flashManager = new SessionFlashManager();
        }
        return flashManager;
    }

    @Override
    public void configConstant(Constants constants) {
        // set config propertis.
        configuration = ConfigProperties.getConfigProps();

        initlization = true;

        constants.setLoggerFactory(new LogbackLoggerFactory());

        // dev_mode
        final boolean dev_mode = ConfigProperties.getPropertyToBoolean(DEV_MODE, false);
        mode = dev_mode ? Mode.DEV : Mode.PROD;
        constants.setDevMode(dev_mode);

        viewPath = ConfigProperties.getProperty(VIEW_PATH, "/WEB-INF/views/");
        if (!StringKit.isBlank(viewPath)) {
            setViewPath = true;
            constants.setBaseViewPath(viewPath);
        }
        appName = ConfigProperties.getProperty(APP, "app");
        domain = ConfigProperties.getProperty(DOMAIN, DEFAULT_DOMAIN);
        String flash = ConfigProperties.getProperty(FLASH, "session");
        flashManager = StringUtils.equals("session", flash) ? new SessionFlashManager()
                : new EhCacheFlashManager("flash_cache_val");
        String view_type = ConfigProperties.getProperty(VIEW_TYPE);
        if (!StringKit.isBlank(view_type)) {
            setViewType(constants, view_type);
        } else {
            constants.setFreeMarkerViewExtension(".ftl.html");
            setFtlSharedVariable();
        }
        String view_404 = ConfigProperties.getProperty(VIEW_404);
        if (!Strings.isNullOrEmpty(view_404)) {
            constants.setError401View(view_404);
        }
        String view_500 = ConfigProperties.getProperty(VIEW_500);
        if (!Strings.isNullOrEmpty(view_500)) {
            constants.setError500View(view_500);
        }
    }

    @Override
    public void configRoute(Routes routes) {
        this.routes = routes;
        routes.add(new AutoBindRoutes());
    }

    @Override
    public void configPlugin(Plugins plugins) {
        String db_url = ConfigProperties.getProperty(DB_URL);
        initDataSource(plugins, db_url);

        if (ConfigProperties.getPropertyToBoolean(SECURITY, false)) {
            plugins.add(new ShiroPlugin(this.routes));
        }
        if (ConfigProperties.getPropertyToBoolean(CACHE, false)) {
            plugins.add(new EhCachePlugin());
        }

        if (ConfigProperties.getPropertyToBoolean(JOB, false)) {
            plugins.add(new QuartzPlugin());
        }

        final String mongo_host = ConfigProperties.getProperty(MONGO_HOST, MongodbPlugin.DEFAULT_HOST);
        final String mongo_url = ConfigProperties.getProperty(MONGO_URL, StringUtils.EMPTY);
        if (!Strings.isNullOrEmpty(mongo_host) || !Strings.isNullOrEmpty(mongo_url)) {
            int mongo_port = ConfigProperties.getPropertyToInt(MONGO_PORT, MongodbPlugin.DEFAUL_PORT);
            String mongo_db = ConfigProperties.getProperty(MONGO_DB, "test");
            boolean moriph = ConfigProperties.getPropertyToBoolean(MONGO_MORIPH, false);
            String pkgs = ConfigProperties.getProperty(MONGO_MORIPH_PKGS, MongodbPlugin.DEFAULT_PKGS);
            final MongodbPlugin mongodb = new MongodbPlugin(mongo_host, mongo_port, mongo_db, moriph, pkgs);
            plugins.add(mongodb);
        }

        final String redis_host = ConfigProperties.getProperty(REDIS_HOST, StringUtils.EMPTY);
        if (!Strings.isNullOrEmpty(redis_host)) {
            int port = ConfigProperties.getPropertyToInt(REDIS_PORT, JedisPlugin.DEFAULT_PORT);
            final JedisPlugin jedis = new JedisPlugin(redis_host, port, 2000);
            plugins.add(jedis);
        }

    }

    @Override
    public void configInterceptor(Interceptors interceptors) {
        try {
            URL config_url = com.google.common.io.Resources.getResource("syslog.json");
            if (config_url != null) {
                SysLogInterceptor sysLogInterceptor = new SysLogInterceptor();
                sysLogInterceptor = sysLogInterceptor.setLogProcesser(new SystemLogProcessor(), config_url.getPath());
                if (sysLogInterceptor != null) {
                    interceptors.add(sysLogInterceptor);
                }
            }
        } catch (IllegalArgumentException ignored) {
            // ingored.
        }

//        interceptors.add(new ContextInterceptor());

        new AutoOnLoadInterceptor(interceptors).load();
    }

    @Override
    public void configHandler(Handlers handlers) {
        //访问路径是/admin/monitor
        DruidStatViewHandler dvh;
        final String view_url = ConfigProperties.getProperty(DB_STAT_VIEW, "/druid/monitor");

        dvh = new DruidStatViewHandler(view_url, new IDruidStatViewAuth() {
            public boolean isPermitted(HttpServletRequest request) {
                return true;
            }
        });

        handlers.add(new ContextPathHandler("ctx"));

        handlers.add(dvh);
    }

    @Override
    public void afterJFinalStart() {
        super.afterJFinalStart();
        List<Class> appCliasses = ClassBox.getInstance().getClasses(ClassType.APP);
        if (appCliasses != null && !appCliasses.isEmpty()) {
            for (Class appCliass : appCliasses) {

                AppLoadEvent event;
                try {
                    event = (AppLoadEvent) appCliass.newInstance();
                    event.load();
                } catch (Throwable t) {
                    logger.error("load event is error!", t);
                    // ingore
                }
            }
        }

        ClassBox.getInstance().clearBox();
    }

    @Override
    public void beforeJFinalStop() {
        super.beforeJFinalStop();
    }

    /**
     * init databases.
     *
     * @param plugins plugin.
     * @param db_url  db_url
     */
    private void initDataSource(Plugins plugins, String db_url) {
        if (!Strings.isNullOrEmpty(db_url)) {
            String dbtype = JdbcUtils.getDbType(db_url, StringUtils.EMPTY);
            String driverClassName;
            try {
                driverClassName = JdbcUtils.getDriverClassName(db_url);
            } catch (SQLException e) {
                throw new DatabaseException(e.getMessage(), e);
            }
            final DruidPlugin druidPlugin = new DruidPlugin(
                    db_url
                    , ConfigProperties.getProperty(DB_USERNAME)
                    , ConfigProperties.getProperty(DB_PASSWORD)
                    , driverClassName);
            druidPlugin.setFilters("stat,wall");
            final WallFilter wall = new WallFilter();
            wall.setDbType(JdbcConstants.MYSQL);
            druidPlugin.addFilter(wall);
            plugins.add(druidPlugin);


            //  setting db table name like 'dev_info'
            final AutoTableBindPlugin atbp = new AutoTableBindPlugin(druidPlugin, SimpleNameStyles.LOWER_UNDERLINE);

            if (!StringUtils.equals(dbtype, JdbcConstants.MYSQL)) {
                if (StringUtils.equals(dbtype, JdbcConstants.ORACLE)) {
                    atbp.setDialect(new OracleDialect());
                } else if (StringUtils.equals(dbtype, JdbcConstants.POSTGRESQL)) {
                    atbp.setDialect(new PostgreSqlDialect());
                } else if (StringUtils.equals(dbtype, JdbcConstants.DB2)) {
                    atbp.setDialect(new DB2Dialect());
                } else if (StringUtils.equals(dbtype, JdbcConstants.H2)) {
                    atbp.setDialect(new H2Dialect());
                } else if (StringUtils.equals(dbtype, "sqlite")) {
                    atbp.setDialect(new Sqlite3Dialect());
                } else {
                    System.err.println("database type is use mysql.");
                }
            }
            atbp.setShowSql(mode.isDev());
            plugins.add(atbp);
            if (ConfigProperties.getPropertyToBoolean(DB_SQLINXML, false)) {
                plugins.add(new SqlInXmlPlugin());
            }
        }

    }

    /**
     * set view type.
     *
     * @param constants jfinal constant.
     * @param view_type view type.
     */
    private void setViewType(Constants constants, String view_type) {
        final ViewType viewType = ViewType.valueOf(view_type.toUpperCase());
        if (viewType == ViewType.FREE_MARKER) {
            constants.setFreeMarkerViewExtension(".ftl");
            setFtlSharedVariable();
        }
        constants.setViewType(viewType);
    }

    /**
     * set freemarker variable.
     */
    private void setFtlSharedVariable() {
        // custmer variable
        final Configuration config = FreeMarkerRender.getConfiguration();
        config.setSharedVariable("block", new BlockDirective());
        config.setSharedVariable("extends", new ExtendsDirective());
        config.setSharedVariable("override", new OverrideDirective());
        config.setSharedVariable("super", new SuperDirective());
        // 增加日期美化指令（类似 几分钟前）
        config.setSharedVariable("prettyTime", new PrettyTimeDirective());
    }

    /**
     * 2 modes
     */
    public enum Mode {

        /**
         * Enable development-specific features, e.g. view the documentation at the URL {@literal "/@documentation"}.
         */
        DEV,
        /**
         * Disable development-specific features.
         */
        PROD;

        public boolean isDev() {
            return this == DEV;
        }

    }
}
