/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package com.jfinal.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.config.Constants;
import com.jfinal.handler.Handler;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;
import com.jfinal.render.RenderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ActionHandler
 */
final class ActionHandler extends Handler {

    private static final Logger logger = LoggerFactory.getLogger(ActionHandler.class);

    private final boolean       devMode;
    private final ActionMapping actionMapping;
    private static final RenderFactory renderFactory = RenderFactory.me();

    public ActionHandler(ActionMapping actionMapping, Constants constants) {
        this.actionMapping = actionMapping;
        this.devMode = constants.getDevMode();
    }

    /**
     * handle
     * 1: Action action = actionMapping.getAction(target)
     * 2: new ActionInvocation(...).invoke()
     * 3: render(...)
     */
    public final void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        if (target.contains(".")) {
            return;
        }

        isHandled[0] = true;
        String[] urlPara = {null};
        Action action = actionMapping.getAction(target, urlPara);

        if (action == null) {
            if (logger.isWarnEnabled()) {
                String qs = request.getQueryString();
                logger.warn("404 Action Not Found: " + (qs == null ? target : target + "?" + qs));
            }
            renderFactory.getErrorRender(404).setContext(request, response).render();
            return;
        }

        try {
            Controller controller = action.getControllerClass().newInstance();
            controller.init(request, response, urlPara[0]);

            if (devMode) {
                boolean isMultipartRequest = ActionReporter.reportCommonRequest(controller, action);
				new ActionInvocation(action, controller).invoke();
				if (isMultipartRequest) ActionReporter.reportMultipartRequest(controller, action);
			}
			else {
				new ActionInvocation(action, controller).invoke();
			}
			
			Render render = controller.getRender();
			if (render instanceof ActionRender) {
				String actionUrl = ((ActionRender)render).getActionUrl();
				if (target.equals(actionUrl))
					throw new RuntimeException("The forward action url is the same as before.");
				else
					handle(actionUrl, request, response, isHandled);
				return ;
			}
			
			if (render == null)
				render = renderFactory.getDefaultRender(action.getViewPath() + action.getMethodName());
			render.setContext(request, response, action.getViewPath()).render();
		}
		catch (RenderException e) {
			if (logger.isErrorEnabled()) {
				String qs = request.getQueryString();
                logger.error(qs == null ? target : target + "?" + qs, e);
			}
		}
		catch (ActionException e) {
			int errorCode = e.getErrorCode();
			if (errorCode == 404 && logger.isWarnEnabled()) {
				String qs = request.getQueryString();
                logger.warn("404 Not Found: " + (qs == null ? target : target + "?" + qs));
			}
			else if (errorCode == 401 && logger.isWarnEnabled()) {
				String qs = request.getQueryString();
                logger.warn("401 Unauthorized: " + (qs == null ? target : target + "?" + qs));
			}
			else if (errorCode == 403 && logger.isWarnEnabled()) {
				String qs = request.getQueryString();
                logger.warn("403 Forbidden: " + (qs == null ? target : target + "?" + qs));
			}
			else if (logger.isErrorEnabled()) {
				String qs = request.getQueryString();
                logger.error(qs == null ? target : target + "?" + qs, e);
			}
			e.getErrorRender().setContext(request, response).render();
		}
		catch (Exception e) {
			if (logger.isErrorEnabled()) {
				String qs = request.getQueryString();
                logger.error(qs == null ? target : target + "?" + qs, e);
			}
			renderFactory.getErrorRender(500).setContext(request, response).render();
		}
	}
}





