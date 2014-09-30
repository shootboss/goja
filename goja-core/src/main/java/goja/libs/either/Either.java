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
 * @version 1.0 2014-09-11 13:27
 * @since JDK 1.6
 */
public class Either<A, B> {

    final public Option<A> _1;
    final public Option<B> _2;

    protected Either(Option<A> _1, Option<B> _2) {
        this._1 = _1;
        this._2 = _2;
    }


    public static <A, B> Either<A, B> _1(A value) {
        return new Either(Some(value), Option.None());
    }

    public static <A, B> Either<A, B> _2(B value) {
        return new Either(Option.None(), Some(value));
    }

    @Override
    public String toString() {
        return "E2(_1: " + _1 + ", _2: " + _2 + ")";
    }
}
