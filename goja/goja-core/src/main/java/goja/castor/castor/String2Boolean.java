/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import goja.castor.Castor;
import goja.lang.Lang;
import goja.lang.Strings;

public class String2Boolean extends Castor<String, Boolean> {

    @Override
    public Boolean cast(String src, Class<?> toType, String... args) {
        return !Strings.isBlank(src) && Lang.parseBoolean(src);
    }

}
