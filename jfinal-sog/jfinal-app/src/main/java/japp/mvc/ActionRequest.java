/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package japp.mvc;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-06-01 21:23
 * @since JDK 1.6
 */
public class ActionRequest {


    static final String C = "__continuation";
    static final String A = "__callback";
    static final String F = "__future";


    /**
     * Free space to store your request specific data
     */
    public Map<String, Object> args = Maps.newHashMapWithExpectedSize(16);

    private ActionRequest() {    }
}
