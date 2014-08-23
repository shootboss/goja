package com.jfinal.module.wxchat.api;

/**
 * <p>
 * .
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
public interface Api {

    /**
     * access_token相关api
     */
    public static interface AccessToken {
        /** 获取ACCESS_TOKEN连接 */
        public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    }

    /**
     * 自定义菜单相关api
     */
    public static interface MENU {
        /** 创建菜单连接 */
        public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create";
        /** 获取菜单连接 */
        public static final String GET_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get";
        /** 删除菜单连接 */
        public static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete";
    }

}
