/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.kits.io;

import com.google.common.base.Optional;

import java.io.File;
import java.net.URL;

/**
 * <p>
 * The jar file kit..
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-11-08 17:37
 * @since JDK 1.6
 */
public class JarKit {
    private final Class<?> klass;

    public JarKit(Class<?> klass) {
        this.klass = klass;
    }

    public Optional<String> getVersion() {
        final Package pkg = klass.getPackage();
        if (pkg == null) {
            return Optional.absent();
        }
        return Optional.fromNullable(pkg.getImplementationVersion());
    }

    @Override
    public String toString() {
        final URL location = klass.getProtectionDomain().getCodeSource().getLocation();
        try {
            final String jar = new File(location.toURI()).getName();
            if (jar.endsWith(".jar")) {
                return jar;
            }
            return "project.jar";
        } catch (Exception ignored) {
            return "project.jar";
        }
    }
}
