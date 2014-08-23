package com.jfinal.module.wxchat.message.input;

import com.jfinal.module.wxchat.message.InputMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <p>
 * 语音消息[普通消息]
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-03-26 21:03
 * @since JDK 1.6
 */
@XStreamAlias("xml")
public class VoiceInputMessage extends InputMessage {
    /** 语音消息媒体id，可以调用多媒体文件下载接口拉取数据 */
    private String MediaId;
    /** 语音格式，如amr，speex等 */
    private String Format;
    /** 消息id，64位整型 */
    private String MsgID;

    public VoiceInputMessage(Builder builder) {
        super(builder);
        this.MediaId = builder.MediaId;
        this.Format = builder.Format;
        this.MsgID = builder.MsgID;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

    public String getMsgID() {
        return MsgID;
    }

    public void setMsgID(String msgID) {
        MsgID = msgID;
    }

    public static class Builder extends InputMessageBuilder {
        private String MediaId;
        private String Format;
        private String MsgID;

        public Builder(String toUserName, String fromUserName, long createTime, String msgType) {
            super(toUserName, fromUserName, createTime, msgType);
        }

        public Builder mediaId(String mediaId) {
            MediaId = mediaId;
            return this;
        }

        public Builder format(String format) {
            Format = format;
            return this;
        }

        public Builder msgID(String msgID) {
            MsgID = msgID;
            return this;
        }

        @Override
        public VoiceInputMessage build() {
            return new VoiceInputMessage(this);
        }
    }
}
/*
 <xml>
     <ToUserName><![CDATA[toUser]]></ToUserName>
     <FromUserName><![CDATA[fromUser]]></FromUserName>
     <CreateTime>1357290913</CreateTime>
     <MsgType><![CDATA[voice]]></MsgType>
     <MediaId><![CDATA[media_id]]></MediaId>
     <Format><![CDATA[Format]]></Format>
     <MsgId>1234567890123456</MsgId>
 </xml>
 */
