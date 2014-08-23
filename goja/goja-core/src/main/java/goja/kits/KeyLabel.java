/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package goja.kits;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import goja.app.StringPool;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class KeyLabel implements Comparable<KeyLabel>, Serializable {
    private String key;
    private String label;

    public KeyLabel() {
        super();
    }

    public KeyLabel(String key, String label) {
        this.label = label;
        this.key = key;
    }

    public static Map<String, String> converListToMap(List<KeyLabel> list) {
        Map<String, String> map = Maps.newLinkedHashMap();
        for (KeyLabel k : list) {
            map.put(k.getKey(), k.getLabel());
        }

        return map;
    }

    public static List<KeyLabel> converMapToList(Map<String, Object> map) {
        List<KeyLabel> list = Lists.newArrayList();
        Set<String> keys = map.keySet();
        for (Object key : keys) {
            KeyLabel keyLabel = new KeyLabel();
            keyLabel.setKey(key.toString());
            keyLabel.setLabel(map.get(key) + "");
            list.add(keyLabel);
        }
        return list;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String value) {
        this.key = value;
    }

    @Override
    public int compareTo(KeyLabel other) {
        return this.label.compareTo(other.getLabel());
    }

    @Override
    public String toString() {
        return "KeyLabel[" + this.label + ", " + this.key + StringPool.RIGHT_SQ_BRACKET;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof KeyLabel)) {
            return false;
        }

        KeyLabel bean = (KeyLabel) obj;
        int nil = (this.getKey() == null) ? 1 : 0;
        nil += (bean.getKey() == null) ? 1 : 0;

        if (nil == 2) {
            return true;
        } else if (nil == 1) {
            return false;
        } else {
            return this.getKey().equals(bean.getKey());
        }
    }

    @Override
    public int hashCode() {
        return (this.getKey() == null) ? 17 : this.getKey().hashCode();
    }
}
