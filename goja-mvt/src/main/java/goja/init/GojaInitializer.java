/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.init;

import com.alibaba.druid.util.JdbcUtils;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.Ordering;
import com.jfinal.kit.PathKit;
import goja.GojaConfig;
import goja.StringPool;
import goja.init.ctxbox.ClassFinder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.apache.tools.ant.types.EnumeratedAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.File;
import java.sql.SQLException;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Properties;
import java.util.Set;

import static goja.init.InitConst.DB_PASSWORD;
import static goja.init.InitConst.DB_SCRIPT_PATH;
import static goja.init.InitConst.DB_URL;
import static goja.init.InitConst.DB_USERNAME;
import static goja.init.InitConst.DEV_MODE;

/**
 * <p>
 * 通过Servlet 3.0 的动态加载方式加载JFinal，免去Web.xml的配置.
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-01-02 15:06
 * @since JDK 1.6
 */
public class GojaInitializer implements ServletContainerInitializer {

    private static final Logger logger = LoggerFactory.getLogger(GojaInitializer.class);

    @Override
    public void onStartup(Set<Class<?>> classSet, ServletContext ctx)
            throws ServletException {

        final Properties p = GojaConfig.getConfigProps();

        boolean security = GojaConfig.getPropertyToBoolean(InitConst.SECURITY, true);
        if (security) {
            ctx.addListener("org.apache.shiro.web.env.EnvironmentLoaderListener");
            ctx.addFilter("ShiroFilter", "org.apache.shiro.web.servlet.ShiroFilter")
                    .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        }
        // init logger
        goja.Logger.init();
        //logger context destroy listener.
        ctx.addListener("ch.qos.logback.classic.selector.servlet.ContextDetachingSCL");

        //Before starting JFinal, lookup class file on the classpath.
        ClassFinder.find();

        String app_name = GojaConfig.getProperty(InitConst.APP, StringUtils.EMPTY);

        FilterRegistration.Dynamic jfinalFilter = ctx.addFilter("goja@jfinal", "com.jfinal.core.JFinalFilter");

        jfinalFilter.setInitParameter("configClass", "goja.Goja");
        jfinalFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        // 支持异步请求处理
        jfinalFilter.setAsyncSupported(true);

        System.out.println("initializer " + app_name + " Application ok!");
        boolean dev_mode = Boolean.valueOf(p.getProperty(DEV_MODE, StringPool.FALSE));
        if (dev_mode) {
            runScriptInitDb(p);
        }
    }


    private void runScriptInitDb(final Properties p) {
        try {

            String script_path = p.getProperty(DB_SCRIPT_PATH, "misc/sql/");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(script_path)
                    , "The Database init database script init!");
            final String real_script_path = PathKit.getRootClassPath() + File.separator + script_path;
            if (logger.isDebugEnabled()) {
                logger.debug("init db script with {}", real_script_path);
            }
            final File script_dir = new File(real_script_path);
            if (script_dir.exists() && script_dir.isDirectory()) {
                final String db_url = p.getProperty(DB_URL);
                Preconditions.checkNotNull(db_url, "The DataBase connection url is must!");
                Collection<File> list_script_files
                        = Ordering.natural()
                        .sortedCopy(FileUtils.listFiles(script_dir, new String[]{"sql"}, false));
                for (File list_script_file : list_script_files) {
                    final SQLExec sql_exec = new SQLExec();
                    final String driverClassName = JdbcUtils.getDriverClassName(db_url);
                    sql_exec.setDriver(driverClassName);
                    sql_exec.setUrl(db_url);
                    final String db_username = p.getProperty(DB_USERNAME, "root");
                    final String db_password = p.getProperty(DB_PASSWORD, "123456");
                    sql_exec.setUserid(db_username);
                    sql_exec.setPassword(db_password);

                    sql_exec.setOnerror((SQLExec.OnError) (EnumeratedAttribute.getInstance(SQLExec.OnError.class, "abort")));
                    sql_exec.setPrint(true);
                    sql_exec.setProject(new Project());
                    sql_exec.setSrc(list_script_file);
                    try {
                        sql_exec.execute();
                    }catch (Exception e){
                        logger.error("the init database has already ok!", e);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("init db script is error!", e);
            throw Throwables.propagate(e);
        }
    }

}
