/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import java.sql.Timestamp;

import goja.castor.Castor;

public class Timestamp2Long extends Castor<Timestamp, Long> {

    @Override
    public Long cast(Timestamp src, Class<?> toType, String... args) {
        return src.getTime();
    }

}
