/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import goja.castor.Castor;
import goja.castor.FailToCastObjectException;

import java.util.Date;


public class Datetime2SqlDate extends Castor<Date, java.sql.Date> {

    @Override
    public java.sql.Date cast(Date src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        return new java.sql.Date(src.getTime());
    }

}
