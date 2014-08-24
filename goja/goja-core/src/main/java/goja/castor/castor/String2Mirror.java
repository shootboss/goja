/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import goja.castor.Castor;
import goja.lang.Mirror;

@SuppressWarnings({"rawtypes"})
public class String2Mirror extends Castor<String, Mirror> {

    private static final String2Class castor = new String2Class();

    @Override
    public Mirror<?> cast(String src, Class<?> toType, String... args) {
        return Mirror.me(castor.cast(src, toType));
    }

}
