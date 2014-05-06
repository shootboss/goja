/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package com.github.sog.controller;

import com.github.sog.config.JFinalApp;
import com.github.sog.config.StringPool;
import com.github.sog.controller.datatables.core.DataSet;
import com.github.sog.controller.datatables.core.DatatablesCriterias;
import com.github.sog.controller.datatables.core.DatatablesResponse;
import com.github.sog.kit.tpl.FreemarkerKit;
import com.github.sog.render.BadRequest;
import com.github.sog.render.FreeMarkerXMLRender;
import com.github.sog.render.JxlsRender;
import com.github.sog.render.NotModified;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

import java.util.Enumeration;
import java.util.Map;

/**
 * <p>
 * 基本控制器.
 * </p>
 *
 * @author walter yang
 * @version 1.0 2013-12-12 14:23
 * @since JDK 1.5
 */
public abstract class BasicController extends Controller {

    protected static boolean flashflag = false;

    /**
     * Send a 304 Not Modified response
     */
    protected static void notModified() {
        new NotModified().render();
    }

    /**
     * Send a 400 Bad request
     */
    protected static void badRequest() {
        new BadRequest().render();
    }

    protected static void renderExcel(String templateFile, Map<String, Object> datas) {
        JxlsRender.me(templateFile).beans(datas).render();
    }

    @Override
    public void render(String view) {

        super.render((view.startsWith(StringPool.SLASH) && JFinalApp.setViewPath)
                ? JFinalApp.viewPath + view.replaceFirst(StringPool.SLASH, StringPool.EMPTY)
                : view);

    }

    /**
     * 在jfinal基础上增加渲染XML格式的数据.
     *
     * @param view 视图，这里是基于freemarker的，所以必须为freemarker模板
     */
    protected void renderXml(String view) {
        render(new FreeMarkerXMLRender(view));
    }

    /**
     * 渲染错误提示信息，以Json格式。
     *
     * @param error 错误信息
     */
    protected void renderError(String error) {
        renderJson(AjaxMessage.error(error));
    }

    protected void renderFailure(String failure) {
        renderJson(AjaxMessage.failure(failure));
    }

    /**
     * 渲染成功提示信息,以JSON格式
     *
     * @param message 提示信息
     */
    protected void renderSuccess(String message) {
        renderJson(AjaxMessage.ok(message));
    }

    protected void renderSuccess() {
        renderJson(AjaxMessage.ok());
    }

    /**
     * 渲染错误提示信息，以Json格式。
     *
     * @param error 错误信息
     * @param e     异常
     */
    protected void renderError(String error, Exception e) {
        renderJson(AjaxMessage.error(error, e));
    }

    /**
     * 渲染视图为字符串
     *
     * @param view 视图模版
     * @return 视图渲染后的字符串
     */
    protected String renderTpl(String view) {
        final Enumeration<String> attrs = getAttrNames();
        final Map<String, Object> root = Maps.newHashMap();
        while (attrs.hasMoreElements()) {
            String attrName = attrs.nextElement();
            root.put(attrName, getAttr(attrName));
        }
        return FreemarkerKit.processString(view, root);
    }

    /**
     * 渲染todo提示
     */
    protected void todo() {
        renderJson(AjaxMessage.developing());
    }

    /**
     * 从Request中获取参数并封装为对象进行处理
     *
     * @return jquery DataTables参数信息
     */
    protected DatatablesCriterias getCriterias() {
        return DatatablesCriterias.criteriasWithRequest(getRequest());
    }

    protected <E> void renderDataTables(Page<E> datas, DatatablesCriterias criterias) {
        Preconditions.checkNotNull(criterias, "datatable criterias is must be not null.");
        DataSet<E> dataSet = DataSet.newSet(datas.getList(), datas.getTotalRow(), datas.getTotalRow());
        DatatablesResponse<E> response = DatatablesResponse.build(dataSet, criterias);
        renderJson(response);
    }

    /**
     * 根据当前路径构造将要跳转的路径的完整Action
     *
     * @param currentActionPath 当前路径，类似 /sau/index
     * @param url               下一个路径，类似/au/login, detail?，admin/detail.
     * @return 下一个Action的完整路径（）
     */
    public String parsePath(String currentActionPath, String url) {
        if (url.startsWith("/")) {//完整路径
            return url.split("\\?")[0];
        } else if (!url.contains("/")) {//类似于detail的路径。
            return "/" + currentActionPath.split("/")[1] + "/" + url.split("\\?")[0];
        } else if (url.contains("http:") || url.contains("https:")) {
            return null;
        }
        ///abc/def","bcd/efg?abc
        return currentActionPath + "/" + url.split("\\?")[0];
    }

    /**
     * 设定Flash，该flash中的所有信息将会出现在下一个请求中。
     * 该操作一般用在forwardAction 及redirect操作前。
     * 在设定Falsh拦截器后，拦截器会自动注入所有当前Action中设定的Flash信息到request中。
     * 且仅注入一次。
     *
     * @param key   键
     * @param value 值
     */
    public void setFlash(String key, Object value) {
        String actionPath = getRequest().getRequestURI();
        JFinalApp.flashManager().setFlash(this.getSession(false), actionPath, key, value);
        flashflag = true;
    }

    @Override
    public void forwardAction(String actionUrl) {
        if (flashflag) {//若有新加入的Flash。更换key。
            String actionPath = getRequest().getRequestURI();
            //将以当前actionPath为key更替为下一个请求actionPath作为key
            JFinalApp.flashManager().updateFlashKey(this.getSession(false), actionPath, actionUrl);
            flashflag = false;
        }
        super.forwardAction(actionUrl);
    }

    @Override
    public void redirect(String url) {
        if (flashflag) {
            String actionPath = getRequest().getRequestURI();
            String newActionPath = parsePath(actionPath, url);
            JFinalApp.flashManager().updateFlashKey(this.getSession(false), actionPath, newActionPath);
            flashflag = false;
        }
        super.redirect(url);
    }

    @Override
    public void redirect(String url, boolean withQueryString) {
        if (flashflag) {
            String actionPath = this.getRequest().getRequestURI();
            String newActionPath = parsePath(actionPath, url);
            JFinalApp.flashManager().updateFlashKey(this.getSession(false), actionPath, newActionPath);
            flashflag = false;
        }
        super.redirect(url, withQueryString);
    }
}
