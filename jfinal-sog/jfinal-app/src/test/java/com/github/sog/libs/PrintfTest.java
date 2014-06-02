/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package com.github.sog.libs;

import japp.libs.Printf;
import org.junit.Test;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-04-04 10:59
 * @since JDK 1.6
 */
public class PrintfTest {
    @Test
    public void testStr() throws Exception {
        System.out.println(Printf.str("%s", "xxx"));
    }
}
