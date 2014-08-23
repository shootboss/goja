package com.jfinal.module.wxchat.core;

/**
 * <p>
 *  常量类
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-03-25 23:03
 * @since JDK 1.6
 */
public interface Constants {
    //微信配置信息
    public static final String WECHAT_APPID = "wechat.appid";
    public static final String WECHAT_APPSECRET = "wechat.appsecret";
    public static final String WECHAT_TOKEN = "wechat.token"; //预设TOKEN


    public static final String WECHAT_PROCESSOR = "wechat.processor"; //处理消息的具体实现类

    //---请求方式---
    public static final String GET = "GET";
    public static final String POST = "POST";

    //-----------------消息类型
    /** 文本消息 */
	public static String MSGTYPE_TEXT = "text";
	/** 图片消息 */
	public static String MSGTYPE_IMAGE = "image";
	/** 地理位置消息 */
	public static String MSGTYPE_LOCATION = "location";
	/** 链接消息 */
	public static String MSGTYPE_LINK = "link";
	/** 事件推送(事件消息) */
	public static String MSGTYPE_EVENT = "event";
	/** 图文消息 */
	public static String MSGTYPE_NEWS = "news";
	/** 音乐消息 */
	public static String MSGTYPE_MUSIC = "music";
    /** 视频消息 */
    public static final String MSGTYPE_VIDEO = "video";
    /** 语音消息 */
    public static final String MSGTYPE_VOICE = "voice";
	
	//-----EVENT
	/** 订阅事件，为关注扫描二维码 */
	public static String EVENT_SUBSCRIBE = "subscribe"; 
	/** 取消订阅事件 */
	public static String EVENT_UNSUBSCRIBE = "unsubscribe";
	/** 自定义菜单点击拉取消息事件 */
	public static String EVENT_CLICK = "click";
    /** 自定义菜单点击跳转链接事件 */
	public static String EVENT_VIEW = "view";
    /** 已关注扫描二维码事件 */
    public static String EVENT_SCAN_ = "SCAN";
    /** 上报地理位置事件 */
    public static String EVENT_LOCATION = "LOCATION";


}
