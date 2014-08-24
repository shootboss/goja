/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;


import goja.kits.date.DateProvider;
import goja.lang.Times;

public class Datetime2String extends DateTimeCastor<java.util.Date, String> {
    private String format = DateProvider.YYYY_MM_DD_HH_MM_SS;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String cast(java.util.Date src, Class<?> toType, String... args) {
        //return Times.sDT(src);
        return Times.format(format, src);
    }

}
