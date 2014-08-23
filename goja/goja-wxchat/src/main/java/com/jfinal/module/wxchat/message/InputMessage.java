package com.jfinal.module.wxchat.message;

/**
 * <p>
 * 请求消息基类
 * 对微信服务器发送来的消息的封装
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
public class InputMessage {

    private String ToUserName; // 开发者微信号
    private String FromUserName; // 发送方帐号（一个OpenID）
    private long CreateTime; // 创建时间(毫秒数)
    private String MsgType; // 消息类型

    public InputMessage(InputMessageBuilder builder) {
        this.ToUserName = builder.ToUserName;
        this.FromUserName = builder.FromUserName;
        this.CreateTime = builder.CreateTime;
        this.MsgType = builder.MsgType;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    protected static class InputMessageBuilder implements MessageBuilder {
        private String ToUserName; // 开发者微信号
        private String FromUserName; // 发送方帐号（一个OpenID）
        private long CreateTime; // 创建时间(毫秒数)
        private String MsgType; // 消息类型

        public InputMessageBuilder(String toUserName, String fromUserName, long createTime, String msgType) {
            ToUserName = toUserName;
            FromUserName = fromUserName;
            CreateTime = createTime;
            MsgType = msgType;
        }

        @Override
        public InputMessage build() {
            return new InputMessage(this);
        }
    }
}

