/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.mvc;

import com.alibaba.fastjson.JSON;
import goja.StringPool;
import org.apache.commons.lang3.StringUtils;

/**
 * <p> ajax请求返回信息 </p>
 *
 * @param <E> 数据格式
 * @author mumu@yfyang
 * @version 1.0 2013-08-10 12:27 PM
 * @since JDK 1.5
 */
public final class AjaxMessage<E> {

    private static final String SUCCESS_MSG   = StringPool.EMPTY;
    /**
     * Don't have permission to access the clues
     */
    private static final String FORBIDDEN_MSG = "You have no right to access this path!";
    /**
     * There is no data of the clues
     */
    private static final String NODATA_MSG    = "Hello, what you request is empty!";
    /**
     * Not logged in the clues
     */
    private static final String NOLOGIN_MSG   = "Hello, you are not logged in? Only logged in can access.";


    /**
     * Returns the message data
     */
    private final E             data;
    /**
     * News clues
     */
    private final String        message;
    /**
     * Message State Machine.
     */
    private final MessageStatus status;
    /**
     * Exception
     */
    private final Exception     exception;


    private static final AjaxMessage OK        = ok(SUCCESS_MSG, null);
    private static final AjaxMessage NODATA    = nodata(NODATA_MSG, null);
    private static final AjaxMessage FORBIDDEN = forbidden(null);
    private static final AjaxMessage ERROR     = error(null);
    private static final AjaxMessage FAILURE   = failure(null);

    /**
     * 构造函数
     *
     * @param data    消息数据
     * @param message 消息提示
     * @param status  消息状态
     */
    protected AjaxMessage(E data, String message, MessageStatus status) {
        this.data = data;
        this.message = message;
        this.status = status;
        this.exception = null;
    }

    /**
     * 构造一个包括异常信息的函数
     *
     * @param data      消息数据
     * @param message   消息提示
     * @param status    消息状态
     * @param exception 异常信息
     */
    protected AjaxMessage(E data, String message, MessageStatus status, Exception exception) {
        this.data = data;
        this.message = message;
        this.status = status;
        this.exception = exception;
    }

    /**
     * 返回处理正常的消息内容
     *
     * @param message 消息提示
     * @param data    消息数据
     * @param <E>     数据泛型类型
     * @return 消息内容
     */
    public static <E> AjaxMessage ok(String message, E data) {
        return new AjaxMessage<E>(data, message, MessageStatus.OK);
    }

    /**
     * 返回处理正常的消息内容
     *
     * @param data 消息数据
     * @param <E>  数据泛型类型
     * @return 消息内容
     */
    public static <E> AjaxMessage ok(E data) {
        return ok(StringUtils.EMPTY, data);
    }

    /**
     * 返回处理正常的消息内容
     *
     * @param message 消息提示
     * @return 消息内容
     */
    public static AjaxMessage ok(String message) {
        return ok(message, null);
    }

    /**
     * 返回处理正常的消息内容
     *
     * @return 消息内容
     */
    public static AjaxMessage ok() {
        return OK;
    }

    /**
     * 正在开发提示语
     *
     * @return 正在开发提示
     */
    public static AjaxMessage developing() {

        return ok("正在开发中...", null);
    }

    /**
     * 返回没有数据的消息内容
     *
     * @param data 消息数据
     * @param <E>  数据泛型类型
     * @return 消息内容
     */
    public static <E> AjaxMessage nodata(E data) {
        return new AjaxMessage<E>(data, NODATA_MSG, MessageStatus.NODATA);
    }

    /**
     * 返回没有数据的消息内容
     *
     * @param data 消息数据
     * @param <E>  数据泛型类型
     * @return 消息内容
     */
    public static <E> AjaxMessage nodata(String message, E data) {
        return new AjaxMessage<E>(data, message, MessageStatus.NODATA);
    }

    /**
     * 返回没有数据的消息内容
     *
     * @return 消息内容
     */
    public static AjaxMessage nodata() {
        return NODATA;
    }

    /**
     * 返回没有登录时消息内容
     *
     * @param data 消息数据
     * @param <E>  数据泛型类型
     * @return 消息内容
     */
    public static <E> AjaxMessage nologin(E data) {
        return new AjaxMessage<E>(data, NOLOGIN_MSG, MessageStatus.NOLOGIN);
    }

    /**
     * 返回没有登录时的消息内容
     *
     * @return 消息内容
     */
    public static AjaxMessage nologin() {
        return nologin(null);
    }

    /**
     * 返回禁止访问消息内容
     *
     * @param data 消息数据
     * @param <E>  数据泛型类型
     * @return 消息内容
     */
    public static <E> AjaxMessage forbidden(E data) {
        return new AjaxMessage<E>(data, FORBIDDEN_MSG, MessageStatus.FORBIDDEN);
    }

    /**
     * 返回禁止访问消息内容
     *
     * @param data 消息数据
     * @param <E>  数据泛型类型
     * @return 消息内容
     */
    public static <E> AjaxMessage forbidden(String message, E data) {
        return new AjaxMessage<E>(data, message, MessageStatus.FORBIDDEN);
    }

    /**
     * 返回禁止访问的消息内容
     *
     * @return 消息内容
     */
    public static AjaxMessage forbidden() {
        return FORBIDDEN;
    }

    public static AjaxMessage error() {
        return ERROR;
    }

    /**
     * 返回处理错误的消息内容
     *
     * @param message 消息提示
     * @return 消息内容
     */
    public static AjaxMessage error(String message) {
        return error(message, null, null);
    }

    /**
     * 返回处理错误的消息内容
     *
     * @param data 消息提示
     * @return 消息内容
     */
    public static <E> AjaxMessage error(E data) {
        return error(StringPool.EMPTY, data, null);
    }

    /**
     * 返回处理错误的消息内容
     *
     * @param message   消息提示
     * @param exception 异常
     * @return 消息内容
     */
    public static AjaxMessage error(String message, Exception exception) {
        return error(message, null, exception);
    }

    /**
     * 返回处理错误的消息内容
     *
     * @param message   消息提示
     * @param exception 异常
     * @param data      数据
     * @return 消息内容
     */
    public static <E> AjaxMessage error(String message, E data, Exception exception) {
        return new AjaxMessage<E>(data, message, MessageStatus.ERROR, exception);
    }

    /**
     * 处理失败的消息，默认提示信息。
     *
     * @return 提示信息
     */
    public static AjaxMessage failure() {
        return FAILURE;
    }

    /**
     * 返回处理失败的消息内容
     *
     * @param message 消息提示
     * @return 消息内容
     */
    public static AjaxMessage failure(String message) {
        return failure(message, null, null);
    }

    /**
     * 返回处理失败的消息内容
     *
     * @param data data
     * @return 消息内容
     */
    public static <E> AjaxMessage failure(E data) {
        return failure(null, data, null);
    }

    /**
     * 返回处理失败的消息内容
     *
     * @param message   消息提示
     * @param exception 异常
     * @return 消息内容
     */
    public static AjaxMessage failure(String message, Exception exception) {
        return failure(message, null, exception);
    }

    /**
     * 返回处理失败的消息内容
     *
     * @param message   消息提示
     * @param exception 异常
     * @param data      数据
     * @return 消息内容
     */
    public static <E> AjaxMessage failure(String message, E data, Exception exception) {
        return new AjaxMessage<E>(data, message, MessageStatus.FAILURE, exception);
    }

    /**
     * 获取消息数据
     *
     * @return 消息数据
     */
    public E getData() {
        return data;
    }

    /**
     * 获取消息提
     *
     * @return 消息提醒
     */
    public String getMessage() {
        return message;
    }

    /**
     * 获取状态
     *
     * @return 状态信息
     */
    public MessageStatus getStatus() {
        return status;
    }

    /**
     * 转换为JSON字符串。
     *
     * @return JSON字符串
     */
    public String toJSON() {
        return JSON.toJSONString(this);
    }

    /**
     * 获取错误异常.
     *
     * @return 异常信息.
     */
    public Exception getException() {
        return exception;
    }

    /**
     * 请求消息处理状态
     */
    protected enum MessageStatus {
        /**
         * 正常
         */
        OK,
        /**
         * 发生内部错误
         */
        ERROR,
        /**
         * 处理失败
         */
        FAILURE,
        /**
         * 没有数据
         */
        NODATA,
        /**
         * 禁止访问
         */
        FORBIDDEN,
        /**
         * 没有登录
         */
        NOLOGIN

    }

}
