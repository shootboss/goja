package com.jfinal.module.wxchat.message.input;

import com.jfinal.module.wxchat.message.InputMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <p>
 *      地理位置消息[普通消息] 对微信服务器发送来的消息的封装
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
@XStreamAlias("xml")
public class LocationInputMessage extends InputMessage {
	private double Location_X; // 地理位置纬度
	private double Location_Y; // 地理位置经度
	private int Scale; // 地图缩放大小
	private String Label; // 地理位置信息
    private String MsgId; //消息id，64位整型

    public LocationInputMessage(Builder builder) {
        super(builder);
        this.Location_X = builder.Location_X;
        this.Location_Y = builder.Location_Y;
        this.Scale = builder.Scale;
        this.Label = builder.Label;
        this.MsgId = builder.MsgId;
    }

    public double getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(double locationX) {
		Location_X = locationX;
	}

	public double getLocation_Y() {
		return Location_Y;
	}

	public void setLocation_Y(double locationY) {
		Location_Y = locationY;
	}

	public int getScale() {
		return Scale;
	}

	public void setScale(int scale) {
		Scale = scale;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		Label = label;
	}

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    public static class Builder extends InputMessageBuilder {
        private double Location_X; // 地理位置纬度
        private double Location_Y; // 地理位置经度
        private int Scale; // 地图缩放大小
        private String Label; // 地理位置信息
        private String MsgId; //消息id，64位整型

        public Builder(String toUserName, String fromUserName, long createTime, String msgType) {
            super(toUserName, fromUserName, createTime, msgType);
        }

        public Builder location_X(double location_X) {
            Location_X = location_X;
            return this;
        }

        public Builder location_Y(double location_Y) {
            Location_Y = location_Y;
            return this;
        }

        public Builder scale(int scale) {
            Scale = scale;
            return this;
        }

        public Builder label(String label) {
            Label = label;
            return this;
        }

        public Builder msgId(String msgId) {
            MsgId = msgId;
            return this;
        }

        @Override
        public LocationInputMessage build() {
            return new LocationInputMessage(this);
        }
    }

}

/*
 * 地理位置消息
 *  <xml>
 *      <ToUserName><![CDATA[toUser]]></ToUserName>
 *      <FromUserName><![CDATA[fromUser]]></FromUserName>
 *      <CreateTime>1351776360</CreateTime>
 *      <MsgType><![CDATA[location]]></MsgType>
 *      <Location_X>23.134521</Location_X>
 *      <Location_Y>113.358803</Location_Y>
 *      <Scale>20</Scale>
 *      <Label><![CDATA[位置信息]]></Label>
 *      <MsgId>1234567890123456</MsgId>
 *  </xml>
 */

