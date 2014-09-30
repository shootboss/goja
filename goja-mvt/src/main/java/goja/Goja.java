/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja;

import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.util.JdbcUtils;
import com.alibaba.druid.wall.WallFilter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.druid.IDruidStatViewAuth;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.ViewType;
import freemarker.template.Configuration;
import goja.annotation.HandlerBind;
import goja.annotation.PluginBind;
import goja.db.dialect.DB2Dialect;
import goja.db.dialect.H2Dialect;
import goja.db.dialect.OracleDialect;
import goja.db.dialect.PostgreSqlDialect;
import goja.db.dialect.Sqlite3Dialect;
import goja.exceptions.DatabaseException;
import goja.init.AppLoadEvent;
import goja.init.InitConst;
import goja.interceptor.AutoOnLoadInterceptor;
import goja.init.ctxbox.ClassBox;
import goja.init.ctxbox.ClassType;
import goja.interceptor.syslog.LogProcessor;
import goja.interceptor.syslog.SysLogInterceptor;
import goja.job.JobsPlugin;
import goja.kits.JfinalKit;
import goja.mvc.AutoBindRoutes;
import goja.mvc.render.ftl.BlockDirective;
import goja.mvc.render.ftl.ExtendsDirective;
import goja.mvc.render.ftl.OverrideDirective;
import goja.mvc.render.ftl.PrettyTimeDirective;
import goja.mvc.render.ftl.SuperDirective;
import goja.mvc.render.ftl.shiro.ShiroTags;
import goja.plugins.index.IndexPlugin;
import goja.plugins.monogo.MongoPlugin;
import goja.plugins.quartz.QuartzPlugin;
import goja.plugins.redis.JedisPlugin;
import goja.plugins.shiro.ShiroPlugin;
import goja.plugins.sqlinxml.SqlInXmlPlugin;
import goja.plugins.tablebind.AutoTableBindPlugin;
import goja.plugins.tablebind.SimpleNameStyles;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static goja.init.InitConst.APP;
import static goja.init.InitConst.APP_VERSION;
import static goja.init.InitConst.CACHE;
import static goja.init.InitConst.DB_SQLINXML;
import static goja.init.InitConst.DB_STAT_VIEW;
import static goja.init.InitConst.DEV_MODE;
import static goja.init.InitConst.DOMAIN;
import static goja.init.InitConst.INDEX_PATH;
import static goja.init.InitConst.JOB;
import static goja.init.InitConst.JOB_QUARTZ;
import static goja.init.InitConst.MONGO_DB;
import static goja.init.InitConst.MONGO_HOST;
import static goja.init.InitConst.MONGO_MORIPH;
import static goja.init.InitConst.MONGO_MORIPH_PKGS;
import static goja.init.InitConst.MONGO_PORT;
import static goja.init.InitConst.MONGO_URL;
import static goja.init.InitConst.REDIS_HOST;
import static goja.init.InitConst.REDIS_PORT;
import static goja.init.InitConst.SECURITY;
import static goja.init.InitConst.VIEW_404;
import static goja.init.InitConst.VIEW_500;
import static goja.init.InitConst.VIEW_PATH;
import static goja.init.InitConst.VIEW_TYPE;

/**
 * <p> The core of goja. </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-06-02 11:11
 * @since JDK 1.6
 */
public class Goja extends JFinalConfig {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Goja.class);

    private static final String DEFAULT_DOMAIN = "http://127.0.0.1:8080/app";

    public static final String FTL_HTML_PREFIX = ".ftl.html";

    public static boolean initlization = false;
    public static boolean started      = false;

    // the application configuration.
    public static Properties configuration;

    //
    public static Mode mode;

    // the application view path.
    public static String  viewPath;
    public static String  domain;
    public static boolean setViewPath;
    public static String  appName;
    public static String  appVersion;

    private Routes _routes;


    /**
     * The list of supported locales
     */
    public static List<String> langs = Lists.newArrayListWithCapacity(16);

    public static File applicationPath = null;


    @Override
    public void configConstant(Constants constants) {
        // set config propertis.
        configuration = GojaConfig.getConfigProps();
        // init application path
        applicationPath = new File(PathKit.getWebRootPath());

        initlization = true;

        // dev_mode
        final boolean dev_mode = GojaConfig.getPropertyToBoolean(DEV_MODE, false);
        mode = dev_mode ? Mode.DEV : Mode.PROD;
        constants.setDevMode(dev_mode);

        viewPath = GojaConfig.getProperty(VIEW_PATH, File.separator + "WEB-INF" + File.separator + "views" + File.separator);
        if (!StrKit.isBlank(viewPath)) {
            setViewPath = true;
            constants.setBaseViewPath(viewPath);
        }
        appName = GojaConfig.getProperty(APP, "app");
        appVersion = GojaConfig.getProperty(APP_VERSION, "0.0.1");

        // init logger.
        Logger.init();

        domain = GojaConfig.getProperty(DOMAIN, DEFAULT_DOMAIN);
        String view_type = GojaConfig.getProperty(VIEW_TYPE);
        if (!StrKit.isBlank(view_type)) {
            setViewType(constants, view_type);
        } else {
            constants.setFreeMarkerViewExtension(FTL_HTML_PREFIX);
            setFtlSharedVariable();
        }
        String view_404 = GojaConfig.getProperty(VIEW_404);
        if (!Strings.isNullOrEmpty(view_404)) {
            constants.setError404View(view_404);
        }
        String view_500 = GojaConfig.getProperty(VIEW_500);
        if (!Strings.isNullOrEmpty(view_500)) {
            constants.setError500View(view_500);
        }
    }

    @Override
    public void configRoute(Routes routes) {
        this._routes = routes;
        routes.add(new AutoBindRoutes());
    }

    @Override
    public void configPlugin(Plugins plugins) {

        if (GojaConfig.getPropertyToBoolean(CACHE, false)) {
            plugins.add(new EhCachePlugin());
        }

        initDataSource(plugins);

        if (GojaConfig.getPropertyToBoolean(SECURITY, false)) {
            plugins.add(new ShiroPlugin(this._routes));
        }

        if (GojaConfig.getPropertyToBoolean(JOB_QUARTZ, false)) {
            plugins.add(new QuartzPlugin());
        }

        if (GojaConfig.getPropertyToBoolean(JOB, false)) {
            plugins.add(new JobsPlugin());
        }

        if (!Strings.isNullOrEmpty(GojaConfig.getProperty(INDEX_PATH))) {
            plugins.add(new IndexPlugin());
        }

        final String mongo_host = GojaConfig.getProperty(MONGO_HOST, MongoPlugin.DEFAULT_HOST);
        final String mongo_url = GojaConfig.getProperty(MONGO_URL, StringUtils.EMPTY);
        if (!Strings.isNullOrEmpty(mongo_host) || !Strings.isNullOrEmpty(mongo_url)) {
            int mongo_port = GojaConfig.getPropertyToInt(MONGO_PORT, MongoPlugin.DEFAUL_PORT);
            String mongo_db = GojaConfig.getProperty(MONGO_DB, "test");
            boolean moriph = GojaConfig.getPropertyToBoolean(MONGO_MORIPH, false);
            String pkgs = GojaConfig.getProperty(MONGO_MORIPH_PKGS, MongoPlugin.DEFAULT_PKGS);
            final MongoPlugin mongodb = new MongoPlugin(mongo_host, mongo_port, mongo_db, moriph, pkgs);
            plugins.add(mongodb);
        }

        final String redis_host = GojaConfig.getProperty(REDIS_HOST, StringUtils.EMPTY);
        if (!Strings.isNullOrEmpty(redis_host)) {
            int port = GojaConfig.getPropertyToInt(REDIS_PORT, JedisPlugin.DEFAULT_PORT);
            final JedisPlugin jedis = new JedisPlugin(redis_host, port, 2000);
            plugins.add(jedis);
        }

        final List<Class> plugins_clses = ClassBox.getInstance().getClasses(ClassType.PLUGIN);
        if (plugins_clses != null && !plugins_clses.isEmpty()) {
            PluginBind pluginBind;
            for (Class plugin : plugins_clses) {
                pluginBind = (PluginBind) plugin.getAnnotation(PluginBind.class);
                if (pluginBind != null) {
                    try {
                        plugins.add((com.jfinal.plugin.IPlugin) plugin.newInstance());
                    } catch (InstantiationException e) {
                        Logger.error("The plugin instance is error!", e);
                    } catch (IllegalAccessException e) {
                        Logger.error("The plugin instance is error!", e);
                    }
                }
            }
        }

    }

    @Override
    public void configInterceptor(Interceptors interceptors) {
        try {
            final List<Class> log_precess = ClassBox.getInstance().getClasses(ClassType.LOGPERCESSOR);
            if (log_precess != null && !log_precess.isEmpty()) {
                Class log_percess_impl_cls = log_precess.get(0);
                URL config_url = com.google.common.io.Resources.getResource("syslog.json");
                if (config_url != null) {
                    SysLogInterceptor sysLogInterceptor = new SysLogInterceptor();
                    sysLogInterceptor = sysLogInterceptor.setLogProcesser((LogProcessor) log_percess_impl_cls.newInstance(), config_url.getPath());
                    if (sysLogInterceptor != null) {
                        interceptors.add(sysLogInterceptor);
                    }
                }
            }

        } catch (IllegalArgumentException e) {
            logger.error("Enable the system operation log interceptor abnormalities.", e);
        } catch (InstantiationException e) {
            logger.error("Enable the system operation log interceptor abnormalities.", e);
        } catch (IllegalAccessException e) {
            logger.error("Enable the system operation log interceptor abnormalities.", e);
        }

        new AutoOnLoadInterceptor(interceptors).load();
    }

    @Override
    public void configHandler(Handlers handlers) {
        //访问路径是/admin/monitor

        final String view_url = GojaConfig.getProperty(DB_STAT_VIEW, "/druid/monitor");

        final DruidStatViewHandler dvh = new DruidStatViewHandler(view_url, new IDruidStatViewAuth() {
            public boolean isPermitted(HttpServletRequest request) {
                return true;
            }
        });

        handlers.add(new ContextPathHandler("ctx"));

        handlers.add(dvh);

        final List<Class> handler_clses = ClassBox.getInstance().getClasses(ClassType.HANDLER);
        if (handler_clses != null && !handler_clses.isEmpty()) {
            HandlerBind handlerBind;
            for (Class handler : handler_clses) {
                handlerBind = (HandlerBind) handler.getAnnotation(HandlerBind.class);
                if (handlerBind != null) {
                    try {
                        handlers.add((com.jfinal.handler.Handler) handler.newInstance());
                    } catch (InstantiationException e) {
                        logger.error("The Handler instance is error!", e);
                    } catch (IllegalAccessException e) {
                        logger.error("The Handler instance is error!", e);
                    }
                }
            }
        }
    }

    @Override
    public void afterJFinalStart() {
        List<Class> appCliasses = ClassBox.getInstance().getClasses(ClassType.APP);
        if (appCliasses != null && !appCliasses.isEmpty()) {
            for (Class appCliass : appCliasses) {

                AppLoadEvent event;
                try {
                    event = (AppLoadEvent) appCliass.newInstance();
                    if (event != null) {
                        event.load();
                    }
                } catch (Throwable t) {
                    logger.error("load event is error!", t);
                }
            }
        }
        JfinalKit.init();
        started = true;
        // clear app config value.
        GojaConfig.clear();
    }

    @Override
    public void beforeJFinalStop() {
        ClassBox.getInstance().clearBox();
        started = false;
    }

    /**
     * init databases.
     *
     * @param plugins plugin.
     */
    private void initDataSource(final Plugins plugins) {

        final Map<String, Properties> dbConfig = GojaConfig.getDbConfig();
        for (String db_config : dbConfig.keySet()) {
            final Properties db_props = dbConfig.get(db_config);
            if (db_props != null && !db_props.isEmpty()) {
                configDatabasePlugins(db_config, plugins,
                        db_props.getProperty(InitConst.DB_URL),
                        db_props.getProperty(InitConst.DB_USERNAME),
                        db_props.getProperty(InitConst.DB_PASSWORD));
            }
        }

        if (GojaConfig.getPropertyToBoolean(DB_SQLINXML, false)) {
            plugins.add(new SqlInXmlPlugin());
        }

    }

    /**
     * The configuration database, specify the name of the database.
     *
     * @param configName the database config name.
     * @param plugins    the jfinal plugins.
     * @param db_url     the database connection url.
     * @param username   the login username.
     * @param password   the login password.
     */
    private void configDatabasePlugins(String configName, final Plugins plugins, String db_url, String username, String password) {
        if (!Strings.isNullOrEmpty(db_url)) {
            String dbtype = JdbcUtils.getDbType(db_url, StringUtils.EMPTY);
            String driverClassName;
            try {
                driverClassName = JdbcUtils.getDriverClassName(db_url);
            } catch (SQLException e) {
                throw new DatabaseException(e.getMessage(), e);
            }
            final DruidPlugin druidPlugin = new DruidPlugin(db_url, username, password, driverClassName);
            druidPlugin.setFilters("stat,wall");
            final WallFilter wall = new WallFilter();
            wall.setDbType(JdbcConstants.MYSQL);
            druidPlugin.addFilter(wall);
            plugins.add(druidPlugin);

            //  setting db table name like 'dev_info'
            final AutoTableBindPlugin atbp = new AutoTableBindPlugin(configName, druidPlugin, SimpleNameStyles.LOWER_UNDERLINE);

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
            constants.setFreeMarkerViewExtension(FTL_HTML_PREFIX);
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
        if (GojaConfig.getPropertyToBoolean(InitConst.SECURITY, true)) {
            config.setSharedVariable("shiro", new ShiroTags());
        }
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
