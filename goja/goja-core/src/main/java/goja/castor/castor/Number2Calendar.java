/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import goja.castor.Castor;

import java.util.Calendar;


public class Number2Calendar extends Castor<Number, Calendar> {

    @Override
    public Calendar cast(Number src, Class<?> toType, String... args) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(src.longValue());
        return c;
    }

}
