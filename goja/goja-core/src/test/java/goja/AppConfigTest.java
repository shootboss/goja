/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja;

import org.junit.Test;

import static org.junit.Assert.*;

public class AppConfigTest {

    @Test
    public void testDbConfigName() throws Exception {
        String config = "db.test.url";
        System.out.println(config.substring(config.indexOf(StringPool.DOT)+1,config.lastIndexOf(StringPool.DOT)));

    }
}