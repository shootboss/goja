/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package test.controller;

import japp.JApp;
import com.github.sog.test.ControllerTestCase;
import org.junit.Test;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-02-24 11:02
 * @since JDK 1.6
 */
public class IndexControllerTest extends ControllerTestCase<JApp> {


    @Test
    public void testIndex() throws Exception {
        String url = "/index";
        String resp = use(url).invoke();
        System.out.println(resp);
    }

    @Test
    public void testFilter() throws Exception {
        String url = "/index/filter";
        String resp = use(url).invoke();
        System.out.println(resp);

    }
}
