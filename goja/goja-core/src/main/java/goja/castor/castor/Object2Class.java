/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;


import goja.castor.Castor;
import goja.castor.FailToCastObjectException;

@SuppressWarnings({"rawtypes"})
public class Object2Class extends Castor<Object, Class> {

    @Override
    public Class cast(Object src, Class<?> toType, String... args) throws FailToCastObjectException {
        return src.getClass();
    }

}
