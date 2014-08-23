/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package goja.app.plugin.tablebind;


import goja.app.StringPool;

public class ParamNameStyles {

    public static INameStyle module(String moduleName) {
        return new ModuleNameStyle(moduleName);
    }

    public static INameStyle lowerModule(String moduleName) {
        return new LowerModuleNameStyle(moduleName);
    }

    public static INameStyle upModule(String moduleName) {
        return new UpModuleNameStyle(moduleName);
    }

    public static INameStyle upUnderlineModule(String moduleName) {
        return new UpUnderlineModuleNameStyle(moduleName);
    }

    public static INameStyle lowerUnderlineModule(String moduleName) {
        return new LowerUnderlineModuleNameStyle(moduleName);
    }
}

class ModuleNameStyle implements INameStyle {
    private String moduleName;

    public ModuleNameStyle(String moduleName) {
        this.moduleName = moduleName;
    }

    public ModuleNameStyle() {
    }

    @Override
    public String name(String className) {
        return moduleName + className;
    }
}

class LowerModuleNameStyle implements INameStyle {
    private String moduleName;

    public LowerModuleNameStyle(String moduleName) {
        this.moduleName = moduleName;
    }

    public LowerModuleNameStyle() {
    }

    @Override
    public String name(String className) {
        return moduleName + className.toLowerCase();
    }
}

class UpModuleNameStyle implements INameStyle {
    private String moduleName;

    public UpModuleNameStyle(String moduleName) {
        this.moduleName = moduleName;
    }

    public UpModuleNameStyle() {
    }

    @Override
    public String name(String className) {
        return moduleName + className.toUpperCase();
    }
}

class UpUnderlineModuleNameStyle implements INameStyle {
    private String moduleName;

    public UpUnderlineModuleNameStyle(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    public String name(String className) {
        String tableName = "";
        for (int i = 0; i < className.length(); i++) {
            char ch = className.charAt(i);
            if (Character.isUpperCase(ch)) {
                tableName += StringPool.UNDERSCORE + ch;
            } else {
                tableName += Character.toUpperCase(ch);
            }
        }
        return moduleName + tableName;
    }

}

class LowerUnderlineModuleNameStyle implements INameStyle {
    private String moduleName;

    public LowerUnderlineModuleNameStyle(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    public String name(String className) {
        String tableName = "";
        for (int i = 0; i < className.length(); i++) {
            char ch = className.charAt(i);
            if (Character.isUpperCase(ch)) {
                tableName += StringPool.UNDERSCORE + Character.toLowerCase(ch);
            } else {
                tableName += ch;
            }
        }
        return moduleName + tableName;
    }
}
