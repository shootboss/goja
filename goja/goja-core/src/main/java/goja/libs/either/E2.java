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
 * @version 1.0 2014-09-11 13:28
 * @since JDK 1.6
 */
public class E2<A, B> extends Either<A,B>{
    private E2(Option<A> _1, Option<B> _2) {
        super(_1, _2);
    }
}
