/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import goja.lang.Strings;

import java.util.Calendar;


public class String2Calendar extends DateTimeCastor<String, Calendar> {

    @Override
    public Calendar cast(String src, Class<?> toType, String... args) {
        if (Strings.isBlank(src))
            return null;
        Calendar c = Calendar.getInstance();
        c.setTime(toDate(src));
        return c;
    }

}
