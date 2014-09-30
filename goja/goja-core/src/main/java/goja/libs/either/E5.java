/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.libs.either;

import goja.libs.base.Option;


/**
 * <p> . </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-09-11 13:33
 * @since JDK 1.6
 */
public class E5<A, B, C, D, E> {

    final public Option<A> _1;
    final public Option<B> _2;
    final public Option<C> _3;
    final public Option<D> _4;
    final public Option<E> _5;

    private E5(Option<A> _1, Option<B> _2, Option<C> _3, Option<D> _4, Option<E> _5) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
    }

    public Option<A> get_1() {
        return _1;
    }

    public Option<B> get_2() {
        return _2;
    }

    public Option<C> get_3() {
        return _3;
    }

    public Option<D> get_4() {
        return _4;
    }

    public Option<E> get_5() {
        return _5;
    }

    public static <A, B, C, D, E> E5<A, B, C, D, E> _1(A value) {
        return new E5(Option.Some(value), Option.None(), Option.None(), Option.None(), Option.None());
    }

    public static <A, B, C, D, E> E5<A, B, C, D, E> _2(B value) {
        return new E5(Option.None(), Option.Some(value), Option.None(), Option.None(), Option.None());
    }

    public static <A, B, C, D, E> E5<A, B, C, D, E> _3(C value) {
        return new E5(Option.None(), Option.None(), Option.Some(value), Option.None(), Option.None());
    }

    public static <A, B, C, D, E> E5<A, B, C, D, E> _4(D value) {
        return new E5(Option.None(), Option.None(), Option.None(), Option.Some(value), Option.None());
    }

    public static <A, B, C, D, E> E5<A, B, C, D, E> _5(E value) {
        return new E5(Option.None(), Option.None(), Option.None(), Option.None(), Option.Some(value));
    }

    @Override
    public String toString() {
        return "E5(_1: " + _1 + ", _2: " + _2 + ", _3:" + _3 + ", _4:" + _4 + ", _5:" + _5 + ")";
    }
}
