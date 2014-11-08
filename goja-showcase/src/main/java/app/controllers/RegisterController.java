/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package app.controllers;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.Restful;
import goja.mvc.Controller;

/**
 * <p>
 * The url register Controller.
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-11-08 19:42
 * @since JDK 1.6
 */
@Before(Restful.class)
public class RegisterController extends Controller {

    /**
     * The index route.
     * the url /register
     */
    public void index() {
        render("/register.ftl.html");
    }
}