/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package japp.exceptions;

import java.util.concurrent.atomic.AtomicLong;

/**
 * The super class for all Play! exceptions
 */
public abstract class JAppException extends RuntimeException {

    static AtomicLong atomicLong = new AtomicLong(System.currentTimeMillis());
    String id;

    public JAppException() {
        super();
        setId();
    }

    public JAppException(String message) {
        super(message);
        setId();
    }

    public JAppException(String message, Throwable cause) {
        super(message, cause);
        setId();
    }

    void setId() {
        long nid = atomicLong.incrementAndGet();
        id = Long.toString(nid, 26);
    }

    public abstract String getErrorTitle();

    public abstract String getErrorDescription();


    public Integer getLineNumber() {
        return -1;
    }

    public String getSourceFile() {
        return "";
    }

    public String getId() {
        return id;
    }


    public String getMoreHTML() {
        return null;
    }
}