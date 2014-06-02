/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package com.github.sog.interceptor;

import japp.JApp;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-03-27 11:38
 * @since JDK 1.6
 */
public class FlashInterceptor implements Interceptor {
    @Override
    /**
     * 该拦截器取得当前ActionPath，从Cache中检查是否有传送给当前Action的Flash对象Map
     * 若有，则遍历Map，并将所有key，value注入到当前的request请求中。
     */
    public void intercept(ActionInvocation ai) {
        final Controller c = ai.getController();
        final HttpSession session = c.getSession(false);
        final String curAction = ai.getViewPath() + ai.getMethodName();
        final Map<String, Object> flashMap = JApp.flashManager().getFlash(session, curAction);
        if (flashMap != null) {
            for (Map.Entry<String, Object> flashEntry : flashMap.entrySet()) {
                c.setAttr(flashEntry.getKey(), flashEntry.getValue());
            }
        }
        ai.invoke();
    }
}
