/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.libs.either;

import goja.libs.base.Option;

import static goja.libs.F.Some;

/**
 * <p> . </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-09-11 13:32
 * @since JDK 1.6
 */
public class E3<A, B, C> {

    final public Option<A> _1;
    final public Option<B> _2;
    final public Option<C> _3;

    private E3(Option<A> _1, Option<B> _2, Option<C> _3) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
    }

    public static <A, B, C> E3<A, B, C> _1(A value) {
        return new E3(Some(value), Option.None(), Option.None());
    }

    public static <A, B, C> E3<A, B, C> _2(B value) {
        return new E3(Option.None(), Some(value), Option.None());
    }

    public static <A, B, C> E3<A, B, C> _3(C value) {
        return new E3(Option.None(), Option.None(), Some(value));
    }

    @Override
    public String toString() {
        return "E3(_1: " + _1 + ", _2: " + _2 + ", _3:" + _3 + ")";
    }
}
