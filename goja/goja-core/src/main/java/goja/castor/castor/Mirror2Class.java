/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;


import goja.castor.Castor;
import goja.lang.Mirror;

@SuppressWarnings({"rawtypes"})
public class Mirror2Class extends Castor<Mirror, Class> {

    @Override
    public Class cast(Mirror src, Class toType, String... args) {
        return src.getType();
    }

}
