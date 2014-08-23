package com.jfinal.module.wxchat.message.input;

import com.jfinal.module.wxchat.message.InputMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <p>
 *      链接消息 对微信服务器发送来的消息的封装
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
@XStreamAlias("xml")
public class LinkInputMessage extends InputMessage {
	private String Title; //消息标题
    private String Description; //消息描述
	private String Url; //消息链接
    private String MsgId; //消息id，64位整型

    public LinkInputMessage(Builder builder) {
        super(builder);
        this.Title = builder.Title;
        this.Description = builder.Description;
        this.Url = builder.Url;
        this.MsgId = builder.MsgId;
    }

    public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    public static class Builder extends InputMessageBuilder {
        private String Title;
        private String Description;
        private String Url;
        private String MsgId; //消息id，64位整型

        public Builder(String toUserName, String fromUserName, long createTime, String msgType) {
            super(toUserName, fromUserName, createTime, msgType);
        }

        public Builder title(String title) {
            Title = title;
            return this;
        }

        public Builder description(String description) {
            Description = description;
            return this;
        }

        public Builder url(String url) {
            Url = url;
            return this;
        }

        public Builder msgId(String msgId) {
            MsgId = msgId;
            return this;
        }

        @Override
        public LinkInputMessage build() {
            return new LinkInputMessage(this);
        }
    }
}

/*
 * 链接消息
 *  <xml>
 *      <ToUserName><![CDATA[toUser]]></ToUserName>
 *      <FromUserName><![CDATA[fromUser]]></FromUserName>
 *      <CreateTime>1351776360</CreateTime>
 *      <MsgType><![CDATA[link]]></MsgType>
 *      <Title><![CDATA[公众平台官网链接]]></Title>
 *      <Description><![CDATA[公众平台官网链接]]></Description>
 *      <Url><![CDATA[url]]></Url>
 *      <MsgId>1234567890123456</MsgId>
 *  </xml>
 */