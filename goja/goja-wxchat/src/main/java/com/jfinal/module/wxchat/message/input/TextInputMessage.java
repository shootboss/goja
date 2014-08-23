package com.jfinal.module.wxchat.message.input;

import com.jfinal.module.wxchat.message.InputMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <p>
 *      文本消息[普通消息] 对微信服务器发送来的消息的封装
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
@XStreamAlias("xml")
public class TextInputMessage extends InputMessage {
	private String Content; // 文本消息内容
    private String MsgId; //消息id，64位整型

    public TextInputMessage(Builder builder) {
        super(builder);
        this.Content = builder.Content;
        this.MsgId = builder.MsgId;
    }

    public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

    public static class Builder extends InputMessageBuilder {
        private String Content; // 文本消息内容
        private String MsgId; //消息id，64位整型

        public Builder(String toUserName, String fromUserName, long createTime, String msgType) {
            super(toUserName, fromUserName, createTime, msgType);
        }

        public Builder content(String content) {
            Content = content;
            return this;
        }

        public Builder msgId(String msgId) {
            MsgId = msgId;
            return this;
        }

        @Override
        public TextInputMessage build() {
            return new TextInputMessage(this);
        }
    }

}

/*
 文本消息
     <xml>
         <ToUserName><![CDATA[toUser]]></ToUserName>
         <FromUserName><![CDATA[fromUser]]></FromUserName>
         <CreateTime>1348831860</CreateTime>
         <MsgType><![CDATA[text]]></MsgType>
         <Content><![CDATA[this is a test]]></Content>
         <MsgId>1234567890123456</MsgId>
     </xml>
 */
