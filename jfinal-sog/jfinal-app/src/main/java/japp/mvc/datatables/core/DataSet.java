/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package japp.mvc.datatables.core;

import java.util.List;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-01-15 14:58
 * @since JDK 1.6
 */
public class DataSet<E> {
    private final List<E> rows;
    private final long    totalDisplayRecords;
    private final long    totalRecords;

    private DataSet(List<E> rows, long totalRecords, long totalDisplayRecords) {
        this.rows = rows;
        this.totalRecords = totalRecords;
        this.totalDisplayRecords = totalDisplayRecords;
    }

    public static <E> DataSet<E> newSet(List<E> rows, long total, long display) {
        return new DataSet<E>(rows, total, display);
    }


    public List<E> getRows() {
        return rows;
    }

    public long getTotalDisplayRecords() {
        return totalDisplayRecords;
    }

    public long getTotalRecords() {
        return totalRecords;
    }
}
