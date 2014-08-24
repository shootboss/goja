/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;


import goja.castor.Castor;
import goja.lang.Mirror;

@SuppressWarnings({"unchecked", "rawtypes"})
public class Class2Mirror extends Castor<Class, Mirror> {

    @Override
    public Mirror<?> cast(Class src, Class toType, String... args) {
        return Mirror.me(src);
    }

}
