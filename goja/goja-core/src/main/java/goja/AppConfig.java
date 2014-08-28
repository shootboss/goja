package goja;

import com.google.common.collect.Lists;
import goja.io.ResourceKit;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Properties;

/**
 * <p> 属性文件获取，并且当属性文件发生改变时，自动重新加载. <p/> 1. 可配置是否重启应用 </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-01-13 10:24
 * @since JDK 1.6
 */
public class AppConfig {

    public static final String REDIS_PREFIX = "redis";
    public static final String MONGO_PREFIX = "mongo";
    public static final String DB_PREFIX    = "db";

    private static final String APPLICATION_PROP = "application.conf";

    private static final ThreadLocal<Properties> configProps = new ThreadLocal<Properties>();

    private static final Properties APPLICATION_CONFIG = new Properties();
    private static final Properties DATABASE_CONFIG    = new Properties();
    private static final Properties MONGODB_CONFIG     = new Properties();
    private static final Properties REDIS_CONFIG       = new Properties();

    private static final List<String> DB_CONFIG_NAMES = Lists.newArrayListWithCapacity(3);

    static {
        readConf();
    }

    /**
     * 重新加载配置文件
     */
    public static void reload() {
        configProps.remove();
        readConf();
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
            if (StringUtils.startsWithIgnoreCase(_key, REDIS_PREFIX)) {
                REDIS_CONFIG.put(_key, value);
            } else if (StringUtils.startsWithIgnoreCase(_key, MONGO_PREFIX)) {
                MONGODB_CONFIG.put(_key, value);
            } else if (StringUtils.startsWithIgnoreCase(_key, DB_PREFIX)) {
                if (_key.endsWith("url")) {
                    String config_name = _key.substring(0, _key.indexOf(StringPool.DOT) - 1);
                    if (StringUtils.equalsIgnoreCase(config_name, "main")) {
                        p.put(_key.replace(config_name + StringPool.DOT, StringPool.EMPTY), value);
                    }
                    if (!DB_CONFIG_NAMES.contains(config_name)) {
                        DB_CONFIG_NAMES.add(config_name);
                    }
                }
                DATABASE_CONFIG.put(_key, value);
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

    public static Properties getDatabaseConfig() {
        return DATABASE_CONFIG;
    }

    public static List<String> dbConfigNames() {
        return DB_CONFIG_NAMES;
    }

    public static Properties getApplicationConfig() {
        return APPLICATION_CONFIG;
    }


    public static String getProperty(String key) {
        checkPropertyLoading();
        return configProps.get().getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        checkPropertyLoading();
        return configProps.get().getProperty(key, defaultValue);
    }

    public static Integer getPropertyToInt(String key) {
        checkPropertyLoading();
        Integer resultInt = null;
        String resultStr = configProps.get().getProperty(key);
        if (resultStr != null)
            resultInt = Integer.parseInt(resultStr);
        return resultInt;
    }

    public static int getPropertyToInt(String key, int defaultValue) {
        Integer result = getPropertyToInt(key);
        return result != null ? result : defaultValue;
    }

    public static boolean getPropertyToBoolean(String key) {
        checkPropertyLoading();
        String resultStr = configProps.get().getProperty(key);
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

    private static void checkPropertyLoading() {
        if (configProps.get() == null)
            throw new RuntimeException("You must load properties file by invoking loadPropertyFile(String) method in configConstant(Constants) method before.");
    }
}
