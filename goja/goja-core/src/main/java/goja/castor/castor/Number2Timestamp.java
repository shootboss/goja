/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import goja.castor.Castor;

import java.sql.Timestamp;


public class Number2Timestamp extends Castor<Number, Timestamp> {

    @Override
    public Timestamp cast(Number src, Class<?> toType, String... args) {
        return new Timestamp(src.longValue());
    }

}
