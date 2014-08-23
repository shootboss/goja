package com.jfinal.module.wxchat.message;

import com.jfinal.module.wxchat.core.Constants;
import com.jfinal.module.wxchat.message.output.*;
import com.jfinal.module.wxchat.utils.MessageUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * .
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
public class OutputMessageBuilderTest {

    @Test
    public void testImageOutputMessageBuilder() {
        ImageOutputMessage.Builder builder = new ImageOutputMessage
                .Builder("<![CDATA[toUserName]]>", "FromUserName", System.currentTimeMillis(), Constants.MSGTYPE_IMAGE);
        Image image = new Image("id1");
        builder.image(image);
        ImageOutputMessage message = builder.build();
        String xml = MessageUtil.outputMessageToXml(message);
        System.out.println(xml);
    }

    @Test
    public void testMusicOutputMessageBuilder() {
        MusicOutputMessage.Builder builder = new MusicOutputMessage
                .Builder("<![CDATA[toUserName]]>", "FromUserName", System.currentTimeMillis(), Constants.MSGTYPE_MUSIC);
        Music music = new Music("title", "descrition", "musicUrl", "HQMusicUrl", "thumbMediaId");
        builder.music(music);
        OutputMessage message = builder.build();
        String xml = MessageUtil.outputMessageToXml(message);
        System.out.println(xml);
    }

    @Test
    public void testNewsOutputMessageBuilder() {
        NewsOutputMessage.Builder builder = new NewsOutputMessage
                .Builder("<![CDATA[toUserName]]>", "FromUserName", System.currentTimeMillis(), Constants.MSGTYPE_NEWS);

        List<Item> articles = new ArrayList<Item>();
        articles.add(new Item("title1", "description1","http://www.baidu.com/1.png", "http://www.baidu.com/1"));
        articles.add(new Item("title2", "description2","http://www.baidu.com/2.png", "http://www.baidu.com/2"));
        articles.add(new Item("title3", "description3","http://www.baidu.com/3.png", "http://www.baidu.com/3"));

        builder.articleCount(articles.size()).articles(articles);

        OutputMessage message = builder.build();
        String xml = MessageUtil.outputMessageToXml(message);
        System.out.println(xml);
    }

    @Test
    public void testTextOutputMessageBuilder() {
        TextOutputMessage.TextOutputMessageBuilder builder = new TextOutputMessage
                .TextOutputMessageBuilder("<![CDATA[toUserName]]>", "FromUserName", System.currentTimeMillis(), Constants.MSGTYPE_TEXT);
        builder.content("Content");
        OutputMessage message = builder.build();
        String xml = MessageUtil.outputMessageToXml(message);
        System.out.println(xml);
    }

    @Test
    public void testVideoOutputMessageBuilder() {
        VideoOutputMessage.Builder builder = new VideoOutputMessage
                .Builder("<![CDATA[toUserName]]>", "FromUserName", System.currentTimeMillis(), Constants.MSGTYPE_VIDEO);
        Video video = new Video("id1", "title", "Description");
        builder.video(video);
        OutputMessage message = builder.build();
        String xml = MessageUtil.outputMessageToXml(message);
        System.out.println(xml);
    }

    @Test
    public void testVoiceOutputMessageBuilder() {
        VoiceOutputMessage.Builder builder = new VoiceOutputMessage
                .Builder("<![CDATA[toUserName]]>", "FromUserName", System.currentTimeMillis(), Constants.MSGTYPE_VOICE);
        Voice voice = new Voice("id1");
        builder.voice(voice);
        OutputMessage message = builder.build();
        String xml = MessageUtil.outputMessageToXml(message);
        System.out.println(xml);
    }


}
