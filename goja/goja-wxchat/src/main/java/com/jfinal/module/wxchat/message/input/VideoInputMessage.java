package com.jfinal.module.wxchat.message.input;

import com.jfinal.module.wxchat.message.InputMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <p>
 * 视频消息[普通消息]
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-03-26 20:03
 * @since JDK 1.6
 */
@XStreamAlias("xml")
public class VideoInputMessage extends InputMessage {
    private String MediaId; //视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
    private String ThumbMediaId; //视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
    private String MsgId; //消息id，64位整型

    public VideoInputMessage(Builder builder) {
        super(builder);
        this.MediaId = builder.MediaId;
        this.ThumbMediaId = builder.ThumbMediaId;
        this.MsgId = builder.MsgId;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }

    public static class Builder extends InputMessageBuilder {
        private String MediaId; //视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
        private String ThumbMediaId; //视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
        private String MsgId; //消息id，64位整型

        public Builder(String toUserName, String fromUserName, long createTime, String msgType) {
            super(toUserName, fromUserName, createTime, msgType);
        }

        public void mediaId(String mediaId) {
            MediaId = mediaId;
        }

        public void thumbMediaId(String thumbMediaId) {
            ThumbMediaId = thumbMediaId;
        }

        public void msgId(String msgId) {
            MsgId = msgId;
        }

        @Override
        public InputMessage build() {
            return new VideoInputMessage(this);
        }
    }
}
/*
<xml>
    <ToUserName><![CDATA[toUser]]></ToUserName>
    <FromUserName><![CDATA[fromUser]]></FromUserName>
    <CreateTime>1357290913</CreateTime>
    <MsgType><![CDATA[video]]></MsgType>
    <MediaId><![CDATA[media_id]]></MediaId>
    <ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>
    <MsgId>1234567890123456</MsgId>
</xml>
 */
