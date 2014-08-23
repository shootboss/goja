package com.jfinal.module.wxchat.api.beans;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * <p>
 * AccessToken json 对应实体
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
public class AccessToken implements Expires {
    private long access; //存入时间，也就是对象创建时间
    private String access_token; //访问认证
    private long expires_in; //有效时间

    @JSONCreator
    public AccessToken(@JSONField(name = "access_token") String access_token,
                       @JSONField(name = "expires_in") long expires_in) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.access = System.currentTimeMillis();
    }

    public String getAccess_token() {
        return access_token;
    }

    @Override
    public long getAccess() {
        return access;
    }

    @Override
    public long getExpires_in() {
        return expires_in;
    }

}
