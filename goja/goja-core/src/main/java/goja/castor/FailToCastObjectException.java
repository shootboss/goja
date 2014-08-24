/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.castor;

@SuppressWarnings("serial")
public class FailToCastObjectException extends RuntimeException {

    public FailToCastObjectException(Throwable cause, String fmt, Object... args) {
        super(String.format(fmt, args), cause);
    }

    public FailToCastObjectException(String fmt, Object... args) {
        super(String.format(fmt, args));
    }

    public FailToCastObjectException(String message) {
        super(message);
    }

    public FailToCastObjectException(String message, Throwable cause) {
        super(message, cause);
    }

}
