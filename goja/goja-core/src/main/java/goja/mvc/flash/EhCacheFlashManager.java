/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.mvc.flash;

import com.jfinal.plugin.ehcache.CacheKit;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-03-27 11:48
 * @since JDK 1.6
 */
public class EhCacheFlashManager implements FlashManager {

    /**
     * falsh 存放在cache中的值。
     */
    private final String flashCacheName;

    /**
     * 锁
     */
    private ReentrantLock lock = new ReentrantLock();

    /**
     * 构造函数
     *
     * @param flashCacheName ehcache 中的key值。
     */
    public EhCacheFlashManager(String flashCacheName) {
        if (StringUtils.isBlank(flashCacheName)) {
            throw new IllegalArgumentException("flashCacheName can not be blank.");
        }
        this.flashCacheName = flashCacheName;
    }

    @Override
    public void setFlash(HttpSession session, String curAction, String key, Object value) {
        String sessionKey = session.getId();
        sessionKey = sessionKey + curAction.replace("/", "_");
        try {
            lock.lock();
            ConcurrentHashMap<String, Object> map;
            Object obj = CacheKit.get(flashCacheName, sessionKey);
            if (obj != null) {
                map = (ConcurrentHashMap<String, Object>) obj;
            } else {
                map = new ConcurrentHashMap<String, Object>();
                CacheKit.put(flashCacheName, sessionKey, map);
            }
            map.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void updateFlashKey(HttpSession session, String curAction, String nextAction) {
        String sessionKey = session.getId();
        String oldKey = sessionKey + curAction.replace("/", "_");
        String newkey = sessionKey + nextAction.replace("/", "_");
        try {
            lock.lock();
            Object obj = CacheKit.get(flashCacheName, oldKey);
            if (obj != null) {
                CacheKit.remove(flashCacheName, oldKey);
                CacheKit.put(flashCacheName, newkey, obj);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public ConcurrentHashMap<String, Object> getFlash(HttpSession session, String curAction) {
        String sessionKey = session.getId();
        String sessionActionKey = sessionKey + curAction.replace("/", "_");
        ConcurrentHashMap<String, Object> map = null;
        try {
            lock.lock();
            Object obj = CacheKit.get(flashCacheName, sessionActionKey);
            if (obj != null) {
                map = (ConcurrentHashMap<String, Object>) obj;
                CacheKit.remove(flashCacheName, sessionActionKey);
            }
        } finally {
            lock.unlock();
        }
        return map;
    }
}
