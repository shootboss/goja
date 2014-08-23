/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package goja.app.plugin.tablebind;

import goja.app.StringPool;
import com.jfinal.kit.StrKit;

public class SimpleNameStyles {
    public static final INameStyle DEFAULT = new INameStyle() {
        @Override
        public String name(String className) {
            return className;
        }
    };

    public static final INameStyle FIRST_LOWER = new INameStyle() {
        @Override
        public String name(String className) {
            return StrKit.firstCharToLowerCase(className);
        }
    };
    public static final INameStyle UP          = new INameStyle() {
        @Override
        public String name(String className) {
            return className.toUpperCase();
        }
    };
    public static final INameStyle LOWER       = new INameStyle() {
        @Override
        public String name(String className) {
            return className.toLowerCase();
        }
    };

    public static final INameStyle UP_UNDERLINE    = new INameStyle() {
        @Override
        public String name(String className) {
            String tableName = "";
            for (int i = 0; i < className.length(); i++) {
                char ch = className.charAt(i);
                if (i != 0 && Character.isUpperCase(ch)) {
                    tableName += StringPool.UNDERSCORE + ch;
                } else {
                    tableName += Character.toUpperCase(ch);
                }
            }
            return tableName;
        }
    };
    public static final INameStyle LOWER_UNDERLINE = new INameStyle() {
        @Override
        public String name(String className) {
            String tableName = "";
            for (int i = 0; i < className.length(); i++) {
                char ch = className.charAt(i);
                if (i == 0) {
                    tableName += Character.toLowerCase(ch);
                } else if (Character.isUpperCase(ch)) {
                    tableName += StringPool.UNDERSCORE + Character.toLowerCase(ch);
                } else {
                    tableName += ch;
                }
            }
            return tableName;
        }
    };
}
