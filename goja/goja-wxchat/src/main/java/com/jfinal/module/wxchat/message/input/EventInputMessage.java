package com.jfinal.module.wxchat.message.input;

import com.jfinal.module.wxchat.message.InputMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <p>
 * 事件推送(事件消息) 对微信服务器发送来的消息的封装
 *
 * 关注/取消关注事件 [subscribe(订阅)、unsubscribe(取消订阅)]
 *
 * <xml>
 *      <ToUserName><![CDATA[toUser]]></ToUserName>
 *      <FromUserName><![CDATA[FromUser]]></FromUserName>
 *      <CreateTime>123456789</CreateTime>
 *      <MsgType><![CDATA[event]]></MsgType>
 *      <Event><![CDATA[subscribe]]></Event> || <Event><![CDATA[unsubscribe]]></Event>
 * </xml>
 *
 * 扫描带参数二维码事件
 *  用户扫描带场景值二维码时，可能推送以下两种事件：
 *      1、如果用户还未关注公众号，则用户可以关注公众号，关注后微信会将带场景值关注事件推送给开发者。
 *      <xml>
 *          <ToUserName><![CDATA[toUser]]></ToUserName>
 *          <FromUserName><![CDATA[FromUser]]></FromUserName>
 *          <CreateTime>123456789</CreateTime>
 *          <MsgType><![CDATA[event]]></MsgType>
 *          <Event><![CDATA[subscribe]]></Event>             ------------事件类型，subscribe
 *          <EventKey><![CDATA[qrscene_123123]]></EventKey>  ------------事件KEY值，qrscene_为前缀，后面为二维码的参数值
 *          <Ticket><![CDATA[TICKET]]></Ticket>              ------------二维码的ticket，可用来换取二维码图片
 *     </xml>
 *
 *     2、如果用户已经关注公众号，则微信会将带场景值扫描事件推送给开发者。
 *     <xml>
 *         <ToUserName><![CDATA[toUser]]></ToUserName>
 *         <FromUserName><![CDATA[FromUser]]></FromUserName>
 *         <CreateTime>123456789</CreateTime>
 *         <MsgType><![CDATA[event]]></MsgType>
 *         <Event><![CDATA[SCAN]]></Event>                  ------------事件类型，subscribe
 *         <EventKey><![CDATA[SCENE_VALUE]]></EventKey>     ------------事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
 *         <Ticket><![CDATA[TICKET]]></Ticket>              ------------二维码的ticket，可用来换取二维码图片
 *    </xml>
 *
 * 上报地理位置事件
 * <xml>
 *     <ToUserName><![CDATA[toUser]]></ToUserName>
 *     <FromUserName><![CDATA[fromUser]]></FromUserName>
 *     <CreateTime>123456789</CreateTime>
 *     <MsgType><![CDATA[event]]></MsgType>
 *     <Event><![CDATA[LOCATION]]></Event>
 *     <Latitude>23.137466</Latitude>
 *     <Longitude>113.352425</Longitude>
 *     <Precision>119.385040</Precision>
 * </xml>
 *
 * 自定义菜单事件
 *      1、点击菜单拉取消息时的事件推送
 *      <xml>
 *          <ToUserName><![CDATA[toUser]]></ToUserName>
 *          <FromUserName><![CDATA[FromUser]]></FromUserName>
 *          <CreateTime>123456789</CreateTime>
 *          <MsgType><![CDATA[event]]></MsgType>
 *          <Event><![CDATA[CLICK]]></Event>
 *          <EventKey><![CDATA[EVENTKEY]]></EventKey>
 *      </xml>
 *
 *      2、点击菜单跳转链接时的事件推送
 *      <xml>
 *          <ToUserName><![CDATA[toUser]]></ToUserName>
 *          <FromUserName><![CDATA[FromUser]]></FromUserName>
 *          <CreateTime>123456789</CreateTime>
 *          <MsgType><![CDATA[event]]></MsgType>
 *          <Event><![CDATA[VIEW]]></Event>
 *          <EventKey><![CDATA[www.qq.com]]></EventKey>
 *      </xml>
 *
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
@XStreamAlias("xml")
public class EventInputMessage extends InputMessage {
    private String Event; // 事件类型

    private String EventKey; // 事件KEY值

    private String Ticket;   //二维码的ticket，可用来换取二维码图片

    private String Latitude; //地理位置纬度
    private String Longitude; //地理位置经度
    private String Precision; //地理位置精度

    public EventInputMessage(Builder builder) {
        super(builder);
        this.Event = builder.Event;

        this.EventKey = builder.EventKey;

        this.Ticket = builder.Ticket;

        this.Latitude = builder.Latitude;
        this.Longitude = builder.Longitude;
        this.Precision = builder.Precision;
    }

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getPrecision() {
        return Precision;
    }

    public void setPrecision(String precision) {
        Precision = precision;
    }

    public static class Builder extends InputMessageBuilder {
        private String Event; // 事件类型

        private String EventKey; // 事件KEY值

        private String Ticket;   //二维码的ticket，可用来换取二维码图片

        private String Latitude; //地理位置纬度
        private String Longitude; //地理位置经度
        private String Precision; //地理位置精度

        public Builder(String toUserName, String fromUserName, long createTime, String msgType) {
            super(toUserName, fromUserName, createTime, msgType);
        }

        public Builder event(String event) {
            Event = event;
            return this;
        }

        public Builder eventKey(String eventKey) {
            EventKey = eventKey;
            return this;
        }

        public Builder ticket(String ticket) {
            Ticket = ticket;
            return this;
        }

        public Builder latitude(String latitude) {
            Latitude = latitude;
            return this;
        }

        public Builder longitude(String longitude) {
            Longitude = longitude;
            return this;
        }

        public Builder precision(String precision) {
            Precision = precision;
            return this;
        }

        @Override
        public EventInputMessage build() {
            return new EventInputMessage(this);
        }
    }

}