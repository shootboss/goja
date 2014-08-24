/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;

import java.util.Set;

import com.alibaba.fastjson.JSON;
import goja.castor.Castor;
import goja.castor.FailToCastObjectException;

@SuppressWarnings("rawtypes")
public class String2Set extends Castor<String, Set> {

    @Override
    public Set cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
        return JSON.parseObject(src,Set.class);
    }

}