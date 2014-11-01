/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.mvc;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import goja.Goja;
import goja.Logger;
import goja.mvc.datatables.core.DataSet;
import goja.mvc.datatables.core.DatatablesCriterias;
import goja.mvc.datatables.core.DatatablesResponse;
import goja.mvc.kit.Requests;
import goja.mvc.render.BadRequest;
import goja.mvc.render.CaptchaRender;
import goja.mvc.render.FreeMarkerXMLRender;
import goja.mvc.render.JxlsRender;
import goja.mvc.render.NotModified;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import static goja.StringPool.EMPTY;
import static goja.StringPool.SLASH;

/**
 * <p>
 * Controller.
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-06-01 20:29
 * @since JDK 1.6
 */
@SuppressWarnings("UnusedDeclaration")
public class Controller extends com.jfinal.core.Controller {

    /**
     * Render four string verification code.
     */
    protected void renderCaptcha() {
        renderCaptcha(4);
    }

    /**
     * Render the specified digit verification code.
     *
     * @param randNum The length of the verification code.
     */
    protected void renderCaptcha(int randNum) {
        CaptchaRender captchaRender = new CaptchaRender(randNum);
        if (Logger.isDebugEnabled()) {
            Logger.debug("The Captcha char is {}", captchaRender.getMd5RandonCode());
        }
        render(captchaRender);
    }

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

    /**
     * According to Excel template, rendering the Excel, and available to download.
     *
     * @param templateFile excel template.
     * @param datas        the export data.
     */
    protected static void renderExcel(String templateFile, Map<String, Object> datas) {
        (new JxlsRender(templateFile).beans(datas)).render();
    }

    @Override
    public void render(String view) {

        super.render((view.startsWith(SLASH))
                ? (Goja.viewPath + SLASH + view.replaceFirst(SLASH, EMPTY))
                : view);

    }

    /**
     * Render data in XML format.
     *
     * @param view freemarket view.
     */
    protected void renderXml(String view) {
        render(new FreeMarkerXMLRender(view));
    }

    /**
     * Rendering errors information, in Json format.
     *
     * @param error error message.
     */
    protected void renderAjaxError(String error) {
        renderJson(AjaxMessage.error(error));
    }

    /**
     * Rendering errors information, in Json format.
     *
     * @param data error data.
     */
    protected <T> void renderAjaxError(T data) {
        renderJson(AjaxMessage.error(data));
    }

    /**
     * Rendering errors information, in Json format.
     *
     * @param error errors information.
     * @param e     exception.
     */
    protected void renderAjaxError(String error, Exception e) {
        renderJson(AjaxMessage.error(error, e));
    }

    /**
     * In the form of JSON rendering failure information.
     *
     * @param failure failure information.
     */
    protected void renderAjaxFailure(String failure) {
        renderJson(AjaxMessage.failure(failure));
    }

    /**
     * In the form of JSON rendering failure information.
     */
    protected <T> void renderAjaxFailure() {
        renderJson(AjaxMessage.failure());
    }

    /**
     * In the form of JSON rendering forbidden information.
     */
    protected void renderAjaxForbidden() {
        renderJson(AjaxMessage.forbidden());
    }

    /**
     * In the form of JSON rendering forbidden information.
     *
     * @param data the forbidden data.
     * @param <T>  Generic parameter.
     */
    protected <T> void renderAjaxForbidden(T data) {
        renderJson(AjaxMessage.forbidden(data));
    }

    /**
     * In the form of JSON rendering forbidden information.
     *
     * @param message the forbidden message.
     * @param data    the forbidden data.
     * @param <T>     Generic parameter.
     */
    protected <T> void renderAjaxForbidden(String message, T data) {
        renderJson(AjaxMessage.forbidden(message, data));
    }

    /**
     * In the form of JSON rendering success information.
     *
     * @param message success information.
     */
    protected void renderAjaxSuccess(String message) {
        renderJson(AjaxMessage.ok(message));
    }

    /**
     * In the form of JSON rendering default success information.
     */
    protected void renderAjaxSuccess() {
        renderJson(AjaxMessage.ok());
    }

    /**
     * With the success of data information.
     *
     * @param data the render data.
     * @param <T>  Generic parameter.
     */
    protected <T> void renderAjaxSuccess(T data) {
        renderJson(AjaxMessage.ok(data));
    }

    /**
     * News Ajax rendering not logged in.
     */
    protected void renderAjaxNologin() {
        renderJson(AjaxMessage.nologin());
    }

    /**
     * News Ajax rendering not logged in.
     *
     * @param data the render data.
     * @param <T>  Generic parameter.
     */
    protected <T> void renderAjaxNologin(T data) {
        renderJson(AjaxMessage.nologin(data));
    }

    /**
     * Render the empty data.
     */
    protected void renderNodata() {
        renderJson(AjaxMessage.nodata());
    }


    /**
     * Rendering errors information, in Json format.
     *
     * @param error error message.
     * @deprecated please user {@link goja.mvc.Controller#renderAjaxError(String)}
     */
    protected void renderError(String error) {
        renderJson(AjaxMessage.error(error));
    }

    /**
     * Rendering errors information, in Json format.
     *
     * @param data error message.
     * @deprecated please user {@link goja.mvc.Controller#renderAjaxError(T)}
     */
    protected <T> void renderError(T data) {
        renderJson(AjaxMessage.error(data));
    }

    /**
     * In the form of JSON rendering failure information.
     *
     * @param failure failure information.
     * @deprecated please user {@link goja.mvc.Controller#renderAjaxFailure(String)}
     */
    protected void renderFailure(String failure) {
        renderJson(AjaxMessage.failure(failure));
    }

    /**
     * In the form of JSON rendering success information.
     *
     * @param message success information.
     * @deprecated please user {@link goja.mvc.Controller#renderAjaxSuccess(String)}
     */
    protected void renderSuccess(String message) {
        renderJson(AjaxMessage.ok(message));
    }

    /**
     * In the form of JSON rendering default success information.
     *
     * @deprecated please user {@link goja.mvc.Controller#renderAjaxSuccess()}
     */
    protected void renderSuccess() {
        renderJson(AjaxMessage.ok());
    }

    /**
     * With the success of data information.
     *
     * @param data the render data.
     * @param <T>  Generic parameter.
     * @deprecated please user {@link goja.mvc.Controller#renderAjaxSuccess(T)}
     */
    protected <T> void renderSuccess(T data) {
        renderJson(AjaxMessage.ok(data));
    }

    /**
     * Rendering errors information, in Json format.
     *
     * @param error errors information.
     * @param e     exception.
     * @deprecated please user {@link goja.mvc.Controller#renderAjaxError(String, java.lang.Exception)}
     */
    protected void renderError(String error, Exception e) {
        renderJson(AjaxMessage.error(error, e));
    }


    /**
     * Render the specified view as a string.
     *
     * @param view view template.
     * @return the string.
     * @deprecated please
     */
    protected String renderTpl(String view) {
        return template(view);
    }

    /**
     * Render view as a string
     *
     * @param view view
     * @return render string.
     */
    protected String template(String view) {
        final Enumeration<String> attrs = getAttrNames();
        final Map<String, Object> root = Maps.newHashMap();
        while (attrs.hasMoreElements()) {
            String attrName = attrs.nextElement();
            root.put(attrName, getAttr(attrName));
        }
        return Freemarkers.processString(view, root);
    }

    /**
     * Rendering todo prompt
     */
    protected void renderTODO() {
        renderJson(AjaxMessage.developing());
    }


    /**
     * Based on the current path structure is going to jump full Action of the path
     *
     * @param currentActionPath The current path, similar to/sau/index
     * @param url               The next path, similar to/au/login, the detail? The admin/detail.
     * @return An Action under the full path.
     */
    protected String parsePath(String currentActionPath, String url) {
        if (url.startsWith(SLASH)) {//完整路径
            return url.split("\\?")[0];
        } else if (!url.contains(SLASH)) {//类似于detail的路径。
            return SLASH + currentActionPath.split(SLASH)[1] + SLASH + url.split("\\?")[0];
        } else if (url.contains("http:") || url.contains("https:")) {
            return null;
        }
        ///abc/def","bcd/efg?abc
        return currentActionPath + SLASH + url.split("\\?")[0];
    }


    /**
     * The Request is ajax with return <code>true</code>.
     *
     * @return true the request is ajax request.
     */
    protected boolean isAjax() {
        return Requests.ajax(getRequest());
    }

    /**
     * Get parameters from the Request and encapsulation as an object for processing。
     *
     * @return jquery DataTables参数信息
     */
    protected DatatablesCriterias getCriterias() {
        return DatatablesCriterias.criteriasWithRequest(getRequest());
    }

    /**
     * The source of data for rendering the jQuery Datatables
     *
     * @param datas     The data.
     * @param criterias datatable criterias.
     * @param <E>       Generic parameter.
     */
    protected <E> void renderDataTables(Page<E> datas, DatatablesCriterias criterias) {
        Preconditions.checkNotNull(criterias, "datatable criterias is must be not null.");
        DataSet<E> dataSet = DataSet.newSet(datas.getList(), datas.getTotalRow(), datas.getTotalRow());
        DatatablesResponse<E> response = DatatablesResponse.build(dataSet, criterias);
        renderJson(response);
    }

    /**
     * rendering the empty datasource.
     *
     * @param criterias datatable criterias.
     */
    protected void renderEmptyDataTables(DatatablesCriterias criterias) {
        Preconditions.checkNotNull(criterias, "datatable criterias is must be not null.");
        DatatablesResponse response = DatatablesResponse.build(EMPTY_DATASET, criterias);
        renderJson(response);
    }

    /**
     * Empty data set.
     */
    private static final DataSet EMPTY_DATASET = DataSet.newSet(Collections.EMPTY_LIST, 0l, 0l);

    /**
     * Converting the JSON data Modal.
     *
     * @param modelClass model class.
     * @param <M>        Generic parameter.
     * @return Modal.
     */
    protected <M> M getModelByJson(Class<? extends Model> modelClass) {
        String jsonData = getPara();
        return null;
    }
}
