/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package japp.db;

import com.github.sog.controller.datatables.core.ColumnDef;
import com.github.sog.controller.datatables.core.DatatablesCriterias;
import com.github.sog.plugin.sqlinxml.SqlKit;
import com.google.common.base.Strings;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import japp.JAppFunc;
import com.jfinal.plugin.activerecord.Model;
import japp.StringPool;

import java.util.List;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-06-02 11:46
 * @since JDK 1.6
 */
public class DaoKit {


    public static <M extends Model> boolean isNew(M m) {
        return isNew(m,JAppFunc.TABLE_PK_COLUMN);
    }


    public static <M extends Model> boolean isNew(M m, String pk_column) {
        return m.getNumber(pk_column) == null;
    }


    /**
     * 分页检索，默认按照id进行排序，需要指定datatables的请求参数。
     *
     * @param model_name sql conf 中的 sqlGroup 的name
     * @param criterias  请求参数
     * @return 分页数据
     */
    public static Page<Record> paginate(String model_name, DatatablesCriterias criterias) {
        return paginate(model_name, criterias, null);
    }

    /**
     * 分页检索，默认按照id进行排序，需要指定datatables的请求参数。
     *
     * @param model_name sql conf 中的 sqlGroup 的name
     * @param criterias  请求参数
     * @return 分页数据
     */
    public static Page<Record> paginate(String model_name, DatatablesCriterias criterias, List<Object> params) {
        return paginate(SqlKit.sql(model_name + ".where"), SqlKit.sql(model_name + ".columns"), criterias, SqlKit.sql(model_name + ".orders"), params);
    }


    /**
     * 分页检索，默认按照id进行排序，需要指定datatables的请求参数。
     *
     * @param where         FROM WHERE 语句.
     * @param sql_columns   SELECT column sql 语句
     * @param criterias     请求参数
     * @param default_order 默认的排序字段，类似：ORDER BY id DESC
     * @return 分页数据
     */
    public static Page<Record> paginate(String where, String sql_columns, DatatablesCriterias criterias, String default_order, List<Object> params) {
        int pageSize = criterias.getDisplaySize();
        int start = criterias.getDisplayStart() / pageSize + 1;
        final List<ColumnDef> sortingColumnDefs = criterias.getSortingColumnDefs();
        if (sortingColumnDefs != null && !sortingColumnDefs.isEmpty()) {
            StringBuilder orderBy = new StringBuilder();
            for (ColumnDef sortingColumnDef : sortingColumnDefs)
                if (sortingColumnDef.isSortable()) {
                    orderBy.append(sortingColumnDef.getName()).append(StringPool.SPACE).append(sortingColumnDef.getSortDirection().toString());
                }
            final String byColumns = orderBy.toString();
            if (!Strings.isNullOrEmpty(byColumns)) {
                where += " ORDER BY " + byColumns;
            }
        }
        if (!where.contains("ORDER")) {
            where += (StringPool.SPACE + default_order);
        }
        if (params == null || params.size() == 0) {
            return Db.paginate(start, pageSize, sql_columns, where);
        } else {

            return Db.paginate(start, pageSize, sql_columns, where, params.toArray());
        }
    }
}
