/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

public class String2Long extends String2Number<Long> {

    @Override
    protected Long getPrimitiveDefaultValue() {
        return 0L;
    }

    @Override
    protected Long valueOf(String str) {
        return Long.valueOf(str);
    }

}
