/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package app.models;

import goja.test.ModelTestCase;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest extends ModelTestCase{

    @Test
    public void testFindByUsername() throws Exception {
User.dao.findByUsername("abc");
    }
}