/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package com.github.sog.controller;

/**
 * <p>
 * ajax请求返回信息
 * </p>
 *
 * @param <E> 数据格式
 * @author mumu@yfyang
 * @version 1.0 2013-08-10 12:27 PM
 * @since JDK 1.5
 * @deprecated
 */
public class AjaxMessage<E> extends japp.mvc.AjaxMessage<E> {


    private AjaxMessage(E data, String message, japp.mvc.AjaxMessage.MessageStatus status) {
        super(data, message, status);
    }

    private AjaxMessage(E data, String message, japp.mvc.AjaxMessage.MessageStatus status, Exception exception) {
        super(data, message, status, exception);
    }
}
