/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package com.jfinal.ext.typeconverter.impl;


import com.jfinal.ext.typeconverter.TypeConversionException;
import com.jfinal.ext.typeconverter.TypeConverter;
import com.jfinal.kit.StringKit;

/**
 * Converts given object to <code>Float</code>.
 * Conversion rules:
 * <ul>
 * <li><code>null</code> value is returned as <code>null</code></li>
 * <li>object of destination type is simply casted</li>
 * <li>object is converted to string, trimmed, and then converted if possible.</li>
 * </ul>
 * Number string may start with plus and minus sign.
 */
public class FloatConverter implements TypeConverter<Float> {

    public Float convert(Object value) {
        if (value == null) {
            return null;
        }

        if (value.getClass() == Float.class) {
            return (Float) value;
        }
        if (value instanceof Number) {
            return Float.valueOf(((Number) value).floatValue());
        }

        try {
            String stringValue = value.toString().trim();
            if (StringKit.startsWithChar(stringValue, '+')) {
                stringValue = stringValue.substring(1);
            }
            return Float.valueOf(stringValue);
        } catch (NumberFormatException nfex) {
            throw new TypeConversionException(value, nfex);
        }
    }

}