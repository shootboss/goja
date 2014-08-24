/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import goja.castor.Castor;

import java.util.Calendar;


public class Calendar2Long extends Castor<Calendar, Long> {
    @Override
    public Long cast(Calendar src, Class<?> toType, String... args) {
        return src.getTimeInMillis();
    }
}
