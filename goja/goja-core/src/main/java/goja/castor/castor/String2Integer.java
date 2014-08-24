/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

public class String2Integer extends String2Number<Integer> {

    @Override
    protected Integer getPrimitiveDefaultValue() {
        return 0;
    }

    @Override
    protected Integer valueOf(String str) {
        return Integer.valueOf(str);
    }

}
