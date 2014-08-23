/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package goja.app.test;

import com.alibaba.druid.util.JdbcUtils;
import goja.Goja;
import goja.app.StringPool;
import goja.init.ConfigProperties;
import goja.init.ctxbox.ClassFinder;
import goja.kits.common.Reflect;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.Ordering;
import com.google.common.io.Files;
import com.jfinal.config.JFinalConfig;
import com.jfinal.core.JFinal;
import com.jfinal.handler.Handler;
import com.jfinal.kit.PathKit;
import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.apache.tools.ant.types.EnumeratedAttribute;
import org.junit.AfterClass;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import static goja.init.InitConst.DB_PASSWORD;
import static goja.init.InitConst.DB_SCRIPT_PATH;
import static goja.init.InitConst.DB_URL;
import static goja.init.InitConst.DB_USERNAME;

public abstract class ControllerTestCase<T extends Goja> {
    protected static final Logger logger = LoggerFactory.getLogger(ControllerTestCase.class);

    protected static ServletContext servletContext = new MockServletContext();


    protected static MockHttpRequest  request;
    protected static MockHttpResponse response;
    protected static Handler          handler;

    private static boolean configStarted = false;

    private static JFinalConfig configInstance;

    private String actionUrl;
    private String bodyData;

    private File bodyFile;
    private File responseFile;

    private Class<? extends JFinalConfig> config;

    @SuppressWarnings("unchecked")
    public ControllerTestCase() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        Preconditions.checkArgument(genericSuperclass instanceof ParameterizedType,
                "Your ControllerTestCase must have genericType");
        config = (Class<? extends JFinalConfig>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
    }

    private static void initConfig(Class<JFinal> clazz, JFinal me, ServletContext servletContext, JFinalConfig config) {
        Reflect.on(me).call("init", config, servletContext);
    }

    public static void start(Class<? extends JFinalConfig> configClass) throws Exception {
        if (configStarted) {
            return;
        }
        final Properties configProps = ConfigProperties.getConfigProps();
        runScriptInitDb(configProps);
        //Before starting JFinal, lookup class file on the classpath.
        ClassFinder.findWithTest();

        Class<JFinal> clazz = JFinal.class;
        JFinal me = JFinal.me();
        configInstance = configClass.newInstance();
        initConfig(clazz, me, servletContext, configInstance);
        handler = Reflect.on(me).get("handler");
        configStarted = true;
        configInstance.afterJFinalStart();

    }

    private static void runScriptInitDb(final Properties p) {
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
                    sql_exec.execute();
                }

            }
        } catch (SQLException e) {
            logger.error("init db script is error!", e);
            throw Throwables.propagate(e);
        }
    }

    @AfterClass
    public static void stop() throws Exception {
        configInstance.beforeJFinalStop();
    }

    public Object findAttrAfterInvoke(String key) {
        return request.getAttribute(key);
    }

    private String getTarget(String url, MockHttpRequest request) {
        String target = url;
        if (url.contains(StringPool.QUESTION_MARK)) {
            target = url.substring(0, url.indexOf(StringPool.QUESTION_MARK));
            String queryString = url.substring(url.indexOf(StringPool.QUESTION_MARK) + 1);
            String[] keyVals = queryString.split(StringPool.AMPERSAND);
            for (String keyVal : keyVals) {
                int i = keyVal.indexOf('=');
                String key = keyVal.substring(0, i);
                String val = keyVal.substring(i + 1);
                request.setParameter(key, val);
            }
        }
        return target;

    }

    @Before
    public void init() throws Exception {
        start(config);
    }

    public String invoke() {
        if (bodyFile != null) {
            List<String> req = null;
            try {
                req = Files.readLines(bodyFile, Charsets.UTF_8);
            } catch (IOException e) {
                Throwables.propagate(e);
            }
            bodyData = Joiner.on("").join(req);
        }
        StringWriter resp = new StringWriter();
        request = new MockHttpRequest(bodyData);
        response = new MockHttpResponse(resp);
        Reflect.on(handler).call("handle", getTarget(actionUrl, request), request, response, new boolean[]{true});
        String response = resp.toString();
        if (responseFile != null) {
            try {
                Files.write(response, responseFile, Charsets.UTF_8);
            } catch (IOException e) {
                Throwables.propagate(e);
            }
        }
        return response;
    }


    public ControllerTestCase<T> bodyFile(File bodyFile) {
        this.bodyFile = bodyFile;
        return this;
    }

    public ControllerTestCase<T> bodyData(String bodyData) {
        this.bodyData = bodyData;
        return this;
    }

    public ControllerTestCase<T> use(String actionUrl) {
        this.actionUrl = actionUrl;
        return this;
    }

    public ControllerTestCase<T> writeTo(File responseFile) {
        this.responseFile = responseFile;
        return this;
    }

}
