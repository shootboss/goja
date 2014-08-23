/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.kits.common;

import com.github.sog.config.StringPool;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-02-07 12:20
 * @since JDK 1.6
 */
public class CommonKit {
    /**
     * 在不足len位的数字前面自动补零
     */
    public static String getLimitLenStr(String str, int len) {
        if (str == null) {
            return StringPool.EMPTY;
        }
        while (str.length() < len) {
            str = StringPool.ZERO + str;
        }
        return str;
    }

    /**
     * 生成制定位随机字母和数字
     *
     * @param i 位数
     * @return 指定<code>i</code>的字母或者数字
     */
    public static String randomAlphanumeric(int i) {
        return RandomStringUtils.randomAlphanumeric(i);
    }


    /**
     * 将字符串数字转化为int型数字
     *
     * @param str      被转化字符串
     * @param defValue 转化失败后的默认值
     * @return int
     */
    public static int parseInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * 将字符串数字转化为double型数字
     *
     * @param str      被转化字符串
     * @param defValue 转化失败后的默认值
     * @return double
     */
    public static double parseDouble(String str, double defValue) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * 格式化double类型值，使得其末尾保留两位小数
     *
     * @param value 数值
     * @return 格式化后的数值
     */
    public static String formatDouble(double value) {
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(value);
    }

    /**
     * 循环删除最后的某个字符，至不是以该字符结尾
     *
     * @param value 需要处理的字符串
     * @param c     结尾字符
     * @return 截取后的字符串
     */
    public static String removeEnd(String value, char c) {

        if (StringUtils.isBlank(value)) {
            return StringPool.EMPTY;
        }
        String ret = value;
        while (StringUtils.isNotBlank(ret)
                && (StringUtils.lastIndexOf(ret, c) == (ret.length() - 1))) {
            ret = StringUtils.removeEnd(ret, String.valueOf(c));
        }
        return ret;
    }

    public static String removeStart(String value, char c) {
        if (StringUtils.isBlank(value)) {
            return StringPool.EMPTY;
        }

        String ret = value;
        while (StringUtils.isNotBlank(ret)
                && (StringUtils.indexOf(ret, String.valueOf(c)) == 0)) {
            ret = StringUtils.removeStart(ret, String.valueOf(c));
        }
        return ret;
    }

    public static String removeFirstAndEnd(String value, char c) {
        String ret = removeEnd(value, c);
        return removeStart(ret, c);
    }

    /**
     * 处理Url与其参数的组合
     *
     * @param url   页面Url
     * @param param 被加入到该Url后的参数
     * @return 一个完整的Url, 包括参数
     */
    public static String dealUrl(String url, String param) {
        String orgUrl = url;
        url = removeEnd(url, '#');
        url = removeEnd(url, '?');
        if (StringUtils.isBlank(url)) {
            return StringPool.EMPTY;
        }

        if (url.lastIndexOf('/') == (url.length() - 1)) {
            url += "index.html";
        }

        if (StringUtils.isBlank(param)) {
            return orgUrl;
        }

        param = removeStart(param, '&');
        param = removeStart(param, '?');
        if (StringUtils.isBlank(param)
                || (!param.contains(StringPool.EQUALS))
                || (param.indexOf(StringPool.EQUALS) > 0
                && (param.indexOf(StringPool.EQUALS)
                == (param.length() - 1)))) {
            return url;
        }
        if (url.indexOf(StringPool.QUESTION_MARK) > 0) {
            url += StringPool.AMPERSAND + param;
        } else {
            url += StringPool.QUESTION_MARK + param;
        }
        return url;
    }

    /**
     * 截取字符串并以"..."结尾
     *
     * @param inputText 输入内容
     * @param length    截取字节数
     * @return 截取后的字符串
     */
    public static String trimStr(String inputText, int length) {
        // inputText = "[转贴] 独立Wap发展应以内容为王 ";// 输入字符
        int len = 0;
        if (length < 0) {
            length = 24;
        }
        char[] charArray = inputText.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char cn : charArray) {
            byte[] bytes = (String.valueOf(cn)).getBytes();
            len = len + bytes.length;
            if (len > length) {
                sb.append("...");
                break;
            }
            sb.append(cn);

        }
        return sb.toString();
    }
}
