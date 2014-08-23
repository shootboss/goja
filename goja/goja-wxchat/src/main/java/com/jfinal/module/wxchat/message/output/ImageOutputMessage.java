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
public class ImageOutputMessage extends OutputMessage {
    private Image Image;

    protected ImageOutputMessage(Builder builder) {
        super(builder);
        this.Image = builder.Image;
    }


    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        Image = image;
    }

    public static class Builder extends OutputMessageBuilder {
        private Image Image;

        public Builder(String toUserName, String fromUserName, long createTime, String msgType) {
            super(toUserName, fromUserName, createTime, msgType);
        }

        public Builder image(Image image) {
            this.Image = image;
            return this;
        }

        @Override
        public ImageOutputMessage build() {
            return new ImageOutputMessage(this);
        }
    }
}
/*
<xml>
    <ToUserName><![CDATA[toUser]]></ToUserName>
    <FromUserName><![CDATA[fromUser]]></FromUserName>
    <CreateTime>12345678</CreateTime>
    <MsgType><![CDATA[image]]></MsgType>
    <Image>
        <MediaId><![CDATA[media_id]]></MediaId>
    </Image>
</xml>
 */