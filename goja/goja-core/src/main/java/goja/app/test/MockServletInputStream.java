/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package goja.app.test;


import goja.app.StringPool;

import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class MockServletInputStream extends ServletInputStream {
    private InputStream is;

    public MockServletInputStream(String str) throws UnsupportedEncodingException {
        is = new ByteArrayInputStream(str.getBytes(StringPool.UTF_8));
    }

    @Override
    public int read() throws IOException {
        return is.read();
    }
}
