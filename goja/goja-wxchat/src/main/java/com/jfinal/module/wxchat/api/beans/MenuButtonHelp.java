package com.jfinal.module.wxchat.api.beans;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * <p>
 *     发送到微信菜单和获取微信菜单格式不一样
 *
 *     返回格式如：{"menu":{"button":[..]}}
 *     用于帮助解析获取的微信菜单
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-03-27 23:03
 * @since JDK 1.6
 */
public class MenuButtonHelp {
    @JSONField(name = "menu")
    private Button button;

    @JSONCreator
    public MenuButtonHelp(@JSONField(name = "menu") Button button) {
        this.button = button;
    }

    public Button getButton() {
        return button;
    }
}
