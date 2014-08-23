/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.app.mvc.dtos;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import goja.app.StringPool;
import goja.app.db.DaoKit;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-08-17 16:11
 * @since JDK 1.6
 */
public class PageDto {

    public final int page;
    public final int pageSize;

    public final List<ReqParam> params = Lists.newArrayListWithCapacity(3);

    private final Map<String, Object> fq = Maps.newHashMap();

    public final List<Object> query_params = Lists.newArrayListWithCapacity(3);

    public PageDto(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public void put(String key, String value, String condition) {
        putTwoVal(key, value, StringPool.EMPTY, condition);
    }

    public void putTwoVal(String key, Object value, Object val2, String condition) {
        if (fq.containsKey(key)) {
            return;
        }
        final ReqParam reqParam = new ReqParam(key, condition);
        params.add(reqParam);
        switch (reqParam.condition) {
            case LIKE:
                query_params.add(DaoKit.like(String.valueOf(value)));
                break;
            case BETWEEN:
                query_params.add(value);
                query_params.add(val2);
                break;
            default:
                query_params.add(value);
                break;
        }
        fq.put(key, value);
        if (val2 != null) {
            fq.put(key + "2", val2);
        }
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<ReqParam> getParams() {
        return params;
    }

    public Map<String, Object> getFq() {
        return fq;
    }

    public List<Object> getQuery_params() {
        return query_params;
    }

    public static class ReqParam {
        public final String key;
        public final Condition condition;

        public ReqParam(String key, String condition) {
            this.key = key;
            this.condition = Condition.valueOf(condition);
        }

        public String toSql() {
            switch (condition) {
                case LIKE:
                    return String.format(" AND %s %s ? ", key, condition.condition);
                case BETWEEN:
                    return String.format(" AND %s BETWEEN ? AND ? ", key);
                default:
                    return String.format(" AND %s %s ? ", key, condition.condition);
            }
        }
    }

    public static enum Condition {
        LIKE(" LIKE "),
        EQ(" = "),
        LT(" < "),
        LTEQ(" <= "),
        GTEQ(" >= "),
        BETWEEN(" BETWEEN "),
        GT(" > ");

        public final String condition;

        Condition(String condition) {
            this.condition = condition;
        }


    }
}
