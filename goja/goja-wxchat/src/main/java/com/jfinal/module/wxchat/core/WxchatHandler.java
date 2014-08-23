package com.jfinal.module.wxchat.core;

import com.github.sog.config.JFinalApp;
import com.github.sog.config.StringPool;
import com.jfinal.handler.Handler;
import com.jfinal.module.wxchat.message.OutputMessage;
import com.jfinal.module.wxchat.processor.Processor;
import com.jfinal.module.wxchat.utils.MessageUtil;
import com.jfinal.module.wxchat.utils.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * <p>
 *     结合jFinal使用
 *
 *     只需要在启用handler即可
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
public class WxchatHandler extends Handler {
    private final Logger logger = LoggerFactory.getLogger(WxchatHandler.class);
    private final Configuration config = Configuration.me;

    private Processor processor;

    public WxchatHandler() {
        String className = JFinalApp.configuration.getProperty(Constants.WECHAT_PROCESSOR);
        createProcessor(className);
    }

    private void createProcessor(String configClass) {
        if (configClass == null)
            throw new RuntimeException("Please set processor class");

        try {
            Object temp = Class.forName(configClass).newInstance();
            if (temp instanceof Processor)
                processor = (Processor)temp;
            else
                throw new RuntimeException("Can not create instance of class: " + configClass);
        } catch (InstantiationException e) {
            throw new RuntimeException("Can not create instance of class: " + configClass, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Can not create instance of class: " + configClass, e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found: " + configClass, e);
        }
    }

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] booleans) {
        try {
            if (request.getMethod().equalsIgnoreCase("GET")) {
                doGet(request, response);
            } else {
                doPost(request, response);
            }
            booleans[0] = true;
        } catch (Exception e) {
            booleans[0] = false;
            logger.error("Error process target: {}", target);
        }
    }

    private void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String signature = request.getParameter("signature");	//微信加密签名
        String timestamp = request.getParameter("timestamp"); 	//时间戳
        String nonce = request.getParameter("nonce");	//随机数
        String echostr = request.getParameter("echostr");//认证字符串

        //TOKEN配置文件中读取
        if (SecurityUtil.checkSignature(config.getToken(), signature, timestamp, nonce)) {
            if (logger.isInfoEnabled()) {
                logger.info("Token authentication is successful");
            }
            response.getWriter().write(echostr);
        }
    }

    private void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding(StringPool.UTF_8);
        response.setCharacterEncoding(StringPool.UTF_8);
        response.setContentType("text/xml;charset=utf-8");

        try {
            PrintWriter out = response.getWriter();
            //解析微信服务器推送的消息
            Map<String,String> map = MessageUtil.parseXml(request);
            if (logger.isInfoEnabled()) {
                logger.info("The message is：{}", map);
            }
            OutputMessage respBaseMessage = processor.process(map);
            responseMessage(out, respBaseMessage);
            out.flush();
        } catch (Exception e) {
            logger.error("process message error", e);
        }
    }

    private void responseMessage(PrintWriter out, OutputMessage outputMessage) {
        if (outputMessage != null) {
            String xml = MessageUtil.outputMessageToXml(outputMessage);
            out.print(xml);
        }
    }
}
