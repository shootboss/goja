package goja;

import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import com.jfinal.plugin.activerecord.DbKit;
import goja.kits.io.ResourceKit;
import goja.lang.Lang;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Properties;

/**
 * <p> 属性文件获取，并且当属性文件发生改变时，自动重新加载. <p/> 1. 可配置是否重启应用 </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-01-13 10:24
 * @since JDK 1.6
 */
public class GojaConfig {

    private static final Logger logger = LoggerFactory.getLogger(GojaConfig.class);

    public static final String REDIS_PREFIX = "redis";
    public static final String MONGO_PREFIX = "mongo";
    public static final String DB_PREFIX    = "db";

    private static final String APPLICATION_PROP = "application.conf";

    private static final ThreadLocal<Properties> configProps = new ThreadLocal<Properties>();

    private static final Properties APPLICATION_CONFIG = new Properties();
    private static final Properties MONGODB_CONFIG     = new Properties();
    private static final Properties REDIS_CONFIG       = new Properties();

    /**
     * The Database Config In application.conf.
     */
    private static final Map<String, Properties> DB_CONFIG = Maps.newHashMapWithExpectedSize(1);

    static {
        readConf();
    }

    /**
     * 重新加载配置文件
     */
    public static void reload() {
        configProps.remove();
        clear();
        readConf();
    }

    public static void clear() {
        DB_CONFIG.clear();
        REDIS_CONFIG.clear();
        MONGODB_CONFIG.clear();
    }

    /**
     * 读取配置文件
     */
    private static void readConf() {
        final Properties p = new Properties();
        ResourceKit.loadFileInProperties(APPLICATION_PROP, p);
        if (checkNullOrEmpty(p)) {
            throw new IllegalArgumentException("Properties file can not be empty. " + APPLICATION_PROP);
        }
        for (Object o : p.keySet()) {
            String _key = String.valueOf(o);
            Object value = p.get(o);
            if (Lang.isEmpty(value)) {
                continue;
            }
            if (StringUtils.startsWithIgnoreCase(_key, REDIS_PREFIX)) {
                REDIS_CONFIG.put(_key, value);
            } else if (StringUtils.startsWithIgnoreCase(_key, MONGO_PREFIX)) {
                MONGODB_CONFIG.put(_key, value);
            } else if (StringUtils.startsWithIgnoreCase(_key, DB_PREFIX)) {
                int last_idx = _key.lastIndexOf(StringPool.DOT);
                if (last_idx > 2) {
                    String config_name = _key.substring(_key.indexOf(StringPool.DOT) + 1, last_idx);
                    logger.debug("the db config is {}", config_name);
                    Properties db_config_props = DB_CONFIG.get(config_name);
                    if (db_config_props == null) {
                        db_config_props = new Properties();
                        DB_CONFIG.put(config_name, db_config_props);
                    }
                    _key = _key.replace(StringPool.DOT + config_name, StringPool.EMPTY);
                    db_config_props.put(_key, value);

                } else {
                    Properties db_main_props = DB_CONFIG.get(DbKit.MAIN_CONFIG_NAME);
                    if (db_main_props == null) {
                        db_main_props = new Properties();
                        DB_CONFIG.put(DbKit.MAIN_CONFIG_NAME, db_main_props);
                    }
                    db_main_props.put(_key, value);
                }
            } else {
                APPLICATION_CONFIG.put(_key, value);
            }
        }
        configProps.set(p);
    }

    /**
     * 如果属性文件为空或者没有内容，则返回true
     *
     * @param p 属性信息
     * @return 是否为空或者没有内容
     */
    private static boolean checkNullOrEmpty(Properties p) {
        return p == null || p.isEmpty();
    }

    /**
     * 获取系统的配置
     *
     * @return 系统配置信息
     */
    public static Properties getConfigProps() {
        return configProps.get();
    }


    public static Properties getRedisConfig() {
        return REDIS_CONFIG;
    }

    public static Properties getMongodbConfig() {
        return MONGODB_CONFIG;
    }

    public static Map<String, Properties> getDbConfig() {
        return DB_CONFIG;
    }

    public static Properties getApplicationConfig() {
        return APPLICATION_CONFIG;
    }


    public static String getProperty(String key) {
        final Properties _p = configProps.get();
        if (checkNullOrEmpty(_p)) {
            return StringPool.EMPTY;
        }
        return _p.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        final Properties _p = configProps.get();
        if (checkNullOrEmpty(_p)) {
            return defaultValue;
        }
        return _p.getProperty(key, defaultValue);
    }

    public static Integer getPropertyToInt(String key) {
        Integer resultInt = null;
        final Properties _p = configProps.get();
        if (checkNullOrEmpty(_p)) {
            return null;
        }
        String resultStr = _p.getProperty(key);
        if (resultStr != null)
            resultInt = Ints.tryParse(resultStr);
        return resultInt;
    }

    public static int getPropertyToInt(String key, int defaultValue) {
        Integer result = getPropertyToInt(key);
        return result != null ? result : defaultValue;
    }

    public static Boolean getPropertyToBoolean(String key) {
        final Properties _p = configProps.get();
        if (checkNullOrEmpty(_p)) {
            return null;
        }
        String resultStr = _p.getProperty(key);
        boolean resultBool = false;
        if (resultStr != null) {
            if (resultStr.trim().equalsIgnoreCase("true"))
                resultBool = true;
            else if (resultStr.trim().equalsIgnoreCase("false"))
                resultBool = false;
        }
        return resultBool;
    }

    public static boolean getPropertyToBoolean(String key, boolean defaultValue) {
        Boolean result = getPropertyToBoolean(key);
        return result != null ? result : defaultValue;
    }
}
