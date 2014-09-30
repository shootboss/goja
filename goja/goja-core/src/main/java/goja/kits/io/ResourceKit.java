/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package goja.kits.io;

import goja.StringPool;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.google.common.io.ByteSource;
import com.google.common.io.Resources;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

public class ResourceKit {
    public static Map<String, String> readProperties(String resourceName) {
        Properties properties = new Properties();
        URL resource = Resources.getResource(resourceName);
        try {
            properties.load(new InputStreamReader(resource.openStream(), StringPool.UTF_8));
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
        return Maps.fromProperties(properties);
    }

    public static void loadFileInProperties(String file_path, Properties properties) {
        URL url = Resources.getResource(file_path);
        if (url == null) {
            throw new IllegalArgumentException("Parameter of file can not be blank");
        }

        ByteSource byteSource = Resources.asByteSource(url);
        try {
            properties.load(byteSource.openStream());
        } catch (IOException e) {
            throw new IllegalArgumentException("Properties file can not be loading: " + url);
        }
    }

}
