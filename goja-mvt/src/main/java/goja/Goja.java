/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja;

import com.alibaba.druid.filter.stat.StatFilter;
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
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.activerecord.dialect.Sqlite3Dialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.druid.IDruidStatViewAuth;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.ViewType;
import com.jfinal.weixin.sdk.api.ApiConfig;
import freemarker.template.Configuration;
import goja.annotation.HandlerBind;
import goja.annotation.PluginBind;
import goja.exceptions.DatabaseException;
import goja.init.AppLoadEvent;
import goja.init.InitConst;
import goja.init.ctxbox.ClassBox;
import goja.init.ctxbox.ClassType;
import goja.job.JobsPlugin;
import goja.mvc.AutoBindRoutes;
import goja.mvc.error.GojaErrorRenderFactory;
import goja.mvc.interceptor.AutoOnLoadInterceptor;
import goja.mvc.interceptor.syslog.LogProcessor;
import goja.mvc.interceptor.syslog.SysLogInterceptor;
import goja.mvc.render.ftl.PrettyTimeDirective;
import goja.mvc.render.ftl.layout.BlockDirective;
import goja.mvc.render.ftl.layout.ExtendsDirective;
import goja.mvc.render.ftl.layout.OverrideDirective;
import goja.mvc.render.ftl.layout.SuperDirective;
import goja.mvc.render.ftl.shiro.ShiroTags;
import goja.mvc.security.SecurityUserData;
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
import static goja.init.InitConst.WX_APPID;
import static goja.init.InitConst.WX_SECRET;
import static goja.init.InitConst.WX_TOKEN;
import static goja.init.InitConst.WX_URL;

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

    public static final String VERSION = "v0.1";

    // the application configuration.
    public static Properties configuration;

    // run mode.
    public static Mode mode;

    // the application view path.
    public static String viewPath;
    public static String domain;
    public static String appName;
    public static String appVersion;

    private Routes _routes;

    public static SecurityUserData securityUserData;


    /**
     * The list of supported locales
     */
    public static final List<String> langs = Lists.newArrayListWithCapacity(16);

    public static File applicationPath = null;

    /**
     * 为方便测试用例的使用，这个提供一个手动初始化的方法为测试用例使用,调用采用反射机制
     * <p/>
     * Reflect.on(Goja.class).call("initWithTest");
     */
    static void initWithTest() {

        // set config propertis.
        configuration = GojaConfig.getConfigProps();
        initlization = true;

        // dev_mode
        mode =Mode.DEV ;
        Logger.init();
    }

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
        // fixed: render view has views//xxx.ftl
        viewPath = GojaConfig.getProperty(VIEW_PATH, File.separator + "WEB-INF" + File.separator + "views");
        constants.setBaseViewPath(viewPath);

        appName = GojaConfig.getProperty(APP, "app");
        appVersion = GojaConfig.getProperty(APP_VERSION, "0.0.1");

        // init logger.
        Logger.init();

        // init wxchat config
        final String wx_url = GojaConfig.getProperty(WX_URL);
        if (!Strings.isNullOrEmpty(wx_url)) {
            // Config Wx Api
            ApiConfig.setDevMode(dev_mode);
            ApiConfig.setUrl(wx_url);
            ApiConfig.setToken(GojaConfig.getProperty(WX_TOKEN));
            ApiConfig.setAppId(GojaConfig.getProperty(WX_APPID));
            ApiConfig.setAppSecret(GojaConfig.getProperty(WX_SECRET));
        }

        if (GojaConfig.getPropertyToBoolean(SECURITY, true)) {
            final List<Class> security_user = ClassBox.getInstance().getClasses(ClassType.SECURITY_DATA);
            if (security_user != null && security_user.size() == 1) {
                try {
                    securityUserData = (SecurityUserData) security_user.get(0).newInstance();
                } catch (InstantiationException e) {
                    logger.error("the security user data has error!", e);
                } catch (IllegalAccessException e) {
                    logger.error("the security user data has error!", e);
                }
            }
        }

        domain = GojaConfig.getProperty(DOMAIN, DEFAULT_DOMAIN);
        String view_type = GojaConfig.getProperty(VIEW_TYPE);
        if (!StrKit.isBlank(view_type)) {
            setViewType(constants, view_type);
        } else {
            constants.setFreeMarkerViewExtension(FTL_HTML_PREFIX);
            setFtlSharedVariable();
        }
        constants.setErrorRenderFactory(new GojaErrorRenderFactory());
    }

    @Override
    public void configRoute(Routes routes) {
        this._routes = routes;
        routes.add(new AutoBindRoutes());
    }

    @Override
    public void configPlugin(Plugins plugins) {
        // fixed: https://github.com/GojaFramework/goja/issues/4
        started = true;

        if (GojaConfig.getPropertyToBoolean(CACHE, false)) {
            plugins.add(new EhCachePlugin());
        }

        initDataSource(plugins);

        if (GojaConfig.getPropertyToBoolean(SECURITY, true)) {
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
        //访问路径是/druid/monitor

        final String view_url = GojaConfig.getProperty(DB_STAT_VIEW, "/druid/monitor");

        final DruidStatViewHandler dvh = new DruidStatViewHandler(view_url, new IDruidStatViewAuth() {
            @Override
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
                        GojaConfig.getProperty(InitConst.DB_URL),
                        GojaConfig.getProperty(InitConst.DB_USERNAME),
                        GojaConfig.getProperty(InitConst.DB_PASSWORD));
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
            druidPlugin.addFilter(new StatFilter());


            druidPlugin.setInitialSize(GojaConfig.getPropertyToInt(InitConst.DB_INITIAL_SIZE, 10));
            druidPlugin.setMinIdle(GojaConfig.getPropertyToInt(InitConst.DB_INITIAL_MINIDLE, 10));
            druidPlugin.setMaxActive(GojaConfig.getPropertyToInt(InitConst.DB_INITIAL_ACTIVE, 100));
            druidPlugin.setMaxWait(GojaConfig.getPropertyToInt(InitConst.DB_INITIAL_MAXWAIT, 60000));
            druidPlugin.setTimeBetweenEvictionRunsMillis(GojaConfig.getPropertyToInt(InitConst.DB_TIMEBETWEENEVICTIONRUNSMILLIS, 120000));
            druidPlugin.setMinEvictableIdleTimeMillis(GojaConfig.getPropertyToInt(InitConst.DB_MINEVICTABLEIDLETIMEMILLIS, 120000));

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
                } else if (StringUtils.equals(dbtype, JdbcConstants.H2)) {
                    atbp.setDialect(new AnsiSqlDialect());
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
        config.setSharedVariable("prettytime", new PrettyTimeDirective());
        if (GojaConfig.getPropertyToBoolean(InitConst.SECURITY, true)) {
            config.setSharedVariable("shiro", new ShiroTags(config.getObjectWrapper()));
        }
    }

    /**
     * 2 modes
     */
    public enum Mode {

        /**
         * Enable development-specific features.
         */
        DEV,
        /**
         * Disable development-specific features.
         */
        PROD;

        /**
         * Determine whether the current operation mode for the development pattern
         *
         * @return If returns true then said to development mode, or as an official running environment.
         */
        public boolean isDev() {
            return this == DEV;
        }

    }
}
