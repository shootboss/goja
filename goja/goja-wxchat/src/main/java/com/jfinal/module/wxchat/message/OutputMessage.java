package com.jfinal.module.wxchat.message;

/**
 * <p>
 *     响应消息基类
 *     对向微信服务器发出去的消息的封装
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
public class OutputMessage {
	private String ToUserName; // 接收方帐号（收到的OpenID）
	private String FromUserName; // 开发者微信号
	private long CreateTime; // 消息创建时间
	private String MsgType; // 消息类型

    protected OutputMessage(OutputMessageBuilder builder){
        ToUserName = builder.ToUserName;
        FromUserName = builder.FromUserName;
        CreateTime = builder.CreateTime;
        MsgType = builder.MsgType;
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


    /**
     * 响应消息基类Builder
     *
     * @param
     */
    protected static class OutputMessageBuilder implements MessageBuilder<OutputMessage> {
        private String ToUserName; // 接收方帐号（收到的OpenID）
        private String FromUserName; // 开发者微信号
        private long CreateTime; // 消息创建时间
        private String MsgType; // 消息类型

        /**
         * 构造方法
         * @param toUserName
         * @param fromUserName
         * @param createTime
         * @param msgType
         */
        protected OutputMessageBuilder(String toUserName, String fromUserName, long createTime, String msgType) {
            ToUserName = toUserName;
            FromUserName = fromUserName;
            CreateTime = createTime;
            MsgType = msgType;
        }

        @Override
        public OutputMessage build() {
            return new OutputMessage(this);
        }
    }
}

/*
 * 回复文本消息 <xml> <ToUserName><![CDATA[toUser]]></ToUserName>
 * <FromUserName><![CDATA[fromUser]]></FromUserName>
 * <CreateTime>12345678</CreateTime> <MsgType><![CDATA[text]]></MsgType>
 * <Content><![CDATA[content]]></Content> </xml>
 * 
 * 回复音乐消息 <xml> <ToUserName><![CDATA[toUser]]></ToUserName>
 * <FromUserName><![CDATA[fromUser]]></FromUserName>
 * <CreateTime>12345678</CreateTime> <MsgType><![CDATA[music]]></MsgType>
 * 
 * <Music> <Title><![CDATA[TITLE]]></Title>
 * <Description><![CDATA[DESCRIPTION]]></Description>
 * <MusicUrl><![CDATA[MUSIC_Url]]></MusicUrl>
 * <HQMusicUrl><![CDATA[HQ_MUSIC_Url]]></HQMusicUrl> </Music> </xml>
 * 
 * 回复图文消息 <xml> <ToUserName><![CDATA[toUser]]></ToUserName>
 * <FromUserName><![CDATA[fromUser]]></FromUserName>
 * <CreateTime>12345678</CreateTime> <MsgType><![CDATA[news]]></MsgType>
 * <ArticleCount>2</ArticleCount> <Articles> <item>
 * <Title><![CDATA[title1]]></Title>
 * <Description><![CDATA[description1]]></Description>
 * <PicUrl><![CDATA[picurl]]></PicUrl> <Url><![CDATA[url]]></Url> </item> <item>
 * <Title><![CDATA[title]]></Title>
 * <Description><![CDATA[description]]></Description>
 * <PicUrl><![CDATA[picurl]]></PicUrl> <Url><![CDATA[url]]></Url> </item>
 * </Articles> </xml>
 */