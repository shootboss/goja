package com.jfinal.module.wxchat.api.beans;

/**
 * <p>
 *     可以有有效期的对象
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
public interface Expires {

    long getAccess();

    long getExpires_in();

}
