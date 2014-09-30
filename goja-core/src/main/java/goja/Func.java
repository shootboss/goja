/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja;

import goja.date.DateProvider;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-06-01 20:22
 * @since JDK 1.6
 */
public interface Func {

    Joiner COMMA_JOINER = Joiner.on(StringPool.COMMA).skipNulls();


    Splitter COMMA_SPLITTER = Splitter.on(StringPool.COMMA).trimResults().omitEmptyStrings();

    Joiner DOT_JOINER = Joiner.on(StringPool.DOT).skipNulls();


    Splitter DOT_SPLITTER = Splitter.on(StringPool.DOT).trimResults().omitEmptyStrings();


    Joiner DASH_JOINER = Joiner.on(StringPool.DASH).skipNulls();

    Splitter DASH_SPLITTER = Splitter.on(StringPool.DASH).trimResults().omitEmptyStrings();


    /**
     * 日期格式化方法
     * <pre>
     *     yyyy-MM-dd HH:mm
     * </pre>
     */
    SimpleDateFormat DATE_FORMAT_YYYY_MM_DD_HH_MM = new SimpleDateFormat(DateProvider.YYYY_MM_DD_HH_MM);

    /**
     * 日期格式化方法
     * <pre>
     *     yyyy-MM-dd HH:mm:ss
     * </pre>
     */
    SimpleDateFormat DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat(DateProvider.YYYY_MM_DD_HH_MM_SS);

    /**
     * Joda的日期格式化方法
     * <pre>
     *     yyyy-MM-dd HH:mm:ss
     * </pre>
     */
    DateTimeFormatter DATE_TIME_PATTERN_YYYY_MM_DD_HH_MM_SS = DateTimeFormat.forPattern(DateProvider.YYYY_MM_DD_HH_MM_SS);
    /**
     * Joda的日期格式化方法
     * <pre>
     *     yyyy-MM-dd HH:mm
     * </pre>
     */
    DateTimeFormatter DATE_TIME_PATTERN_YYYY_MM_DD_HH_MM    = DateTimeFormat.forPattern(DateProvider.YYYY_MM_DD_HH_MM);
    /**
     * Joda的日期格式化方法
     * <pre>
     *     yyyy-MM-dd
     * </pre>
     */
    DateTimeFormatter DATE_TIME_PATTERN_YYYY_MM_DD          = DateTimeFormat.forPattern(DateProvider.YYYY_MM_DD);

    /**
     * 默认的数据库主键
     */
    String TABLE_PK_COLUMN = StringPool.PK_COLUMN;


    // ---------------------------------------------------------------- array

    String[] EMPTY_ARRAY = new String[0];
}
