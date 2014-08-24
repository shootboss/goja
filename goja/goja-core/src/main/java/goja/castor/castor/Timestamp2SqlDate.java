/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import java.sql.Date;
import java.sql.Timestamp;

import goja.castor.Castor;
import goja.castor.FailToCastObjectException;

public class Timestamp2SqlDate extends Castor<Timestamp, Date> {

    @Override
    public Date cast(Timestamp src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        return new Date(src.getTime());
    }

}
