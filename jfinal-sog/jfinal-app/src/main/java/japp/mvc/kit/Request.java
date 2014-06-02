/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package japp.mvc.kit;

import com.github.sog.config.StringPool;
import com.google.common.base.Strings;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-06-01 20:47
 * @since JDK 1.6
 */
public class Request {



    /**
     * 获取客户端IP地址，此方法用在proxy环境中
     *
     * @param request 当前请求
     * @return 客户端ip，如果获取失败，则返回 127.0.0.1
     */
    public static String remoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(ip)) {
            String[] ips = StringUtils.split(ip, ',');
            if (ips != null) {
                for (String tmpip : ips) {
                    if (StringUtils.isBlank(tmpip))
                        continue;
                    tmpip = tmpip.trim();
                    if (isIPAddr(tmpip) && !tmpip.startsWith("10.") && !tmpip.startsWith("192.168.") && !StringPool.LOCAL_HOST.equals(tmpip)) {
                        return tmpip.trim();
                    }
                }
            }
        }
        ip = request.getHeader("x-real-ip");
        if (isIPAddr(ip))
            return ip;
        ip = request.getRemoteAddr();
        if (ip.indexOf('.') == -1)
            ip = StringPool.LOCAL_HOST;
        return ip;
    }

    /**
     * 判断是否为搜索引擎
     *
     * @param request 当前请求
     * @return true 表示当前请求为搜索引擎发过来的请求。
     */
    public static boolean robot(HttpServletRequest request) {
        String ua = request.getHeader("user-agent");
        return !StringUtils.isBlank(ua) && (ua != null && (ua.contains("Baiduspider") || ua.contains("Googlebot") || ua.contains("sogou") || ua.contains("sina") || ua.contains("iaskspider") || ua.contains("ia_archiver") || ua.contains("Sosospider") || ua.contains("YoudaoBot") || ua.contains("yahoo") || ua.contains("yodao") || ua.contains("MSNBot") || ua.contains("spider") || ua.contains("Twiceler") || ua.contains("Sosoimagespider") || ua.contains("naver.com/robots") || ua.contains("Nutch") || ua.contains("spider")));
    }

    /**
     * 获取COOKIE
     *
     * @param name    cookie 名称
     * @param request 当前请求
     * @return cookie 信息
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie ck : cookies) {
            if (StringUtils.equalsIgnoreCase(name, ck.getName()))
                return ck;
        }
        return null;
    }

    /**
     * 获取COOKIE
     *
     * @param request 当前请求
     * @param name    cokie名称
     * @return 字符串形式的cookie值
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie ck : cookies) {
            if (StringUtils.equalsIgnoreCase(name, ck.getName()))
                return ck.getValue();
        }
        return null;
    }

    /**
     * 设置COOKIE
     *
     * @param name
     * @param value
     * @param maxAge
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response,
                                 String name, String value, int maxAge) {
        setCookie(request, response, name, value, maxAge, true);
    }

    /**
     * 设置COOKIE
     *
     * @param name
     * @param value
     * @param maxAge
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name,
                                 String value, int maxAge, boolean all_sub_domain) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        if (all_sub_domain) {
            String serverName = request.getServerName();
            String domain = getDomainOfServerName(serverName);
            if (domain != null && domain.indexOf('.') != -1) {
                cookie.setDomain('.' + domain);
            }
        }
        cookie.setPath(StringPool.SLASH);
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request,
                                    HttpServletResponse response, String name, boolean all_sub_domain) {
        setCookie(request, response, name, "", 0, all_sub_domain);
    }

    /**
     * 获取用户访问URL中的根域名
     * 例如: www.dlog.cn -> dlog.cn
     *
     * @param host 访问地址
     * @return 根域名
     */
    public static String getDomainOfServerName(String host) {
        if (isIPAddr(host))
            return null;
        String[] names = StringUtils.split(host, '.');
        int len = names.length;
        if (len == 1) return null;
        if (len == 3) {
            return makeup(names[len - 2], names[len - 1]);
        }
        if (len > 3) {
            String dp = names[len - 2];
            if (dp.equalsIgnoreCase("com") || dp.equalsIgnoreCase("gov") || dp.equalsIgnoreCase("net") || dp.equalsIgnoreCase("edu") || dp.equalsIgnoreCase("org"))
                return makeup(names[len - 3], names[len - 2], names[len - 1]);
            else
                return makeup(names[len - 2], names[len - 1]);
        }
        return host;
    }

    /**
     * 判断字符串是否是一个IP地址
     *
     * @param addr 字符串
     * @return true 表示ip地址，否则反之
     */
    public static boolean isIPAddr(String addr) {
        if (StringUtils.isEmpty(addr))
            return false;
        String[] ips = StringUtils.split(addr, '.');
        if (ips.length != 4)
            return false;
        try {
            int ipa = Integer.parseInt(ips[0]);
            int ipb = Integer.parseInt(ips[1]);
            int ipc = Integer.parseInt(ips[2]);
            int ipd = Integer.parseInt(ips[3]);
            return ipa >= 0 && ipa <= 255 && ipb >= 0 && ipb <= 255 && ipc >= 0
                    && ipc <= 255 && ipd >= 0 && ipd <= 255;
        } catch (Exception ignored) {}
        return false;
    }

    private static String makeup(String... ps) {
        StringBuilder s = new StringBuilder();
        for (int idx = 0; idx < ps.length; idx++) {
            if (idx > 0)
                s.append('.');
            s.append(ps[idx]);
        }
        return s.toString();
    }

    /**
     * 获取HTTP端口
     *
     * @param request 当前请求
     * @return http端口
     */
    public static int getHttpPort(HttpServletRequest request) {
        try {
            return new URL(request.getRequestURL().toString()).getPort();
        } catch (MalformedURLException excp) {
            return 80;
        }
    }

    /**
     * 获取浏览器提交的整形参数
     *
     * @param request      当前请求
     * @param param        参数
     * @param defaultValue 默认值
     * @return 值
     */
    public static int param(HttpServletRequest request, String param, int defaultValue) {
        return NumberUtils.toInt(request.getParameter(param), defaultValue);
    }

    /**
     * 获取浏览器提交的字符串参
     *
     * @param request      当前请求
     * @param param        参数
     * @param defaultValue 默认值
     * @return 值
     */
    public static String param(HttpServletRequest request, String param, String defaultValue) {
        String value = request.getParameter(param);
        return (StringUtils.isEmpty(value)) ? defaultValue : value;
    }

    /**
     * 获取浏览器提交的整形参数
     *
     * @param request      当前请求
     * @param param        参数
     * @param defaultValue 默认值
     * @return 值
     */
    public static long param(HttpServletRequest request, String param, long defaultValue) {
        return NumberUtils.toLong(request.getParameter(param), defaultValue);
    }

    public static long[] paramValues(HttpServletRequest req, String name) {
        String[] values = req.getParameterValues(name);
        if (values == null) return null;
        return (long[]) ConvertUtils.convert(values, long.class);
    }

    /**
     * 判断当前Request是否位Ajax请求，通过消息头来进行判断.
     *
     * @param request 当前请求对象
     * @return 如果返回<code>true</code>则，表示当前的Request是由Ajax请求，否则反之
     */
    public static boolean ajax(HttpServletRequest request) {
        String x_requested = request.getHeader("x-requested-with");
        return Strings.isNullOrEmpty(x_requested) && "XMLHttpRequest".equals(x_requested);
    }
}
