package com.jfinal.module.wxchat.processor;

import com.jfinal.module.wxchat.message.OutputMessage;

import java.util.Map;

/**
 * <p>
 * 微信消息处理接口
 *
 * 需要指定消息处理类处理消息
 *
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-03-26 18:03
 * @since JDK 1.6
 */
public interface Processor {

    OutputMessage process(Map<String, String> message);

}
