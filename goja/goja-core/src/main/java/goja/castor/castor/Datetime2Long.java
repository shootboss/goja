/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import goja.castor.Castor;

import java.util.Date;

public class Datetime2Long extends Castor<Date, Long> {

    @Override
    public Long cast(Date src, Class<?> toType, String... args) {
        return src.getTime();
    }

}
