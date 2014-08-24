/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;


import goja.castor.Castor;
import goja.castor.FailToCastObjectException;

@SuppressWarnings({"rawtypes"})
public class Enum2String extends Castor<Enum, String> {

    @Override
    public String cast(Enum src, Class<?> toType, String... args) throws FailToCastObjectException {
        return src.name();
    }
}
