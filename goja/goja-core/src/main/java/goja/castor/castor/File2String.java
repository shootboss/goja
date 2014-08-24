/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import goja.castor.Castor;
import goja.castor.FailToCastObjectException;

import java.io.File;


public class File2String extends Castor<File, String> {

    @Override
    public String cast(File src, Class<?> toType, String... args) throws
            FailToCastObjectException {
        return src.getAbsolutePath();
    }

}
