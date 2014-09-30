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
public class E4<A, B, C, D> {

    final public Option<A> _1;
    final public Option<B> _2;
    final public Option<C> _3;
    final public Option<D> _4;

    private E4(Option<A> _1, Option<B> _2, Option<C> _3, Option<D> _4) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
    }

    public static <A, B, C, D> E4<A, B, C, D> _1(A value) {
        return new E4(Option.Some(value), Option.None(), Option.None(), Option.None());
    }

    public static <A, B, C, D> E4<A, B, C, D> _2(B value) {
        return new E4(Option.None(), Some(value), Option.None(), Option.None());
    }

    public static <A, B, C, D> E4<A, B, C, D> _3(C value) {
        return new E4(Option.None(), Option.None(), Some(value), Option.None());
    }

    public static <A, B, C, D> E4<A, B, C, D> _4(D value) {
        return new E4(Option.None(), Option.None(), Option.None(), Some(value));
    }

    @Override
    public String toString() {
        return "E4(_1: " + _1 + ", _2: " + _2 + ", _3:" + _3 + ", _4:" + _4 + ")";
    }
}
