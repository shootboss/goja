/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package goja.plugin.redis;

import redis.clients.jedis.Jedis;

public interface JedisAction<T> {

    T action(Jedis jedis);
}
