package com.jfinal.module.wxchat.message.output;

import com.jfinal.module.wxchat.message.OutputMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * <p>
 * 回复图文消息 对向微信服务器发出去的消息的封装
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
@XStreamAlias("xml")
public class NewsOutputMessage extends OutputMessage {
    /**
     * 图文消息个数，限制为10条以内
     */
    private Integer ArticleCount;
    /**
     * 图文条目信息
     */
    private List<Item> Articles;

    protected NewsOutputMessage(Builder builder) {
        super(builder);
        this.ArticleCount = builder.ArticleCount;
        this.Articles = builder.Articles;
    }

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(Integer articleCount) {
        ArticleCount = articleCount;
    }

    public List<Item> getArticles() {
        return Articles;
    }

    public void setArticles(List<Item> articles) {
        Articles = articles;
    }


    public static class Builder extends OutputMessageBuilder {
        private Integer ArticleCount;
        private List<Item> Articles;

        public Builder(String toUserName, String fromUserName, long createTime, String msgType) {
            super(toUserName, fromUserName, createTime, msgType);
        }

        public Builder articleCount(Integer articleCount) {
            this.ArticleCount = articleCount;
            return this;
        }

        public Builder articles(List<Item> articles) {
            this.Articles = articles;
            return this;
        }

        @Override
        public NewsOutputMessage build() {
            return new NewsOutputMessage(this);
        }
    }
}
/*
<xml>
    <ToUserName><![CDATA[toUser]]></ToUserName>
    <FromUserName><![CDATA[fromUser]]></FromUserName>
    <CreateTime>12345678</CreateTime>
    <MsgType><![CDATA[news]]></MsgType>
    <ArticleCount>2</ArticleCount>
    <Articles>
        <item>
            <Title><![CDATA[title1]]></Title>
            <Description><![CDATA[description1]]></Description>
            <PicUrl><![CDATA[picurl]]></PicUrl>
            <Url><![CDATA[url]]></Url>
        </item>
        <item>
            <Title><![CDATA[title]]></Title>
            <Description><![CDATA[description]]></Description>
            <PicUrl><![CDATA[picurl]]></PicUrl>
            <Url><![CDATA[url]]></Url>
        </item>
    </Articles>
</xml>
 */
