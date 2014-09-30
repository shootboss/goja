/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.libs.tuple;

/**
 * <p> . </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-09-11 13:34
 * @since JDK 1.6
 */
public class T3<A, B, C> {

    final public A _1;
    final public B _2;
    final public C _3;

    public T3(A _1, B _2, C _3) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
    }

    @Override
    public String toString() {
        return "T3(_1: " + _1 + ", _2: " + _2 + ", _3:" + _3 + ")";
    }
}
