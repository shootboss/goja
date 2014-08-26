/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.el;

import goja.lang.Lang;
import goja.lang.util.Context;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ElTest {

    @Test
    public void testTest() throws Exception {

        final int eval = El.eval("3+4*5");
        assertEquals(eval, 23);
    }

    @Test
    public void testContext() throws Exception {
        Context context = Lang.context();
        context.set("a", 10);
        final int val = El.eval(context, "a*10");
        assertEquals(val, 100);

    }
}