/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package japp.kits;

import com.github.sog.config.JApp;
import com.jfinal.kit.PathKit;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.RenderException;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import japp.Logger;
import japp.StringPool;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * <p>
 * .
 * </p>
 *
 * @author walter yang
 * @version 1.0 2013-11-11 12:46 AM
 * @since JDK 1.5
 */
public class Freemarkers {

    //配置
    private static Configuration appConfig = null;

    public static final String UPDATE_RESPONSE_TEMPLATE = "__updateResponseTemplate";

    static {
        getAppConfiguration();
    }

    /**
     * appConfig配置所有参数
     * 重写freemarker中的  reader方法，读取该配置文件
     *
     * @return config
     */
    private static Configuration getAppConfiguration() {
        if (appConfig == null) {
            //从freemarker 视图中获取所有配置
            appConfig = (Configuration) FreeMarkerRender.getConfiguration().clone();
            try {
                //设置模板路径
                appConfig.setDirectoryForTemplateLoading(new File(PathKit.getWebRootPath() + JApp.viewPath));
                appConfig.setObjectWrapper(new DefaultObjectWrapper());

            } catch (IOException e) {
                // TODO log
            }
        }
        return appConfig;
    }

    /**
     * 渲染模版为字符串，并制定参数
     *
     * @param tpl          模版
     * @param renderParams 参数信息
     * @return 渲染后的字符串
     */
    public static String processString(String tpl, Map<String, Object> renderParams) {
        if (appConfig == null) {
            getAppConfiguration();
        }
        StringWriter result = new StringWriter();
        try {
            Template template = appConfig.getTemplate(tpl);
            template.process(renderParams, result);
        } catch (IOException e) {
            throw new RenderException(e);
        } catch (TemplateException e) {
            throw new RenderException(e);
        }
        return result.toString();
    }

    /**
     * 将Freemakrer的字符串模版，渲染参数为字符串形式的结果。
     *
     * @param strTemplat   字符串模版，而不是文件模版
     * @param renderParams 渲染参数
     * @return 字符串形式的渲染结果
     */
    public static String renderStrTemplate(String strTemplat, Map<String, Object> renderParams) {
        Configuration cfg = new Configuration();
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate(UPDATE_RESPONSE_TEMPLATE, strTemplat);
        cfg.setTemplateLoader(stringLoader);
        Writer out = new StringWriter(2048);

        try {
            Template tpl = cfg.getTemplate(UPDATE_RESPONSE_TEMPLATE, StringPool.UTF_8);
            tpl.process(renderParams, out);
        } catch (IOException e) {
            Logger.error("Get update response template occurs error.", e);
        } catch (TemplateException e) {
            Logger.error("Process template occurs error.template content is:\n {}", e, strTemplat);
            throw new IllegalArgumentException("Error update response template.", e);
        }

        return out.toString();
    }

}
