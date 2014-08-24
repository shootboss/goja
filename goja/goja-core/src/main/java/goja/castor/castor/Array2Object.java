/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import goja.castor.Castor;
import goja.castor.Castors;
import goja.castor.FailToCastObjectException;

import java.lang.reflect.Array;


public class Array2Object extends Castor<Object, Object> {

    public Array2Object() {
        this.fromClass = Array.class;
        this.toClass = Object.class;
    }

    @Override
    public Object cast(Object src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        if (Array.getLength(src) == 0)
            return null;
        return Castors.me().castTo(Array.get(src, 0), toType);
    }

}
