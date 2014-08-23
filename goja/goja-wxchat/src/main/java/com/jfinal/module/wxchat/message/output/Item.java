package com.jfinal.module.wxchat.message.output;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <p>
 *      图文消息条目信息
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
@XStreamAlias("item")
public class Item {
	/** 图文消息标题 */
	private String Title;
	/** 图文消息描述 */
	private String Description;
	/** 图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80。 */
	private String PicUrl;
	/** 点击图文消息跳转链接 */
	private String Url;

	public Item(String title, String description, String picUrl, String url) {
		super();
		Title = title;
		Description = description;
		PicUrl = picUrl;
		Url = url;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

}