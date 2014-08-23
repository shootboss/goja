/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.app.mvc.datatables.core;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import goja.app.mvc.datatables.DTConstants;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * datatables插件的服务端响应VO.
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-01-15 14:32
 * @since JDK 1.6
 */
public final class DatatablesCriterias implements Serializable {
    private static final long serialVersionUID = 7101911815995931134L;

    private final String          search;
    private final int             displayStart;
    private final int             displaySize;
    private final List<ColumnDef> columnDefs;
    private final List<ColumnDef> sortingColumnDefs;
    private final int             internalCounter;

    private DatatablesCriterias(String search, int displayStart, int displaySize, List<ColumnDef> columnDefs,
                                List<ColumnDef> sortingColumnDefs, int internalCounter) {
        this.search = search;
        this.displayStart = displayStart;
        this.displaySize = displaySize;
        this.columnDefs = columnDefs;
        this.sortingColumnDefs = sortingColumnDefs;
        this.internalCounter = internalCounter;
    }


    public static DatatablesCriterias criteriasWithRequest(HttpServletRequest request) {
        if (request != null) {

            String sSearch = request.getParameter(DTConstants.DT_S_SEARCH);
            String sEcho = request.getParameter(DTConstants.DT_S_ECHO);
            String sDisplayStart = request.getParameter(DTConstants.DT_I_DISPLAY_START);
            String sDisplayLength = request.getParameter(DTConstants.DT_I_DISPLAY_LENGTH);
            String sColNumber = request.getParameter(DTConstants.DT_I_COLUMNS);
            String sSortingColNumber = request.getParameter(DTConstants.DT_I_SORTING_COLS);

            int iEcho = StringUtils.isNotBlank(sEcho) ? Ints.tryParse(sEcho) : -1;
            int iDisplayStart = StringUtils.isNotBlank(sDisplayStart) ? Ints.tryParse(sDisplayStart) : 1;
            int iDisplayLength = StringUtils.isNotBlank(sDisplayLength) ? Ints.tryParse(sDisplayLength) : 10;
            int colNumber = StringUtils.isNotBlank(sColNumber) ? Ints.tryParse(sColNumber) : -1;
            int sortingColNumber = StringUtils.isNotBlank(sSortingColNumber) ? Ints.tryParse(sSortingColNumber) : -1;

            iDisplayStart = (iDisplayStart == 0) ? 1 : iDisplayStart + 1;

            List<ColumnDef> columnDefs = Lists.newArrayList();
            List<ColumnDef> sortingColumnDefs = Lists.newArrayList();

            for (int i = 0; i < colNumber; i++) {

                ColumnDef columnDef = new ColumnDef();

                columnDef.setName(request.getParameter(DTConstants.DT_M_DATA_PROP + i));
                columnDef.setFilterable(Boolean.parseBoolean(request.getParameter(DTConstants.DT_B_SEARCHABLE + i)));
                columnDef.setSortable(Boolean.parseBoolean(request.getParameter(DTConstants.DT_B_SORTABLE + i)));
                columnDef.setSearch(request.getParameter(DTConstants.DT_S_COLUMN_SEARCH + i));

                columnDefs.add(columnDef);
            }

            // Sorted column management
            for (int i = 0; i < sortingColNumber; i++) {
                String sSortingCol = request.getParameter(DTConstants.DT_I_SORT_COL + i);
                Integer sortingCol = StringUtils.isNotBlank(sSortingCol) ? Integer.parseInt(sSortingCol) : -1;
                ColumnDef sortedColumnDef = columnDefs.get(sortingCol);

                String sortingColDirection = request.getParameter(DTConstants.DT_S_SORT_DIR + i);
                if (StringUtils.isNotBlank(sortingColDirection)) {
                    sortedColumnDef.setSortDirection(ColumnDef.SortDirection.valueOf(sortingColDirection.toUpperCase()));
                }

                sortingColumnDefs.add(sortedColumnDef);
            }

            return new DatatablesCriterias(sSearch, iDisplayStart, iDisplayLength, columnDefs, sortingColumnDefs, iEcho);
        } else {
            return null;
        }
    }

    /**
     * @return true if a column is being sorted, false otherwise.
     */
    public Boolean hasOneSortedColumn() {
        return !sortingColumnDefs.isEmpty();
    }

    /**
     * @return true if a column is being filtered, false otherwise.
     */
    public Boolean hasOneFilteredColumn() {
        for (ColumnDef columnDef : this.columnDefs) {
            if (StringUtils.isNotBlank(columnDef.getSearch())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return true if a column is filterable, false otherwise.
     */
    public Boolean hasOneFilterableColumn() {
        for (ColumnDef columnDef : this.columnDefs) {
            if (columnDef.isFilterable()) {
                return true;
            }
        }
        return false;
    }

    public String getSearch() {
        return search;
    }

    public int getDisplayStart() {
        return displayStart;
    }

    public int getDisplaySize() {
        return displaySize;
    }

    public List<ColumnDef> getColumnDefs() {
        return columnDefs;
    }

    public List<ColumnDef> getSortingColumnDefs() {
        return sortingColumnDefs;
    }

    public int getInternalCounter() {
        return internalCounter;
    }
}
