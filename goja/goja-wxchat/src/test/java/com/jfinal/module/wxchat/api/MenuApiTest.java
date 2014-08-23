package com.jfinal.module.wxchat.api;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.module.wxchat.api.beans.Button;
import com.jfinal.module.wxchat.api.beans.ErrCodeMsg;
import org.junit.Test;

/**
 * <p>
 * . fancydong
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
public class MenuApiTest {
    private String menu = "{\n" +
            "     \"button\":[\n" +
            "     {\t\n" +
            "          \"type\":\"click\",\n" +
            "          \"name\":\"今日歌曲\",\n" +
            "          \"key\":\"V1001_TODAY_MUSIC\"\n" +
            "      },\n" +
            "      {\n" +
            "           \"type\":\"click\",\n" +
            "           \"name\":\"歌手简介\",\n" +
            "           \"key\":\"V1001_TODAY_SINGER\"\n" +
            "      },\n" +
            "      {\n" +
            "           \"name\":\"菜单\",\n" +
            "           \"sub_button\":[\n" +
            "           {\t\n" +
            "               \"type\":\"view\",\n" +
            "               \"name\":\"搜索\",\n" +
            "               \"url\":\"http://www.soso.com/\"\n" +
            "            },\n" +
            "            {\n" +
            "               \"type\":\"view\",\n" +
            "               \"name\":\"视频\",\n" +
            "               \"url\":\"http://v.qq.com/\"\n" +
            "            },\n" +
            "            {\n" +
            "               \"type\":\"click\",\n" +
            "               \"name\":\"赞一下我们aaaaaaaaa\",\n" +
            "               \"key\":\"V1001_GOOD\"\n" +
            "            }]\n" +
            "       }]\n" +
            " }";


    @Test
    public void testCreateMenu() {
        /*ErrCodeMsg s = MenuApi.createMenu(menu, AccessTokenApi.getAccessToken(DefaultApp.AppId, DefaultApp.AppSecret).getAccess_token());
        System.out.println(s.getErrcode() + "===" + s.getErrmsg());*/
        Button button = JSONObject.parseObject(menu, Button.class);
        System.out.println(button.getButton());
        System.out.println(button.getButton().get(0).getName());
        ErrCodeMsg s = MenuApi.createMenu(button, AccessTokenApi.getAccessToken(DefaultApp.AppId, DefaultApp.AppSecret).getAccess_token());
        System.out.println(s.getErrcode() + "===" + s.getErrmsg());
    }

    @Test
    public void testGetMenu() {
        Button menu = MenuApi.getMenu(AccessTokenApi.getAccessToken(DefaultApp.AppId, DefaultApp.AppSecret).getAccess_token());
        if (menu != null) {
            System.out.println(menu.toJSONString());
        }
    }

    @Test
    public void testDeleteMenu() {
        ErrCodeMsg s = MenuApi.deleteMenu(AccessTokenApi.getAccessToken(DefaultApp.AppId, DefaultApp.AppSecret).getAccess_token());
        System.out.println(s.getErrcode() + "===" + s.getErrmsg());
    }
}
