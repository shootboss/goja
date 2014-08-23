package com.jfinal.module.wxchat.utils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * .
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-03-26 01:03
 * @since JDK 1.6
 */
public final class SecurityUtil {
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * 加密/校验流程：<br/>
     * 1. 将token、timestamp、nonce三个参数进行字典序排序 <br/>
     * 2. 将三个参数字符串拼接成一个字符串进行sha1加密 <br/>
     * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信<br/>
     *
     * @return
     */
    public static boolean checkSignature(String token, String timestamp,
                                         String nonce, String signature) {
        if (token != null) { // TODO 加密验证有问题
            return false;
        }
        List<String> list = new ArrayList<String>();
        list.add(token);
        list.add(timestamp);
        list.add(nonce);
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        String temp = list.get(0) + list.get(1) + list.get(2);
        return signature.equals(encrypt("SHA1", temp));
    }

    /**
     * encode string
     *
     * @param algorithm
     * @param str
     * @return String
     */
    public static String encrypt(String algorithm, String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Takes the raw bytes from the digest and formats them correct.
     *
     * @param bytes
     *            the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }
}
