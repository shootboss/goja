package com.jfinal.module.wxchat.message.output;

import com.jfinal.module.wxchat.message.OutputMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <p>
 *  回复文本消息  对向微信服务器发出去的消息的封装
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
@XStreamAlias("xml")
public class TextOutputMessage extends OutputMessage {

	private String Content;

    protected TextOutputMessage(TextOutputMessageBuilder builder) {
        super(builder);
        this.Content = builder.Content;
    }

    public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

    public static class TextOutputMessageBuilder extends OutputMessageBuilder {
        private String Content;

        /**
         * 构成方法
         *
         * @param toUserName
         * @param fromUserName
         * @param createTime
         * @param msgType
         */
        public TextOutputMessageBuilder(String toUserName, String fromUserName, long createTime, String msgType) {
            super(toUserName, fromUserName, createTime, msgType);
        }

        public TextOutputMessageBuilder content(String content) {
            this.Content = content;
            return this;
        }

        @Override
        public TextOutputMessage build() {
            return new TextOutputMessage(this);
        }
    }

}

/*
 * 回复文本消息
 *  <xml>
 *      <ToUserName><![CDATA[toUser]]></ToUserName>
 *      <FromUserName><![CDATA[fromUser]]></FromUserName>
 *      <CreateTime>12345678</CreateTime>
 *      <MsgType><![CDATA[text]]></MsgType>
 *      <Content><![CDATA[content]]></Content>
 *  </xml>
 */
