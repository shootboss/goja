/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import java.sql.Timestamp;
import java.util.Calendar;

import goja.castor.Castor;

public class Timestamp2Calendar extends Castor<Timestamp, Calendar> {

    @Override
    public Calendar cast(Timestamp src, Class<?> toType, String... args) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(src.getTime());
        return c;
    }

}
