package com.jfinal.module.wxchat.message.output;


import com.jfinal.module.wxchat.message.OutputMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <p>
 * 回复音乐消息 对向微信服务器发出去的消息的封装
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
@XStreamAlias("xml")
public class MusicOutputMessage extends OutputMessage {

    private Music Music;

    protected MusicOutputMessage(Builder builder) {
        super(builder);
        this.Music = builder.Music;
    }

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }

    /**
     * MusicOutputMessageBuilder
     */
    public static class Builder extends OutputMessageBuilder {
        private Music Music;

        public Builder(String toUserName, String fromUserName, long createTime, String msgType) {
            super(toUserName, fromUserName, createTime, msgType);
        }

        public Builder music(Music music) {
            Music = music;
            return this;
        }

        @Override
        public MusicOutputMessage build() {
            return new MusicOutputMessage(this);
        }
    }

}

/*
<xml>
    <ToUserName><![CDATA[toUser]]></ToUserName>
    <FromUserName><![CDATA[fromUser]]></FromUserName>
    <CreateTime>12345678</CreateTime>
    <MsgType><![CDATA[music]]></MsgType>
    <Music>
        <Title><![CDATA[TITLE]]></Title>
        <Description><![CDATA[DESCRIPTION]]></Description>
        <MusicUrl><![CDATA[MUSIC_Url]]></MusicUrl>
        <HQMusicUrl><![CDATA[HQ_MUSIC_Url]]></HQMusicUrl>
        <ThumbMediaId><![CDATA[media_id]]></ThumbMediaId>
    </Music>
</xml>
 */