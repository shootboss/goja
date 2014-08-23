/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.mvc.flash;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-03-27 11:47
 * @since JDK 1.6
 */
public interface FlashManager {

    /**
     * Flash msg 默认key值
     */
    public String FLASH_MSG_KEY = "flash-msg";

    /**
     * 添加flash信息到缓存中。
     *
     * @param session   session路径
     * @param curAction 当前ActionPath
     * @param key       键
     * @param value     值
     */
    public void setFlash(HttpSession session, String curAction, String key,
                         Object value);

    /**
     * 在调用redirect forwardAction
     * 时回调此接口，将以当前actionPath为key更替为下一个请求actionPath作为key。
     *
     * @param session    session的Id值
     * @param curAction  当前ActionPath
     * @param nextAction 下一个ActionPath
     */
    public void updateFlashKey(HttpSession session, String curAction,
                               String nextAction);

    /**
     * 从cache中取得Flash的Map
     *
     * @param session   session路径
     * @param curAction 当前ActionPath
     * @return Flash的Map
     */
    public ConcurrentHashMap<String, Object> getFlash(HttpSession session, String curAction);
}
