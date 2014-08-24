/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;


import goja.castor.Castor;

public class Number2Integer extends Castor<Number, Integer> {

    @Override
    public Integer cast(Number src, Class<?> toType, String... args) {
        return src.intValue();
    }

}
