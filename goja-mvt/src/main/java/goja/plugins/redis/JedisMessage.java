/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package goja.plugins.redis;

import java.io.Serializable;

public interface JedisMessage<T extends Serializable> {
    public void onMessage(T message);
}
