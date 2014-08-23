/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package com.github.sog.kit.cst;

import com.google.common.collect.Lists;
import goja.AppFunc;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-02-16 21:55
 * @since JDK 1.6
 */
public class AppFuncTest {

    @Test
    public void testCommaJoiner() throws Exception {
        String abc = "a,b,c";
        List<String> src_strs = Lists.newArrayList("a", "b", "c");
        String dest = AppFunc.COMMA_JOINER.join(src_strs);
        Assert.assertEquals(abc, dest);
    }

    @Test
    public void testCommaSplitter() throws Exception {
        String abc = "a,b,c";
        List<String> src_strs = Lists.newArrayList("a", "b", "c");
        List<String> dest = AppFunc.COMMA_SPLITTER.splitToList(abc);
        Assert.assertEquals(src_strs, dest);
    }

    @Test
    public void testDashJoiner() throws Exception {
        String abc = "a-b-c";
        List<String> src_strs = Lists.newArrayList("a", "b", "c");
        String dest = AppFunc.DASH_JOINER.join(src_strs);
        Assert.assertEquals(abc, dest);
    }

    @Test
    public void testDashSplitter() throws Exception {

        String abc = "a-b-c";
        List<String> src_strs = Lists.newArrayList("a", "b", "c");
        List<String> dest = AppFunc.DASH_SPLITTER.splitToList(abc);
        Assert.assertEquals(src_strs, dest);

    }

    @Test
    public void testDotJoiner() throws Exception {
        String abc = "a.b.c";
        List<String> src_strs = Lists.newArrayList("a", "b", "c");
        String dest = AppFunc.DOT_JOINER.join(src_strs);
        Assert.assertEquals(abc, dest);
    }

    @Test
    public void testDotSplitter() throws Exception {

        String abc = "a.b.c";
        List<String> src_strs = Lists.newArrayList("a", "b", "c");
        List<String> dest = AppFunc.DOT_SPLITTER.splitToList(abc);
        Assert.assertEquals(src_strs, dest);

    }
}
