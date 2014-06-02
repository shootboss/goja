/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package com.github.sog.controller.datatables.core;

import com.google.common.base.Objects;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-01-15 14:52
 * @since JDK 1.6
 * @deprecated use {@link japp.mvc.datatables.core.ColumnDef}
 */
public class ColumnDef {

    private String        name;
    private boolean       sortable;
    private boolean       sorted;
    private boolean       filterable;
    private boolean       filtered;
    private String        regex;
    private String        search;
    private SortDirection sortDirection;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public boolean isSorted() {
        return sorted;
    }

    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    public boolean isFilterable() {
        return filterable;
    }

    public void setFilterable(boolean filterable) {
        this.filterable = filterable;
    }

    public boolean isFiltered() {
        return filtered;
    }

    public void setFiltered(boolean filtered) {
        this.filtered = filtered;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("sortable", sortable)
                .add("sorted", sorted)
                .add("filterable", filterable)
                .add("filtered", filtered)
                .add("regex", regex)
                .add("search", search)
                .add("sortDirection", sortDirection)
                .toString();
    }

    public enum SortDirection {
        ASC, DESC
    }
}
