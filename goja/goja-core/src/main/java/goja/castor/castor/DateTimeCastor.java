/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor.castor;


import goja.castor.Castor;
import goja.castor.FailToCastObjectException;
import goja.lang.Times;

public abstract class DateTimeCastor<FROM, TO> extends Castor<FROM, TO> {

    protected java.util.Date toDate(String src) {
        try {
            return Times.D(src);
        }
        catch (Throwable e) {
            throw new FailToCastObjectException(e,
                                                "'%s' to %s",
                                                src,
                                                java.util.Date.class.getName());
        }
    }

}
