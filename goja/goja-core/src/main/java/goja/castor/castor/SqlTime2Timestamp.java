/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import java.sql.Time;
import java.sql.Timestamp;

import goja.castor.Castor;
import goja.castor.FailToCastObjectException;

public class SqlTime2Timestamp extends Castor<Time, Timestamp> {

    @Override
    public Timestamp cast(Time src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        return new Timestamp(src.getTime());
    }

}
