package com.jfinal.module.wxchat.core;

import com.github.sog.config.JFinalApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * <p>
 *     微信公众帐号配置文件
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-03-26 00:03
 * @since JDK 1.6
 */
public class Configuration {
    protected Logger logger = LoggerFactory.getLogger(Configuration.class);

    public static final String DEFAULR_TOKEN = "token";

    private String appid;
    private String appsecret;
    private String token = DEFAULR_TOKEN;

    public final static Configuration me = new Configuration();

    /**
     * 创建配置信息
     */
    private Configuration() {
        logger.info("create Configuration");
        appid = JFinalApp.configuration.getProperty(Constants.WECHAT_APPID);
        appsecret = JFinalApp.configuration.getProperty(Constants.WECHAT_APPSECRET);
        token = JFinalApp.configuration.getProperty(Constants.WECHAT_TOKEN);
    }

    public String getAppid() {
        return appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public String getToken() {
        return token;
    }
}
