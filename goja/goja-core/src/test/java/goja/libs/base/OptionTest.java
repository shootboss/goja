/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.libs.base;

import goja.libs.F;

public class OptionTest {

    public Option<Double> div(double a, double b) {
        if (b == 0)
            return new None();
        else
            return F.Some(a / b);
    }

}