/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package app.controllers;

import com.jfinal.core.ActionKey;
import goja.Logger;
import goja.mvc.Controller;
import goja.mvc.security.shiro.Securitys;

import java.util.Map;

/**
 * <p>
 * The url main Controller.
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-11-01 15:03
 * @since JDK 1.6
 */
public class MainController extends Controller {

    /**
     * The index route.
     * the url /main
     */
    public void index() {
        Logger.info("haha");
        Map<String,String> a = null;
        a.toString();
        render("/main.ftl");
    }


    @ActionKey("/")
    public void main(){
        redirect(Securitys.isLogin() ? "/main" : "/login");
    }

}