/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import goja.castor.Castor;
import goja.castor.FailToCastObjectException;
import goja.lang.Lang;

import java.lang.reflect.Array;


public class Array2Array extends Castor<Object, Object> {

    public Array2Array() {
        this.fromClass = Array.class;
        this.toClass = Array.class;
    }

    @Override
    public Object cast(Object src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        return Lang.array2array(src, toType.getComponentType());
    }

}
