/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.libs.tuple;

import com.google.common.base.Objects;

/**
 * <p> . </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-09-11 13:33
 * @since JDK 1.6
 */
public class Tuple<A, B> {


    final public A a;
    final public B b;

    public Tuple(A _a, B _b) {
        this.a = _a;
        this.b = _b;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("a", a)
                .add("b", b)
                .toString();
    }
}
