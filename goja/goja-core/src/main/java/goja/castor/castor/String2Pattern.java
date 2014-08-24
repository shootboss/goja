/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import java.util.regex.Pattern;

import goja.castor.Castor;
import goja.castor.FailToCastObjectException;
import goja.lang.Lang;

public class String2Pattern extends Castor<String, Pattern> {

    @Override
    public Pattern cast(String src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        try {
            return Pattern.compile(src);
        }
        catch (Exception e) {
            throw new FailToCastObjectException("Error regex: " + src, Lang.unwrapThrow(e));
        }
    }

}
