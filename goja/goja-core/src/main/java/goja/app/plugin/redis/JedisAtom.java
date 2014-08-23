/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.app.plugin.redis;

import redis.clients.jedis.Transaction;

public interface JedisAtom {

    void action(Transaction transaction);
}
