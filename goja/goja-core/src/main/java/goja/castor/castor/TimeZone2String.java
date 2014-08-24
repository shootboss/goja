/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import goja.castor.Castor;

import java.util.TimeZone;


public class TimeZone2String extends Castor<TimeZone, String> {

    @Override
    public String cast(TimeZone src, Class<?> toType, String... args) {
        return src.getID();
    }

}
