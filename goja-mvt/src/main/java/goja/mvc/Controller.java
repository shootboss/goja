/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.mvc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.jfinal.core.TypeConverter;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;
import goja.Goja;
import goja.Logger;
import goja.kits.base.DateKit;
import goja.mvc.datatables.core.DataSet;
import goja.mvc.datatables.core.DatatablesCriterias;
import goja.mvc.datatables.core.DatatablesResponse;
import goja.mvc.kit.Requests;
import goja.mvc.render.BadRequest;
import goja.mvc.render.CaptchaRender;
import goja.mvc.render.JxlsRender;
import goja.mvc.render.NotModified;
import goja.mvc.security.SecurityKit;
import goja.mvc.security.shiro.AppUser;
import goja.mvc.security.shiro.Securitys;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
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
     * Map Type reference.
     */
    public static final TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE = new TypeReference<Map<String, Object>>() {};

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
    protected <M extends Model> Optional<M> getModelByJson(Class<? extends M> modelClass) {
        try {
            String jsonData = IOUtils.toString(getRequest().getInputStream());
            if (Strings.isNullOrEmpty(jsonData)) {
                return Optional.absent();
            }
            final Map<String, Object> data_map = JSON.parseObject(jsonData, MAP_TYPE_REFERENCE);
            if (data_map == null) {
                return Optional.absent();
            }
            M model = modelClass.newInstance();
            for (String key : data_map.keySet()) {
                model.set(key, data_map.get(key));
            }
            return Optional.of(model);
        } catch (IOException e) {
            Logger.error("parse request json has error!", e);
        } catch (InstantiationException e) {
            Logger.error("parse request json has error!", e);
        } catch (IllegalAccessException e) {
            Logger.error("parse request json has error!", e);
        }
        return Optional.absent();
    }

    /**
     * According to the table fields to obtain values from the Request and converted to the Model
     *
     * @param modelClass The convert class.
     * @param <M>        Generic parameter.
     * @return The modeal .
     */
    protected <M> Optional<M> getModelByRequest(Class<?> modelClass) {
        final HttpServletRequest request = getRequest();
        final Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap.size() > 0) {
            Object model;
            try {
                model = modelClass.newInstance();
            } catch (Exception e) {
                Logger.error("instance the object has error!", e);
                return Optional.absent();
            }
            if (model instanceof Model) {
                final Model db_model = (Model) model;
                Table table = TableMapping.me().getTable(db_model.getClass());
                if (table == null) {
                    Logger.error("the model has note found!");
                } else {
                    final Map<String, Class<?>> columnTypeMap = table.getColumnTypeMap();

                    for (String label : columnTypeMap.keySet()) {
                        final String param_value = request.getParameter(label);
                        final Class<?> column_type = columnTypeMap.get(label);
                        String[] paraValue = parameterMap.get(label);
                        try {
                            Object value = paraValue[0] != null ? TypeConverter.convert(column_type, param_value) : null;
                            db_model.set(label, value);
                        } catch (Exception ex) {
                            Logger.warn("Can not convert parameter: {}, {}, {}. ", label, param_value, column_type);
                            db_model.set(label, param_value);
                        }
                    }
                    return Optional.of((M)db_model);
                }
            } else {
                Method[] methods = modelClass.getMethods();
                for (Method method : methods) {
                    String methodName = method.getName();
                    if (!methodName.startsWith("set"))	// only setter method
                        continue;

                    Class<?>[] types = method.getParameterTypes();
                    if (types.length != 1)						// only one parameter
                        continue;

                    String attrName = methodName.substring(3);
                    String value = request.getParameter(StrKit.firstCharToLowerCase(attrName));
                    if (value != null) {
                        try {
                            method.invoke(model, TypeConverter.convert(types[0], value));
                        } catch (Exception e) {
                                throw new RuntimeException(e);
                        }
                    }
                }
                return Optional.of((M)model);
            }

        }


        return Optional.absent();
    }

    /**
     * For information on the logged in user.
     * <p/>
     * This access is through the way the Cookie and Session
     *
     * @param <M> Generic parameter.
     * @return user model.
     */
    protected <M extends Model> Optional<M> getLogin() {
        final HttpServletRequest request = getRequest();
        if (SecurityKit.isLogin(request)) {
            final M user = SecurityKit.getLoginUser(request);
            return Optional.of(user);
        } else {
            return Optional.absent();
        }
    }

    /**
     * The current Shiro login user.
     * <p/>
     * If it opens the secruity function can call this method to obtain the logged in user.
     *
     * @param <L> Generic parameter.
     * @param <U> Generic parameter.
     * @return Shiro login user.
     */
    protected <L extends Model, U extends Model> Optional<AppUser<L, U>> getPrincipal() {
        if (Securitys.isLogin()) {
            final AppUser<L, U> appUser = Securitys.getLogin();
            return Optional.of(appUser);
        }
        return Optional.absent();
    }


    /**
     * JodaTime time request
     *
     * @param name param.
     *             the datetime format : yyyy-MM-dd
     * @return datetime.
     */
    protected DateTime getDate(String name) {
        return getDate(name, DateTime.now());
    }

    /**
     * JodaTime time request
     *
     * @param name         param.
     *                     the datetime format : yyyy-MM-dd
     * @param defaultValue the default value.
     * @return datetime.
     */
    protected DateTime getDate(String name, DateTime defaultValue) {
        String value = getRequest().getParameter(name);
        if (Strings.isNullOrEmpty(value))
            return defaultValue;
        return DateKit.parseDashYMDDateTime(value);
    }


    /**
     * JodaTime time request
     *
     * @param name param.
     *             the datetime format : yyyy-MM-dd HH:mm:ss
     * @return datetime.
     */
    protected DateTime getDateTime(String name) {
        return getDateTime(name, DateTime.now());
    }

    /**
     * JodaTime time request
     *
     * @param name         param.
     *                     the datetime format : yyyy-MM-dd HH:mm:ss
     * @param defaultValue the default value.
     * @return datetime.
     */
    protected DateTime getDateTime(String name, DateTime defaultValue) {
        String value = getRequest().getParameter(name);
        if (Strings.isNullOrEmpty(value))
            return defaultValue;
        return DateKit.parseDashYMDHMSDateTime(value);
    }
}
