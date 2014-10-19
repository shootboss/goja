/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.plugins.sqlinxml;

import goja.StringPool;
import goja.kits.map.JaxbKit;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-02-06 23:45
 * @since JDK 1.6
 */
public class SqlXmlFileListener extends FileAlterationListenerAdaptor {

    private static final Logger logger = LoggerFactory.getLogger(SqlXmlFileListener.class);
    final Map<String, String> sqlMap;

    public SqlXmlFileListener(Map<String, String> sqlMap) {
        this.sqlMap = sqlMap;
    }


    private void reload(File change_file) {
        SqlGroup group;
        if (change_file.isFile()) {
            group = JaxbKit.unmarshal(change_file, SqlGroup.class);
            String name = group.name;
            if (StringUtils.isBlank(name)) {
                name = change_file.getName();
            }
            for (SqlItem sqlItem : group.sqlItems) {
                SqlKit.putOver(name + StringPool.DOT + sqlItem.id, sqlItem.value);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("reload file:" + change_file.getAbsolutePath());
            }
        }
    }

    private void removeFile(File remove_file) {
        SqlGroup group;
        if (remove_file.isFile()) {
            group = JaxbKit.unmarshal(remove_file, SqlGroup.class);
            String name = group.name;
            if (StringUtils.isBlank(name)) {
                name = remove_file.getName();
            }
            for (SqlItem sqlItem : group.sqlItems) {
                SqlKit.remove(name + StringPool.DOT + sqlItem.id);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("delete file:" + remove_file.getAbsolutePath());
            }
        }
    }


    @Override
    public void onFileCreate(File file) {
        reload(file);
    }

    @Override
    public void onFileChange(File file) {
        reload(file);
    }

    @Override
    public void onFileDelete(File file) {
        removeFile(file);
    }
}
