/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package goja.plugins.redis;

import com.jfinal.plugin.IPlugin;
import goja.StringPool;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Protocol;

import static goja.GojaConfig.getPropertyToBoolean;
import static goja.GojaConfig.getPropertyToInt;
import static goja.GojaConfig.getPropertyToLong;
import static goja.init.InitConst.REDIS_MAXIDLE;
import static goja.init.InitConst.REDIS_MAXTOTAL;
import static goja.init.InitConst.REDIS_MINEVICTABLEIDLETIMEMILLIS;
import static goja.init.InitConst.REDIS_MINIDLE;
import static goja.init.InitConst.REDIS_NUMTESTSPEREVICTIONRUN;
import static goja.init.InitConst.REDIS_SOFTMINEVICTABLEIDLETIMEMILLIS;
import static goja.init.InitConst.REDIS_TESTONBORROW;
import static goja.init.InitConst.REDIS_TESTONRETURN;
import static goja.init.InitConst.REDIS_TESTWHILEIDLE;
import static goja.init.InitConst.REDIS_TIMEBETWEENEVICTIONRUNSMILLIS;
import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.DEFAULT_NUM_TESTS_PER_EVICTION_RUN;
import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.DEFAULT_TEST_ON_BORROW;
import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.DEFAULT_TEST_ON_RETURN;
import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.DEFAULT_TEST_WHILE_IDLE;
import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;
import static org.apache.commons.pool2.impl.GenericObjectPoolConfig.DEFAULT_MAX_IDLE;
import static org.apache.commons.pool2.impl.GenericObjectPoolConfig.DEFAULT_MAX_TOTAL;
import static org.apache.commons.pool2.impl.GenericObjectPoolConfig.DEFAULT_MIN_IDLE;

@SuppressWarnings("UnusedDeclaration")
public class JedisPlugin implements IPlugin {

    public static final String DEFAULT_HOST = StringPool.LOCAL_HOST;
    public static final int    DEFAULT_PORT = Protocol.DEFAULT_PORT;

    private final String    host;
    private final int       port;
    private final int       timeout;

    public        JedisPool pool;
    private       String    password;


    public JedisPlugin() {
        host = DEFAULT_HOST;
        port = Protocol.DEFAULT_PORT;
        timeout = Protocol.DEFAULT_TIMEOUT;
    }

    public JedisPlugin(String host) {
        this.host = host;
        port = Protocol.DEFAULT_PORT;
        timeout = Protocol.DEFAULT_TIMEOUT;
    }

    public JedisPlugin(String host, int port) {
        this.host = host;
        this.port = port;
        timeout = Protocol.DEFAULT_TIMEOUT;
    }

    public JedisPlugin(String host, int port, int timeout) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
    }


    @Override
    public boolean start() {

        JedisShardInfo shardInfo = new JedisShardInfo(host, port, timeout);
        if (StringUtils.isNotBlank(password)) {
            shardInfo.setPassword(password);
        }
        JedisPoolConfig poolConfig = new JedisPoolConfig();

        poolConfig.setMaxIdle(getPropertyToInt(REDIS_MAXIDLE, DEFAULT_MAX_IDLE));
        poolConfig.setMaxTotal(getPropertyToInt(REDIS_MAXTOTAL, DEFAULT_MAX_TOTAL));
        poolConfig.setMinEvictableIdleTimeMillis(getPropertyToLong(REDIS_MINEVICTABLEIDLETIMEMILLIS, DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS));
        poolConfig.setMinIdle(getPropertyToInt(REDIS_MINIDLE, DEFAULT_MIN_IDLE));
        poolConfig.setNumTestsPerEvictionRun(getPropertyToInt(REDIS_NUMTESTSPEREVICTIONRUN, DEFAULT_NUM_TESTS_PER_EVICTION_RUN));
        poolConfig.setSoftMinEvictableIdleTimeMillis(getPropertyToLong(REDIS_SOFTMINEVICTABLEIDLETIMEMILLIS, DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS));
        poolConfig.setTimeBetweenEvictionRunsMillis(getPropertyToLong(REDIS_TIMEBETWEENEVICTIONRUNSMILLIS, DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS));
        poolConfig.setTestWhileIdle(getPropertyToBoolean(REDIS_TESTWHILEIDLE, DEFAULT_TEST_WHILE_IDLE));
        poolConfig.setTestOnReturn(getPropertyToBoolean(REDIS_TESTONRETURN, DEFAULT_TEST_ON_RETURN));
        poolConfig.setTestOnBorrow(getPropertyToBoolean(REDIS_TESTONBORROW, DEFAULT_TEST_ON_BORROW));

        pool = new JedisPool(poolConfig
                , shardInfo.getHost()
                , shardInfo.getPort()
                , shardInfo.getTimeout(),
                shardInfo.getPassword());
        JedisKit.init(pool);
        return true;
    }

    @Override
    public boolean stop() {
        try {
            pool.destroy();
        } catch (Exception ex) {
            System.err.println("Cannot properly close Jedis pool:" + ex);
        }
        pool = null;
        return true;
    }

}
