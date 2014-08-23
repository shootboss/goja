package com.jfinal.module.wxchat.api.beans;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.module.wxchat.core.Constants;
import org.junit.Test;

/**
 * <p>
 * .
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-03-27 22:03
 * @since JDK 1.6
 */
public class MenuTest {

    @Test
    public void toJson() {
        Menu menu = new Menu();
        menu.setType(Constants.EVENT_CLICK);
        menu.setUrl("url");
        String json = JSON.toJSONString(menu);

        System.out.println(json);
    }

}
