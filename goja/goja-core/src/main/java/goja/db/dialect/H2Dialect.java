/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.db.dialect;

import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-03-27 0:54
 * @since JDK 1.6
 */
public class H2Dialect extends AnsiSqlDialect {
    public boolean isSupportReturning() {
        return true;
    }
}