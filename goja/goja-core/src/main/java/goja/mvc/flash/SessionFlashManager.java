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
 * @version 1.0 2014-03-27 11:56
 * @since JDK 1.6
 */
public class SessionFlashManager implements FlashManager {
    /**
     * 默认存储session前缀
     */
    private final static String sessionKeyPrefix = "_flash_";

    @Override
    public void setFlash(HttpSession session, String curAction, String key, Object value) {
        String sessionKey = sessionKeyPrefix + curAction.replace("/", "_");
        Object obj = session.getAttribute(sessionKey);
        ConcurrentHashMap<String, Object> map;
        if (obj != null) {
            map = (ConcurrentHashMap<String, Object>) obj;
        } else {
            map = new ConcurrentHashMap<String, Object>();
            session.setAttribute(sessionKey, map);
        }
        map.put(key, value);

    }

    @Override
    public void updateFlashKey(HttpSession session, String curAction, String nextAction) {
        String oldKey = sessionKeyPrefix + curAction.replace("/", "_");
        String newkey = sessionKeyPrefix + nextAction.replace("/", "_");
        Object obj = session.getAttribute(oldKey);
        if (obj != null) {
            session.removeAttribute(oldKey);
            session.setAttribute(newkey, obj);
        }
    }

    @Override
    public ConcurrentHashMap<String, Object> getFlash(HttpSession session, String curAction) {
        String sessionActionKey = sessionKeyPrefix + curAction.replace("/", "_");
        ConcurrentHashMap<String, Object> map = null;
        Object obj = session.getAttribute(sessionActionKey);
        if (obj != null) {
            map = (ConcurrentHashMap<String, Object>) obj;
            session.removeAttribute(sessionActionKey);
        }
        return map;
    }
}
