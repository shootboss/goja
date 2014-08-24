/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;


public class SqlTime2String extends DateTimeCastor<java.sql.Time, String> {

    @Override
    public String cast(java.sql.Time src, Class<?> toType, String... args) {
        return src.toString();
    }

}
