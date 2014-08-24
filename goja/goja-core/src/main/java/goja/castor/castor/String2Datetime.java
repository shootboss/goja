/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;


import goja.lang.Strings;

public class String2Datetime extends DateTimeCastor<String, java.util.Date> {

    @Override
    public java.util.Date cast(String src, Class<?> toType, String... args) {
        // 处理空白
        if (Strings.isBlank(src))
            return null;
        return toDate(src);
    }

}
