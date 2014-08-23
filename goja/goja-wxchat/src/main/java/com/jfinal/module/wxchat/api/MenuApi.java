package com.jfinal.module.wxchat.api;

import com.alibaba.fastjson.JSON;
import com.jfinal.module.wxchat.api.beans.Button;
import com.jfinal.module.wxchat.api.beans.ErrCodeMsg;
import com.jfinal.module.wxchat.api.beans.MenuButtonHelp;
import com.jfinal.module.wxchat.exceptions.WechatException;
import com.jfinal.module.wxchat.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *     菜单API
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-03-26 22:03
 * @since JDK 1.6
 */
public class MenuApi {
    private static Logger logger = LoggerFactory.getLogger(MenuApi.class);

    /**
     * 创建菜单
     *
     * @param menu 菜单json数据
     * @param accessToken 使用凭证
     * @return 响应结果
     */
    private static ErrCodeMsg createMenu(String menu, String accessToken){
        try {
            String result = HttpUtil.post(Api.MENU.CREATE_MENU_URL + "?access_token="+ accessToken, menu);
            return parseResult(result);
        } catch (Exception e) {
            logger.error("Error create menu", e);
            throw new WechatException("Error create menu", e);
        }
    }

    /**
     * 创建菜单
     *
     * @param button 菜单按钮
     * @param accessToken 使用凭证
     * @return 响应结果
     */
    public static ErrCodeMsg createMenu(Button button,String accessToken) {
        String menuJson = button.toJSONString();
        return createMenu(menuJson, accessToken);
    }

    /**
     * 获取已定义的菜单
     *
     * @param accessToken -- 使用凭证
     * @return 菜单json字符串
     */
    public static Button getMenu(String accessToken) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("access_token", accessToken);
            String result = HttpUtil.get(Api.MENU.GET_MENU_URL, map);
            if (!result.contains("button")) { //结果中不含button
                return null;
            }
            //{"menu":{"button":[..]}} 需要转换一下取出MenuButton
            MenuButtonHelp help = JSON.parseObject(result, MenuButtonHelp.class);
            return help.getButton();
        } catch (Exception e) {
            logger.error("Failed access method get menu", e);
            throw new WechatException("Error get menu", e);
        }
    }

    /**
     * 删除菜单
     *
     * @param accessToken -- 使用凭证
     * @return 响应结果
     */
    public static ErrCodeMsg deleteMenu(String accessToken) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("access_token", accessToken);
            String result = HttpUtil.get(Api.MENU.DELETE_MENU_URL, map);
            return parseResult(result);
        } catch (Exception e) {
            logger.error("Failed access method delete menu", e);
            throw new WechatException("Failed access method delete menu", e);
        }
    }

//----------------------辅助方法

    /**
     * 结果转成ErrCodeMsg对象
     *
     * @param result
     * @return 响应结果
     */
    private static ErrCodeMsg parseResult(String result) {
        ErrCodeMsg errCodeMsg = JSON.parseObject(result, ErrCodeMsg.class);
        return errCodeMsg;
    }

}
