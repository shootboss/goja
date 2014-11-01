/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package goja.mvc.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.render.Render;
import com.jfinal.render.RenderFactory;
import goja.StringPool;

import java.util.Locale;

public class I18nInterceptor implements Interceptor {
    private String defaultLanguage = "zh";
    private String defaultCountry  = "CN";
    private String languagePara    = "language";
    private String countryPara     = "country";
    private String localePara      = "locale";
    private String skipFlagPara    = "skipi18n";

    @Override
    public void intercept(ActionInvocation ai) {
        Controller controller = ai.getController();
        String language = controller.getAttr(languagePara);
        if (StrKit.isBlank(language)) {
            language = controller.getPara(languagePara, defaultLanguage);
        }
        String country = controller.getAttr(countryPara);
        if (StrKit.isBlank(country)) {
            country = controller.getPara(countryPara, defaultCountry);
        }
        Locale locale = new Locale(language, country);
        controller.setLocaleToCookie(locale);
        controller.setAttr(localePara, locale);
        ai.invoke();
        if (Boolean.TRUE.equals(ai.getController().getAttr(skipFlagPara))) {
            return;
        }
        Render render = controller.getRender();
        if (render == null) {
            render = RenderFactory.me().getDefaultRender(ai.getMethodName());
        }
        String view = render.getView();

        String prefix = getPrefix(country, language);

        if (view.startsWith(StringPool.SLASH)) {
            view = StringPool.SLASH + prefix + StringPool.SLASH + view.substring(1, view.length());
        } else {
            view = StringPool.SLASH + prefix + ai.getViewPath() + view;
        }
    }

    private String getPrefix(String country, String language) {
        String prefix = language;
        if (StrKit.notBlank(country)) {
            prefix = language + StringPool.UNDERSCORE + country;
        }
        //TODO
        // ScriptEngineManager sem = new ScriptEngineManager();
        // ScriptEngine se = sem.getEngineByName("javascript");
        // try {
        // se.put("country",country);
        // se.put("language", language);
        // prefix = (String) se.eval(exps);
        // } catch (ScriptException e) {
        // Throwables.propagate(e);
        // }
        return prefix;
    }

    public I18nInterceptor defaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
        return this;
    }

    public I18nInterceptor defaultCountry(String defaultCountry) {
        this.defaultCountry = defaultCountry;
        return this;
    }

    public I18nInterceptor languagePara(String languagePara) {
        this.languagePara = languagePara;
        return this;
    }

    public I18nInterceptor countryPara(String countryPara) {
        this.countryPara = countryPara;
        return this;
    }

    public I18nInterceptor localePara(String localePara) {
        this.localePara = localePara;
        return this;
    }

    public I18nInterceptor skipFlagPara(String skipFlagPara) {
        this.skipFlagPara = skipFlagPara;
        return this;
    }

}
