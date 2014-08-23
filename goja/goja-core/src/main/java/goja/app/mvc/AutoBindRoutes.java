/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package goja.app.mvc;

import goja.annotation.ControllerBind;
import com.google.common.base.Preconditions;
import com.jfinal.config.Routes;
import com.jfinal.kit.StrKit;
import goja.Logger;
import goja.app.StringPool;
import goja.init.ctxbox.ClassBox;
import goja.init.ctxbox.ClassType;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@SuppressWarnings("unchecked")
public class AutoBindRoutes extends Routes {


    private static final String suffix = "Controller";

    @Override
    public void config() {
        List<Class> controllerClasses = ClassBox.getInstance().getClasses(ClassType.CONTROLLER);
        if (controllerClasses != null && !controllerClasses.isEmpty()) {
            ControllerBind controllerBind;
            for (Class controller : controllerClasses) {
                controllerBind = (ControllerBind) controller.getAnnotation(ControllerBind.class);
                if (controllerBind == null) {
                    final String controllerKey = controllerKey(controller);
                    this.add(controllerKey, controller);
                    Logger.debug("routes.add(" + controllerKey + ", " + controller.getName() + StringPool.RIGHT_BRACKET);
                } else if (StrKit.isBlank(controllerBind.viewPath())) {
                    this.add(controllerBind.controllerKey(), controller);
                    Logger.debug("routes.add(" + controllerBind.controllerKey() + ", " + controller.getName() + StringPool.RIGHT_BRACKET);
                } else {
                    this.add(controllerBind.controllerKey(), controller, controllerBind.viewPath());
                    Logger.debug("routes.add(" + controllerBind.controllerKey() + ", " + controller + StringPool.COMMA
                            + controllerBind.viewPath() + StringPool.RIGHT_BRACKET);
                }
            }
        }
    }

    private static String controllerKey(Class clazz) {
        final String simpleName = clazz.getSimpleName();
        Preconditions.checkArgument(simpleName.endsWith(suffix),
                " does not has a @ControllerBind annotation and it's name is not end with " + suffix);
        String controllerKey = StringPool.SLASH + StrKit.firstCharToLowerCase(simpleName);
        controllerKey = controllerKey.substring(0, controllerKey.indexOf(suffix));

        String pak_name = clazz.getPackage().getName();
        if (StringUtils.endsWith(pak_name, "controllers")) {
            return controllerKey;
        } else {
            String biz_pk = StringUtils.substringAfter(pak_name, ".controllers.");
            biz_pk = biz_pk.replace(StringPool.DOT, StringPool.SLASH);
            controllerKey = StringPool.SLASH + biz_pk + controllerKey;
            return controllerKey;
        }

    }


}
