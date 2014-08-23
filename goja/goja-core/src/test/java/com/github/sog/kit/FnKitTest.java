/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package com.github.sog.kit;

import com.alibaba.druid.util.JdbcUtils;
import goja.kits.common.FnKit;
import goja.kits.date.DateProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-02-07 12:15
 * @since JDK 1.6
 */
public class FnKitTest {
    @Test
    public void testContains() throws Exception {
        final Date currentDate = DateProvider.DEFAULT.getCurrentDate();
        Object[] src = new Object[]{"0", 1, currentDate};
        boolean cot = FnKit.contains(src, currentDate);
        Assert.assertTrue(cot);
    }

    @Test
    public void testContainsIgnoreCase() throws Exception {

    }

    @Test
    public void testIndexOf() throws Exception {

    }

    @Test
    public void testDateadd() throws Exception {

    }

    @Test
    public void testDatediff() throws Exception {

    }

    @Test
    public void testJdbcDriver() throws Exception {
        System.out.println(JdbcUtils.getDriverClassName("jdbc:h2:mem:jfinal_example"));

    }
}
