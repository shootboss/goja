package com.jfinal.module.wxchat.message.output;

import com.jfinal.module.wxchat.message.OutputMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <p>
 * .
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-03-26 20:03
 * @since JDK 1.6
 */
@XStreamAlias("xml")
public class VoiceOutputMessage extends OutputMessage {
    private Voice Voice;

    protected VoiceOutputMessage(Builder builder) {
        super(builder);
        this.Voice = builder.Voice;
    }

    public Voice getVoice() {
        return Voice;
    }

    public void setVoice(Voice voice) {
        Voice = voice;
    }


    public static class Builder extends OutputMessageBuilder {
        private Voice Voice;

        public Builder(String toUserName, String fromUserName, long createTime, String msgType) {
            super(toUserName, fromUserName, createTime, msgType);
        }

        public void voice(Voice voice) {
            this.Voice = voice;
        }

        @Override
        public VoiceOutputMessage build() {
            return new VoiceOutputMessage(this);
        }
    }
}
/*
<xml>
    <ToUserName><![CDATA[toUser]]></ToUserName>
    <FromUserName><![CDATA[fromUser]]></FromUserName>
    <CreateTime>12345678</CreateTime>
    <MsgType><![CDATA[voice]]></MsgType>
    <Voice>
        <MediaId><![CDATA[media_id]]></MediaId>
    </Voice>
</xml>
 */
