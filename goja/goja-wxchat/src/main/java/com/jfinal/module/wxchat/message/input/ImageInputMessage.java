package com.jfinal.module.wxchat.message.input;

import com.jfinal.module.wxchat.message.InputMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <p>
 *      图片消息[普通消息] 对微信服务器发送来的消息的封装
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
@XStreamAlias("xml")
public class ImageInputMessage extends InputMessage {
	private String PicUrl; // 图片链接
    private String MediaId; //图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
    private String MsgId; //消息id，64位整型

    public ImageInputMessage(InputMessageBuilder builder) {
        super(builder);
    }


    public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    public static class Builder extends InputMessageBuilder {
        private String PicUrl; // 图片链接
        private String MediaId; //图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
        private String MsgId; //消息id，64位整型

        public Builder(String toUserName, String fromUserName, long createTime, String msgType) {
            super(toUserName, fromUserName, createTime, msgType);
        }

        public Builder picUrl(String picUrl) {
            PicUrl = picUrl;
            return this;
        }

        public Builder mediaId(String mediaId) {
            MediaId = mediaId;
            return this;
        }

        public Builder msgId(String msgId) {
            MsgId = msgId;
            return this;
        }
    }
}
/*
 图片消息
 <xml>
     <ToUserName><![CDATA[toUser]]></ToUserName>
     <FromUserName><![CDATA[fromUser]]></FromUserName>
     <CreateTime>1348831860</CreateTime>
     <MsgType><![CDATA[image]]></MsgType>
     <PicUrl><![CDATA[this is a url]]></PicUrl>
     <MediaId><![CDATA[media_id]]></MediaId>
     <MsgId>1234567890123456</MsgId>
 </xml>
 */