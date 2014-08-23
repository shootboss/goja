package com.jfinal.module.wxchat.api;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.jfinal.module.wxchat.api.beans.AccessToken;
import com.jfinal.module.wxchat.exceptions.WechatException;
import com.jfinal.module.wxchat.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * <p>
 *     获取获取access token接口服务
 *
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
public class AccessTokenApi {
    private static Logger logger = LoggerFactory.getLogger(MenuApi.class);
    private final static String GRANT_TYPE = "client_credential";

    /**
     * 获取 使用凭证
     *
     * https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
     *
     * grant_type : 获取access_token填写client_credential
     * appid : 第三方用户唯一凭证
     * secret : 第三方用户唯一凭证密钥，即appsecret
     *
     * @param appId
     * @param appSecret
     * @return 使用凭证 AccessToken
     */
    public static AccessToken getAccessToken(String appId, String appSecret) {
        //TODO AccessToken 需要从缓存里面获取
        try {
            Map<String, Object> map = Maps.newHashMap();
            map.put("grant_type", GRANT_TYPE);
            map.put("appid", appId);
            map.put("secret", appSecret);
            String result = HttpUtil.get(Api.AccessToken.GET_ACCESS_TOKEN_URL, map);
            if (result.contains("access_token") && result.contains("expires_in")) {
                AccessToken at = JSON.parseObject(result, AccessToken.class);
                return at;
            } else {
                logger.error("Error to Get AccessToken, err code and msg: ", result);
                throw new WechatException("Error to Get AccessToken, err code and msg: " + result);
            }
        } catch (Exception e) {
            logger.error("Error to Get AccessToken", e);
            throw new WechatException("Error to Get AccessToken", e);
        }
    }


}
