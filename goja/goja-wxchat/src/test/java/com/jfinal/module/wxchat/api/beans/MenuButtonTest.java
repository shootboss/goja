package com.jfinal.module.wxchat.api.beans;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.jfinal.module.wxchat.core.Constants;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * .
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-03-27 22:03
 * @since JDK 1.6
 */
public class MenuButtonTest {

    @Test
    public void toJson() {
        Button menuButton = new Button(new ArrayList<Menu>());

        Menu menu1 = new Menu();
        menu1.setType(Constants.EVENT_CLICK);
        menu1.setName("今日歌曲");
        menu1.setKey("V1001_TODAY_MUSIC");
        menuButton.addMenu(menu1);

        Menu menu2 = new Menu();
        menu2.setType(Constants.EVENT_CLICK);
        menu2.setName("歌手简介");
        menu2.setKey("V1001_TODAY_SINGER");
        menuButton.addMenu(menu2);

        Menu menu3 = new Menu();
        menu3.setName("歌手简介");
        List<Menu> subs = Lists.newArrayList();
        menu3.setSub_button(subs);

        Menu sub1 = new Menu();
        sub1.setType(Constants.EVENT_VIEW);
        sub1.setName("搜索");
        sub1.setUrl("http://www.soso.com/");
        subs.add(sub1);

        Menu sub2 = new Menu();
        sub2.setType(Constants.EVENT_VIEW);
        sub2.setName("视频");
        sub2.setUrl("http://v.qq.com/");
        subs.add(sub2);

        Menu sub3 = new Menu();
        sub3.setType(Constants.EVENT_CLICK);
        sub3.setName("赞一下我们");
        sub3.setKey("V1001_GOOD");
        subs.add(sub3);

        menuButton.addMenu(menu3);

        String json = JSON.toJSONString(menuButton);

        System.out.println(json);
    }

}
