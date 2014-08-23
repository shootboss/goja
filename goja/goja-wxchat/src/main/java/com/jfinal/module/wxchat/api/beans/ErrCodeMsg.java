package com.jfinal.module.wxchat.api.beans;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * <p>
 *     全局返回码json对应java实体
 *     错误时微信会返回错误码等信息
 *     参见：http://mp.weixin.qq.com/wiki/index.php?title=%E5%85%A8%E5%B1%80%E8%BF%94%E5%9B%9E%E7%A0%81%E8%AF%B4%E6%98%8E
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
public class ErrCodeMsg {
    private String errcode;
    private String errmsg;

    @JSONCreator
    public ErrCodeMsg(@JSONField(name="errcode") String errcode,
                      @JSONField(name="errmsg") String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public String getErrcode() {
        return errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

}
