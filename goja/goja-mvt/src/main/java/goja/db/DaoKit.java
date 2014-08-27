/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.db;

import com.google.common.base.Strings;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import goja.Func;
import goja.StringPool;
import goja.mvc.datatables.core.ColumnDef;
import goja.mvc.datatables.core.DatatablesCriterias;
import goja.mvc.dtos.PageDto;
import goja.plugin.sqlinxml.SqlKit;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger             = LoggerFactory.getLogger(DaoKit.class);
    public static final  String SQL_PIRFIX_WHERE   = ".where";
    public static final  String SQL_PIRFIX_COLUMNS = ".column";
    public static final  String SQL_PIRFIX_ORDERS  = ".order";

    /**
     * 根据默认主键<code>id</code>和实体判断是否为新构成的实体。
     *
     * @param m   实体Model
     * @param <M> 泛型实体参数
     * @return 如果为新构成的则返回true
     */
    public static <M extends Model> boolean isNew(M m) {
        return isNew(m, Func.TABLE_PK_COLUMN);
    }


    /**
     * 根据主键和实体判断是否为新构成的实体。
     *
     * @param m         实体Model
     * @param pk_column 主键列
     * @param <M>       泛型实体参数
     * @return 如果为新构成的则返回true
     */
    public static <M extends Model> boolean isNew(M m, String pk_column) {
        final Number number = m.getNumber(pk_column);
        return number == null || number.intValue() <= 0;
    }

    /**
     * 拼接LIKE SQL 百分号
     *
     * @param value 数值
     * @return SQL LIKE表达形式
     */
    public static String like(String value) {
        return StringPool.PERCENT + Strings.nullToEmpty(value) + StringPool.PERCENT;
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
        return paginate(SqlKit.sql(model_name + SQL_PIRFIX_WHERE), SqlKit.sql(model_name + SQL_PIRFIX_COLUMNS), criterias, SqlKit.sql(model_name + SQL_PIRFIX_ORDERS), params);
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


    /**
     * 分页检索，默认按照id进行排序，需要指定datatables的请求参数。
     *
     * @param model_name sql conf 中的 sqlGroup 的name
     * @param pageDto    请求参数
     * @return 分页数据
     */
    public static Page<Record> paginate(String model_name, PageDto pageDto) {
        return paginate(SqlKit.sql(model_name + SQL_PIRFIX_WHERE), SqlKit.sql(model_name + SQL_PIRFIX_COLUMNS), SqlKit.sql(model_name + SQL_PIRFIX_ORDERS), pageDto);
    }


    /**
     * 分页检索，默认按照id进行排序，需要指定datatables的请求参数。
     *
     * @param where         FROM WHERE 语句.
     * @param sql_columns   SELECT column sql 语句
     * @param pageDto       请求参数\
     * @param default_order 默认的排序字段，类似：ORDER BY id DESC
     * @return 分页数据
     */
    public static Page<Record> paginate(String where, String sql_columns, String default_order, PageDto pageDto) {
        where = Strings.nullToEmpty(where);
        int pageSize = pageDto.pageSize;
        int p = pageDto.page;
        int start = ((p - 1) * pageSize) + 1;
        final List<PageDto.ReqParam> params = pageDto.params;
        final List<Object> query_params = pageDto.query_params;
        if ((params == null || params.isEmpty()) && (query_params == null || query_params.isEmpty())) {
            return Db.paginate(start, pageSize, sql_columns, where);
        } else {
            if (!StringUtils.containsIgnoreCase(where, "WHERE")) {
                where = where + " WHERE 1=1 ";
            }
            for (PageDto.ReqParam param : pageDto.params) {
                where += param.toSql();
            }


            return Db.paginate(start, pageSize, sql_columns, where + default_order, query_params.toArray());
        }
    }
}
