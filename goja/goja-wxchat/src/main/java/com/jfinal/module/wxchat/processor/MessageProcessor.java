package com.jfinal.module.wxchat.processor;

import com.jfinal.module.wxchat.message.InputMessage;
import com.jfinal.module.wxchat.message.input.*;
import com.jfinal.module.wxchat.utils.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 消息处理抽象类
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-03-26 19:03
 * @since JDK 1.6
 */
public abstract class MessageProcessor implements Processor {
    protected Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    public InputMessage convertToMessage(Map<String, String> message) {
        String MsgType = message.get("MsgType");
        Class<? extends InputMessage> clazz = Clazz.getClazz(MsgType);
        String xml = mapToXml(message);
        return MessageUtil.xmlToInputMessage(xml, clazz);
    }

    public String mapToXml(Map<String, String> message) {
        Set<Map.Entry<String, String>> set = message.entrySet();
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (Map.Entry<String, String> entry : set) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append("<").append(key).append(">")
                .append(value)
                .append("</").append(key).append(">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    private enum Clazz {
        EVENT("event", EventInputMessage.class),
        IMAGE("image", ImageInputMessage.class),
        LINK("link", LinkInputMessage.class),
        LOCATION("location", LocationInputMessage.class),
        TEXT("text", TextInputMessage.class),
        VIDEO("video", VideoInputMessage.class),
        VOICE("voice", VoiceInputMessage.class);

        private String msgType;
        private Class<? extends InputMessage> clazz;

        Clazz(String msgType, Class<? extends InputMessage> clazz) {
            this.msgType = msgType;
            this.clazz = clazz;
        }

        public static Class<? extends InputMessage> getClazz(String msgType) {
            Clazz[] clazzs = Clazz.values();
            for (Clazz clz : clazzs) {
                if (clz.msgType.equals(msgType)) {
                    return clz.clazz;
                }
            }
            return null;
        }

    }
}
