/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;


import goja.castor.Castor;

public class Character2Number extends Castor<Character, Number> {

    @Override
    public Number cast(Character src, Class<?> toType, String... args) {
        return (int) src;
    }

}
