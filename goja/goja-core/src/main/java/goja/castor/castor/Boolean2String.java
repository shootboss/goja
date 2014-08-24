/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;


import goja.castor.Castor;
import goja.castor.FailToCastObjectException;

public class Boolean2String extends Castor<Boolean, String> {

    @Override
    public String cast(Boolean src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        return String.valueOf(src);
    }

}
