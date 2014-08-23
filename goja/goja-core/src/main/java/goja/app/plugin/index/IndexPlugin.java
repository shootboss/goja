/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.app.plugin.index;

import com.jfinal.plugin.IPlugin;
import goja.init.ConfigProperties;
import org.slf4j.Logger;

import java.io.IOException;

import static goja.init.InitConst.INDEX_PATH;

/**
 * <p>
 * Lucene 索引库.
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-08-24 0:16
 * @since JDK 1.6
 */
public class IndexPlugin implements IPlugin {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(IndexHolder.class);

    @Override
    public boolean start() {
        try {
            IndexHolder.init(ConfigProperties.getProperty(INDEX_PATH));
        } catch (IOException e) {
            logger.error("the index plugin has error!", e);
            return false;
        }
        return false;
    }

    @Override
    public boolean stop() {
        return true;
    }
}
