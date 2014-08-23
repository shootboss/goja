package com.jfinal.module.wxchat.utils;

import com.jfinal.module.wxchat.processor.MessageProcessor;
import com.jfinal.module.wxchat.message.OutputMessage;
import com.jfinal.module.wxchat.message.input.EventInputMessage;
import com.jfinal.module.wxchat.utils.MessageUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * .
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
public class MessageUtilTest {

    @Test
    public void testMessageToXml() {
        //RespBaseMessage m = new TextMessage("<![CDATA[toUser]]>", "<![CDATA[fromUser]]>",System.currentTimeMillis(), "<![CDATA[text]]>", "<![CDATA[content]]>");

        /*List<Item> articles = new ArrayList<Item>();
        articles.add(new Item("title1", "description1","http://www.baidu.com/1.png", "http://www.baidu.com/1"));
        articles.add(new Item("title2", "description2","http://www.baidu.com/2.png", "http://www.baidu.com/2"));
        articles.add(new Item("title3", "description3","http://www.baidu.com/3.png", "http://www.baidu.com/3"));
        RespBaseMessage m = new NewsMessage("<![CDATA[toUser]]>", "<![CDATA[fromUser]]>",System.currentTimeMillis(), "<![CDATA["+ Constants.MSGTYPE_NEWS +"]]>", articles.size() , articles);*/

        /*Music music = new Music("title", "description", "http://www.baidu.com/1.png", "http://www.baidu.com/1", "id1");
        RespBaseMessage m = new MusicMessage("<![CDATA[toUser]]>", "<![CDATA[fromUser]]>",System.currentTimeMillis(), "<![CDATA["+ Constants.MSGTYPE_MUSIC +"]]>", music);*/
        /*Video video = new Video("id1", "title", "Description");
        RespBaseMessage m = new VideoMessage("<![CDATA[toUser]]>", "<![CDATA[fromUser]]>",System.currentTimeMillis(), "<![CDATA["+ Constants.MSGTYPE_VIDEO +"]]>", video);*/

        /*Voice voice = new Voice("id1");
        RespBaseMessage m = new VoiceMessage("<![CDATA[toUser]]>", "<![CDATA[fromUser]]>",System.currentTimeMillis(), "<![CDATA["+ Constants.MSGTYPE_VOICE +"]]>", voice);*/

        /*Image image = new Image("id1");
        RespBaseMessage m = new ImageMessage("<![CDATA[toUser]]>", "<![CDATA[fromUser]]>",System.currentTimeMillis(), "<![CDATA["+ Constants.MSGTYPE_IMAGE +"]]>", image);

        String s = MessageUtil.messageToXml(m);
        System.out.println(s);*/
    }

    @Test
    public void testXmlToMessage() {
        /*String xml = "<xml>" +
                "<ToUserName><![CDATA[toUser]]></ToUserName>" +
                "<FromUserName><![CDATA[fromUser]]></FromUserName>" +
                "<CreateTime>1351776360</CreateTime>" +
                "<MsgType><![CDATA[link]]></MsgType>" +
                "<Title><![CDATA[公众平台官网链接]]></Title>" +
                "<Description><![CDATA[公众平台官网链接]]></Description>" +
                "<Url><![CDATA[url]]></Url>" +
                "<MsgId>1234567890123456</MsgId>" +
                "</xml> ";*/

        /*Map<String, String> map = new HashMap<String, String>();

        map.put("ToUserName", "<![CDATA[toUser]]>");
        map.put("FromUserName", "<![CDATA[FromUserName]]>");
        map.put("CreateTime", "1351776360");
        map.put("MsgType", "<![CDATA[link]]>");
        map.put("Title", "<![CDATA[公众平台官网链接]]>");
        map.put("Description", "<![CDATA[公众平台官网链接]]>");
        map.put("Url", "<![CDATA[url]]>");
        map.put("MsgId", "1234567890123456");

        MessageProcessor processr = new MessageProcessor() {

            @Override
            public OutputMessage process(Map<String, String> message) {
                return null;
            }
        };



        LinkInputMessage message = (LinkInputMessage) MessageUtil.xmlToReqMessage(processr.mapToXml(map), LinkInputMessage.class);
        System.out.println(message.getUrl() + "==" + message.getDescription());*/

        MessageProcessor processr = new MessageProcessor() {

            @Override
            public OutputMessage process(Map<String, String> message) {
                return null;
            }
        };
        String xml = "<xml>" +
                "<ToUserName><![CDATA[toUser]]></ToUserName>" +
                "<FromUserName><![CDATA[FromUser]]></FromUserName>" +
                "<CreateTime>123456789</CreateTime>" +
                "<MsgType><![CDATA[event]]></MsgType>" +
                "<Event><![CDATA[subscribe]]></Event>" +
                "</xml>";
        Map<String, String> map = new HashMap<String, String>();

        map.put("ToUserName", "<![CDATA[toUser]]>");
        map.put("FromUserName", "<![CDATA[FromUserName]]>");
        map.put("CreateTime", "1351776360");
        map.put("MsgType", "<![CDATA[event]]>");
        map.put("Event", "<![CDATA[subscribe]]>");
        EventInputMessage message = (EventInputMessage) MessageUtil.xmlToInputMessage(processr.mapToXml(map), EventInputMessage.class);
        System.out.println(message.getEvent());
    }

}
