/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import goja.castor.Castor;

import java.util.Date;


public class Number2Datetime extends Castor<Number, Date> {

    @Override
    public Date cast(Number src, Class<?> toType, String... args) {
        return new Date(src.longValue());
    }

}
