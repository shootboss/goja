/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

public class String2Float extends String2Number<Float> {

    @Override
    protected Float getPrimitiveDefaultValue() {
        return 0.0f;
    }

    @Override
    protected Float valueOf(String str) {
        return Float.valueOf(str);
    }

}
