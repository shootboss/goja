/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import java.util.TimeZone;

import goja.castor.Castor;
import goja.lang.Strings;

public class String2TimeZone extends Castor<String, TimeZone> {

    @Override
    public TimeZone cast(String src, Class<?> toType, String... args) {
        if (Strings.isBlank(src))
            return null;
        return TimeZone.getTimeZone(src);
    }

}
