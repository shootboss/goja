package com.jfinal.module.wxchat.utils;

import com.jfinal.module.wxchat.message.InputMessage;
import com.jfinal.module.wxchat.message.OutputMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *     消息处理工具类
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
public final class MessageUtil {

    private static final String PREFIX_CDATA    = "<![CDATA[";
    private static final String SUFFIX_CDATA    = "]]>";

    private static final XStream xStream = new SupportCDATAXStream(new SupportCDATAXppDriver());


    /**
     * 可支持某一字段可以加入CDATA标签
     * 如果需要某一字段使用原文
     * 就需要在String类型的text的头加上"<![CDATA["和结尾处加上"]]>"标签，
     * 以供XStream输出时进行识别
     */
    private static class SupportCDATAXStream extends XStream {
        public SupportCDATAXStream(XppDriver xppDriver) {
            super(xppDriver);
        }
    }
    private static class SupportCDATAXppDriver extends XppDriver {
        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                @Override
                protected void writeText(QuickWriter writer, String text) {
                    if(text.startsWith(PREFIX_CDATA)
                            && text.endsWith(SUFFIX_CDATA)) {
                        writer.write(text);
                    }else{
                        super.writeText(writer, text);
                    }
                }
            };
        }
    }

    /**
     * 初始化XStream
     * 可支持某一字段可以加入CDATA标签
     * 如果需要某一字段使用原文
     * 就需要在String类型的text的头加上"<![CDATA["和结尾处加上"]]>"标签，
     * 以供XStream输出时进行识别
     * @param isAddCDATA 是否支持CDATA标签
     * @return
     */
    public static XStream initXStream(boolean isAddCDATA){
        XStream xstream = null;
        if(isAddCDATA){
            xstream =  new XStream(
                    new XppDriver() {
                        public HierarchicalStreamWriter createWriter(Writer out) {
                            return new PrettyPrintWriter(out) {
                                protected void writeText(QuickWriter writer, String text) {
                                    if(text.startsWith(PREFIX_CDATA)
                                            && text.endsWith(SUFFIX_CDATA)) {
                                        writer.write(text);
                                    }else{
                                        super.writeText(writer, text);
                                    }
                                }
                            };
                        };
                    }
            );
        }else{
            xstream = new XStream();
        }
        return xstream;
    }

    /**
     * 解析request内容成Map对象
     *
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(HttpServletRequest request)
            throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        // 释放资源
        inputStream.close();
        return map;
    }

    /**
     * 将回复消息对象转换成xml数据格式
     *
     * @param message
     * @return
     */
    public static String outputMessageToXml(OutputMessage message) {
        //xStream.alias("xml", message.getClass());
        xStream.processAnnotations(message.getClass());
        return xStream.toXML(message);
    }

    /**
     * 将xml转换成请求消息
     *
     * @return
     */
    public static InputMessage xmlToInputMessage(String xml, Class<? extends InputMessage> clazz) {
        xStream.processAnnotations(clazz);
        return (InputMessage) xStream.fromXML(xml);
    }

}
