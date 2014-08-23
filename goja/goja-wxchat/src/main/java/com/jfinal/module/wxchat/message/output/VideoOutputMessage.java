package com.jfinal.module.wxchat.message.output;

import com.jfinal.module.wxchat.message.OutputMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <p>
 * 回复视频消息
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-03-26 20:03
 * @since JDK 1.6
 */
@XStreamAlias("xml")
public class VideoOutputMessage extends OutputMessage {

    private Video Video;

    protected VideoOutputMessage(Builder builder) {
        super(builder);
        this.Video = builder.Video;
    }

    public Video getVideo() {
        return Video;
    }

    public void setVideo(Video video) {
        Video = video;
    }

    public static class Builder extends OutputMessageBuilder {
        private Video Video;

        public Builder(String toUserName, String fromUserName, long createTime, String msgType) {
            super(toUserName, fromUserName, createTime, msgType);
        }

        public void video(Video video) {
            this.Video = video;
        }

        @Override
        public VideoOutputMessage build() {
            return new VideoOutputMessage(this);
        }
    }
}
/*
<xml>
    <ToUserName><![CDATA[toUser]]></ToUserName>
    <FromUserName><![CDATA[fromUser]]></FromUserName>
    <CreateTime>12345678</CreateTime>
    <MsgType><![CDATA[video]]></MsgType>
    <Video>
        <MediaId><![CDATA[media_id]]></MediaId>
        <Title><![CDATA[title]]></Title>
        <Description><![CDATA[description]]></Description>
    </Video>
</xml>
 */