/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

public class String2Short extends String2Number<Short> {

    @Override
    protected Short getPrimitiveDefaultValue() {
        return 0;
    }

    @Override
    protected Short valueOf(String str) {
        return Short.valueOf(str);
    }

}
