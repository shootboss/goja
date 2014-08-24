/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import goja.castor.Castor;
import goja.castor.FailToCastObjectException;

import java.util.Calendar;
import java.util.Date;


public class Datetime2Calendar extends Castor<Date, Calendar> {

    @Override
    public Calendar cast(Date src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        Calendar c = Calendar.getInstance();
        c.setTime(src);
        return c;
    }

}
