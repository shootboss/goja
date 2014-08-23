package com.jfinal.module.wxchat.utils;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * .
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-03-26 22:03
 * @since JDK 1.6
 */
public final class HttpUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private final static int DEFAULT_TIMEOUT = 3000;

    /**
     * GET 方式请求
     *
     * @param url 包含请求参数的链接 http://www.abc.com?a=a&b=b
     * @return
     */
    public static String get(String url) {
        InputStream is = null;
        try {
             is = Request.Get(url)
                    .connectTimeout(DEFAULT_TIMEOUT)
                    .socketTimeout(DEFAULT_TIMEOUT)
                     .execute().returnContent().asStream();//asString乱码
            return parseResult(is);
        } catch (IOException e) {
            logger.error("Request.get " + url + "error");
            throw new RuntimeException("Request.get " + url + "error", e);
        } finally {
            if (is != null) {
                try { is.close(); } catch (IOException e) { }
            }
        }
    }

    /**
     * 通过GET请求访问
     *
     * @param url 请求地址
     * @param paramsMap 请求参数
     * @return
     */
    public static String get(String url, Map<String, Object> paramsMap) {
        if (paramsMap != null) {
            String paramAndValues = parseParamsMap(paramsMap);
            if (url.indexOf("?") > -1) {
                url = url + "&" + paramAndValues;
            } else {
                url = url + "?" + paramAndValues;
            }
        }
        return get(url);
    }

    /**
     * 通过POST方式请求
     *
     * @param url 请求地址
     * @param params 请求参数 参数直接通过流写出
     * @return
     */
    public static String post(String url, String params) {
        InputStream is = null;
        try {
           is = Request.Post(url).connectTimeout(DEFAULT_TIMEOUT)
                    .socketTimeout(DEFAULT_TIMEOUT)
                    .bodyString(params, ContentType.APPLICATION_JSON)
                    .execute().returnContent().asStream();
            return parseResult(is);
        } catch (IOException e) {
            logger.error("Request.post " + url + "error");
            throw new RuntimeException("Request.post " + url + "error", e);
        } finally {
            if (is != null) {
                try { is.close(); } catch (IOException e) { }
            }
        }
    }

    /**
     * 通过POST方式请求
     *
     * @param url 请求地址
     * @param params 请求参数
     * @return
     */
    public static String post(String url, Map<String, Object> params) {
        InputStream is = null;
        try {
            is = Request.Post(url).connectTimeout(DEFAULT_TIMEOUT)
                    .socketTimeout(DEFAULT_TIMEOUT)
                    .bodyForm(toForm(params).build())
                    .execute().returnContent().asStream();
            return parseResult(is);
        } catch (IOException e) {
            logger.error("Request.post " + url + "error");
            throw new RuntimeException("Request.post " + url + "error", e);
        } finally {
            if (is != null) {
                try { is.close(); } catch (IOException e) { }
            }
        }
    }



    private static Form toForm(Map<String, Object> params) {
        Form form = Form.form();
        Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            form.add(key, (String) value);
        }
        return form;
    }

    /**
     * 请求参数字符串拼接
     *
     * @param paramsMap
     * @return
     */
    private static String parseParamsMap(Map<String, Object> paramsMap) {
        if (paramsMap != null) {
            StringBuffer sb = new StringBuffer();
            Set<Map.Entry<String, Object>> entrySet = paramsMap.entrySet();
            Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                String key = entry.getKey();
                Object value = entry.getValue();
                sb.append(key + "=" + value + "&");
            }
            return sb.toString();
        }
        return "";
    }

    /**
     * 解析请求结果
     *
     * @param is
     * @return
     * @throws java.io.IOException
     */
    private static String parseResult(InputStream is) throws IOException {
        try {
            StringBuffer sb = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            logger.error("Error parse result", e);
            throw e;
        }
    }



}
