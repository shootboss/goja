/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

public class String2Byte extends String2Number<Byte> {

    @Override
    protected Byte getPrimitiveDefaultValue() {
        return (byte) 0;
    }

    @Override
    protected Byte valueOf(String str) {
        return Byte.valueOf(str);
    }

}
