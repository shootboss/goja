/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package goja.plugin.redis;

import com.github.sog.config.StringPool;

public class TopicNest {
    private StringBuilder sb;
    private String        key;

    public TopicNest(String key) {
        this.key = key;
    }

    public String key() {
        prefix();
        String generatedKey = sb.toString();
        generatedKey = generatedKey.substring(0, generatedKey.length() - 1);
        sb = null;
        return generatedKey;
    }

    private void prefix() {
        if (sb == null) {
            sb = new StringBuilder();
            sb.append(key);
            sb.append(StringPool.COLON);
        }
    }

    public TopicNest cat(int id) {
        prefix();
        sb.append(id);
        sb.append(StringPool.COLON);
        return this;
    }

    public TopicNest cat(String field) {
        prefix();
        sb.append(field);
        sb.append(StringPool.COLON);
        return this;
    }
}
