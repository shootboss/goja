package com.jfinal.module.wxchat.api.beans;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.jfinal.module.wxchat.core.Constants;

import java.util.List;

/**
 * <p>
 * 菜单对象
 *  {
 *      "button":[
 *      {
 *          "type":"click",
 *          "name":"今日歌曲",
 *          "key":"V1001_TODAY_MUSIC"
 *      },
 *      {
 *          "type":"click",
 *          "name":"歌手简介",
 *          "key":"V1001_TODAY_SINGER"
 *       },
 *       {
 *        "name":"菜单",
 *        "sub_button":[
 *          {
 *              "type":"view",
 *              "name":"搜索",
 *              "url":"http://www.soso.com/"
 *          },
 *          {
 *              "type":"view",
 *              "name":"视频",
 *              "url":"http://v.qq.com/"
 *           },
 *           {
 *              "type":"click",
 *              "name":"赞一下我们",
 *              "key":"V1001_GOOD"
 *           }
 *          ]
 *       }
 *    ]
 *  }
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-03-27 22:03
 * @since JDK 1.6
 */
public class Menu {
    private String type; //菜单类型 -- click || view
    private String name; //菜单名称
    private String key; //类型为click时指定
    private String url; //类型为view时指定

    private List<Menu> sub_button; //子菜单

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    /**
     * 只有在type是click时候才能指定
     *
     * @param key 类型为click时指定，表click的键职
     */
    public void setKey(String key) {
        if (!Constants.EVENT_CLICK.equals(type)) {
            throw new RuntimeException("you must set type click");
        }
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    /**
     * 只有在type是view时候才能指定
     *
     * @param url 类型为view时指定，表示view的地址
     */
    public void setUrl(String url) {
        if (!Constants.EVENT_VIEW.equals(type)) {
            throw new RuntimeException("you must set type view");
        }
        this.url = url;
    }

    public List<Menu> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<Menu> sub_button) {
        this.sub_button = sub_button;
    }

}
