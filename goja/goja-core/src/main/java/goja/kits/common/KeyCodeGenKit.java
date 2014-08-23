/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.kits.common;

import goja.kits.date.DateProvider;
import goja.kits.encry.EncodeKit;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * <p>
 * 产品序列号,生成方法，默认的，具体的可根据业务自己实现.
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-02-07 12:18
 * @since JDK 1.6
 */
public class KeyCodeGenKit {

    /**
     * 产品序列号前10位+KEY前5位+5位随机数字和字符（大小写）+时间戳 MD5 加密
     *
     * @param productSerialNo 产品序列号
     * @param codeKey         产品代码
     * @return 授权码
     */
    public static String generateKeyCode(String productSerialNo, String codeKey) {
        StringBuilder sb = new StringBuilder();
        sb.append(CommonKit.getLimitLenStr(productSerialNo, 10))
                .append(CommonKit.getLimitLenStr(codeKey, 5))
                .append(CommonKit.randomAlphanumeric(5))
                .append(DateProvider.DEFAULT.getCurrentTimeInMillis());
        return digestPassword(sb.toString());
    }


    /**
     * 加密
     *
     * @param password 需要加密的字符串
     */
    private static String digestPassword(String password) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[EncodeKit.SALT_SIZE];
            random.nextBytes(salt);

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt);
            md.update(password.getBytes());
            byte[] digest = md.digest();

            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(salt) + encoder.encode(digest);
        } catch (NoSuchAlgorithmException ne) {
            System.err.println(ne.toString());
            return null;
        } catch (Exception e) {
            System.err.println(e.toString());
            return null;
        }
    }
}
