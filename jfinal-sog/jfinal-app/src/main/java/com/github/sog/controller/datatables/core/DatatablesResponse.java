/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package com.github.sog.controller.datatables.core;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * jQuery.Datatables 插件的响应实体.
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-01-15 14:38
 * @since JDK 1.6
 * @deprecated use {@link japp.mvc.datatables.core.DatatablesResponse}
 */
public final class DatatablesResponse<E> implements Serializable {
    private static final long serialVersionUID = 2150327528879897368L;

    private final List<E> aaData;
    private final long    iTotalRecords;
    private final long    iTotalDisplayRecords;
    private final int     sEcho;


    private DatatablesResponse(DataSet<E> dataSet, DatatablesCriterias criterias) {
        this.aaData = dataSet.getRows();
        this.iTotalRecords = dataSet.getTotalRecords();
        this.iTotalDisplayRecords = dataSet.getTotalDisplayRecords();
        this.sEcho = criterias.getInternalCounter();
    }

    public static <T> DatatablesResponse<T> build(DataSet<T> dataSet, DatatablesCriterias criterias) {
        return new DatatablesResponse<T>(dataSet, criterias);
    }

    public List<E> getAaData() {
        return aaData;
    }

    public long getiTotalRecords() {
        return iTotalRecords;
    }

    public long getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public int getsEcho() {
        return sEcho;
    }
}
