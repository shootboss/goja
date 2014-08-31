/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package goja.plugins.redis;

import goja.GojaConfig;
import goja.StringPool;
import goja.init.InitConst;
import com.jfinal.plugin.IPlugin;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Protocol;

import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

@SuppressWarnings("UnusedDeclaration")
public class JedisPlugin implements IPlugin {

    public static final String DEFAULT_HOST = StringPool.LOCAL_HOST;
    public static final int    DEFAULT_PORT = Protocol.DEFAULT_PORT;
    private final String host;
    private final int    port;
    private final int    timeout;
    public JedisPool pool;
    private String password;
    private int     maxidle                        = GenericObjectPoolConfig.DEFAULT_MAX_IDLE;
    private int     maxTotal                       = GenericObjectPoolConfig.DEFAULT_MAX_TOTAL;
    private long    minevictableidletimemillis     = GenericObjectPoolConfig.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
    private int     minidle                        = GenericObjectPoolConfig.DEFAULT_MIN_IDLE;
    private int     numtestsperevictionrun         = GenericObjectPoolConfig.DEFAULT_NUM_TESTS_PER_EVICTION_RUN;
    private long    softminevictableidletimemillis = GenericObjectPoolConfig.DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
    private long    timebetweenevictionrunsmillis  = GenericObjectPoolConfig.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;
    private boolean testwhileidle                  = GenericObjectPoolConfig.DEFAULT_TEST_WHILE_IDLE;
    private boolean testonreturn                   = GenericObjectPoolConfig.DEFAULT_TEST_ON_RETURN;
    private boolean testonborrow                   = GenericObjectPoolConfig.DEFAULT_TEST_ON_BORROW;

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
        final Properties properties = GojaConfig.getRedisConfig();
        if (properties != null && !properties.isEmpty()) {

            Set<Entry<Object, Object>> entrySet = properties.entrySet();
            for (Entry<Object, Object> entry : entrySet) {
                parseSetting(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }
        JedisShardInfo shardInfo = new JedisShardInfo(host, port, timeout);
        if (StringUtils.isNotBlank(password)) {
            shardInfo.setPassword(password);
        }
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        setPoolConfig(poolConfig);
        pool = new JedisPool(poolConfig, shardInfo.getHost(), shardInfo.getPort(), shardInfo.getTimeout(),
                shardInfo.getPassword());
        JedisKit.init(pool);
        return true;
    }

    private void setPoolConfig(JedisPoolConfig poolConfig) {
        poolConfig.setMaxIdle(maxidle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMinEvictableIdleTimeMillis(minevictableidletimemillis);
        poolConfig.setMinIdle(minidle);
        poolConfig.setNumTestsPerEvictionRun(numtestsperevictionrun);
        poolConfig.setSoftMinEvictableIdleTimeMillis(softminevictableidletimemillis);
        poolConfig.setTimeBetweenEvictionRunsMillis(timebetweenevictionrunsmillis);
        poolConfig.setTestWhileIdle(testwhileidle);
        poolConfig.setTestOnReturn(testonreturn);
        poolConfig.setTestOnBorrow(testonborrow);
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

    private void parseSetting(String key, String value) {
        if (InitConst.REDIS_PASSWORD.equalsIgnoreCase(key)) {
            password = value;
        } else if (InitConst.REDIS_MAXTOTAL.equalsIgnoreCase(key)) {
            maxTotal = Integer.valueOf(value);
        } else if (InitConst.REDIS_MAXIDLE.equalsIgnoreCase(key)) {
            maxidle = Integer.valueOf(value);
        } else if (InitConst.REDIS_MINEVICTABLEIDLETIMEMILLIS.equalsIgnoreCase(key)) {
            minevictableidletimemillis = Long.valueOf(value);
        } else if (InitConst.REDIS_MINIDLE.equalsIgnoreCase(key)) {
            minidle = Integer.valueOf(value);
        } else if (InitConst.REDIS_NUMTESTSPEREVICTIONRUN.equalsIgnoreCase(key)) {
            numtestsperevictionrun = Integer.valueOf(value);
        } else if (InitConst.REDIS_SOFTMINEVICTABLEIDLETIMEMILLIS.equalsIgnoreCase(key)) {
            softminevictableidletimemillis = Long.valueOf(value);
        } else if (InitConst.REDIS_TIMEBETWEENEVICTIONRUNSMILLIS.equalsIgnoreCase(key)) {
            timebetweenevictionrunsmillis = Long.valueOf(value);
        } else if (InitConst.REDIS_TESTWHILEIDLE.equalsIgnoreCase(key)) {
            testwhileidle = Boolean.getBoolean(value);
        } else if (InitConst.REDIS_TESTONRETURN.equalsIgnoreCase(key)) {
            testonreturn = Boolean.getBoolean(value);
        } else if (InitConst.REDIS_TESTONBORROW.equalsIgnoreCase(key)) {
            testonborrow = Boolean.getBoolean(value);
        }
    }
}
