/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package app.controllers;

import goja.mvc.Controller;

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
        render("/main.ftl");
    }
}