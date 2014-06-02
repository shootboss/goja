/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package japp.mvc;

import com.alibaba.fastjson.JSON;
import japp.StringPool;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * ajax请求返回信息
 * </p>
 *
 * @param <E> 数据格式
 * @author mumu@yfyang
 * @version 1.0 2013-08-10 12:27 PM
 * @since JDK 1.5
 */
public class AjaxMessage<E> {

    private static final String SUCCESS_MSG   = "您好，请求以筋斗云的速度请求成功，恭喜你！";
    /**
     * 没有权限访问的提示语
     */
    private static final String FORBIDDEN_MSG = "403,无权访问！";
    /**
     * 没有权限访问的提示语
     */
    private static final String NODATA_MSG    = "您好，你所请求的内容为空!";
    /**
     * 没有登录的提示语
     */
    private static final String NOLOGIN_MSG   = "您好，你是不是没有登录?只有登录后才能访问。";
    /**
     * 返回带的消息数据
     */
    private final E             data;
    /**
     * 消息提示语
     */
    private final String        message;
    /**
     * 消息状态
     */
    private final MessageStatus status;
    /**
     * 异常
     */
    private final Exception     exception;

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
        return ok(SUCCESS_MSG, null);
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
        return nodata(NODATA_MSG, null);
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
     * 返回禁止访问的消息内容
     *
     * @return 消息内容
     */
    public static AjaxMessage forbidden() {
        return forbidden(null);
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
