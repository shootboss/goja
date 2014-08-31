/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package goja.plugins.sqlinxml;

import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import goja.Goja;
import goja.Logger;
import goja.StringPool;
import goja.kits.map.JaxbKit;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SqlKit {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SqlKit.class);

    private static final Map<String, String> SQL_MAP = Maps.newHashMap();

    private static final String CONFIG_SUFFIX = "sql.xml";

    public static String sql(String groupNameAndsqlId) {

        return SQL_MAP.get(groupNameAndsqlId);
    }

    static void clearSqlMap() {
        SQL_MAP.clear();
    }

    static void putOver(String name, String value) {
        SQL_MAP.put(name, value);
    }


    static void init() {
        final URL resource = SqlKit.class.getClassLoader().getResource(StringPool.EMPTY);
        if (resource == null) {
            throw new NullPointerException("the resources is null.");
        }
        FluentIterable<File> iterable = Files.fileTreeTraverser().breadthFirstTraversal(new File(resource.getFile()));
        final List<File> files = Lists.newArrayList();
        for (File f : iterable) {
            if (f.getName().endsWith(CONFIG_SUFFIX)) {
                files.add(f);
            }
        }

        SqlGroup group;
        for (File xmlfile : files) {
            group = JaxbKit.unmarshal(xmlfile, SqlGroup.class);
            String name = group.name;
            if (StringUtils.isBlank(name)) {
                name = xmlfile.getName();
            }
            for (SqlItem sqlItem : group.sqlItems) {
                final String sql_name = name + StringPool.DOT + sqlItem.id;
                if (SQL_MAP.containsKey(sql_name)) {
                    logger.warn("In file {} SQL id in XML for {} has been loaded", xmlfile.getAbsolutePath(), sql_name);
                    continue;
                }
                final String _val = sqlItem.value;
                if(Strings.isNullOrEmpty(_val)){
                    logger.warn("In file {} SQL id in XML for {} is empty", xmlfile.getAbsolutePath(), sql_name);
                    continue;
                }
                SQL_MAP.put(sql_name, _val.replace('\r', ' ').replace('\n', ' ').replaceAll(" {2,}", " "));
            }
        }
        if (Logger.isDebugEnabled())
            Logger.debug("SQL_MAP" + SQL_MAP);
        if (Goja.mode.isDev()) {
            // 启动文件监控
            runWatch();
        }
    }

    private static void runWatch() {
        final URL resource = SqlKit.class.getClassLoader().getResource(StringPool.EMPTY);
        if (resource == null) {
            return;
        }
        // 轮询间隔 3 秒
        long interval = TimeUnit.SECONDS.toMillis(3);
        final String path = resource.getPath();

        File config_file = new File(path);
        List<FileAlterationObserver> observerList = Lists.newArrayList();
        final File[] childrenfiles = config_file.listFiles();
        if (childrenfiles != null) {
            for (File child : childrenfiles) {
                if (child.isDirectory()) {
                    final FileAlterationObserver observer = new FileAlterationObserver(
                            child.getAbsolutePath(),
                            FileFilterUtils.and(
                                    FileFilterUtils.fileFileFilter(),
                                    FileFilterUtils.suffixFileFilter(CONFIG_SUFFIX)),
                            null
                    );

                    observer.addListener(new SqlXmlFileListener(SQL_MAP));
                    observerList.add(observer);

                }
            }
        }

        final FileAlterationObserver[] observers = observerList.toArray(new FileAlterationObserver[observerList.size()]);
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observers);
        // 开始监控
        try {
            monitor.start();
        } catch (Exception e) {
            Logger.error("file monitor is error!", e);
        }

    }

    static void remove(String s) {
        SQL_MAP.remove(s);
    }
}
