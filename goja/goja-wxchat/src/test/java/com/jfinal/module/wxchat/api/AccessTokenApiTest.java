package com.jfinal.module.wxchat.api;

import com.jfinal.module.wxchat.api.beans.AccessToken;
import org.junit.Test;

/**
 * <p>
 * .
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
public class AccessTokenApiTest {

    @Test
    public void testGetAccessToken() {
        AccessToken accessToken = AccessTokenApi.getAccessToken(DefaultApp.AppId, DefaultApp.AppSecret);
        System.out.println(accessToken.getAccess_token());
        System.out.println(accessToken.getExpires_in());
    }

}
