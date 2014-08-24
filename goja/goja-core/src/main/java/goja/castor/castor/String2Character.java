/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import goja.castor.Castor;
import goja.castor.FailToCastObjectException;

public class String2Character extends Castor<String, Character> {

    @Override
    public Character cast(String src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        return src.charAt(0);
    }

}
