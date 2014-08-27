/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.db.filter;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.TableMapping;
import goja.StringPool;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-04-02 23:06
 * @since JDK 1.6
 */
public class Filter<M extends Model> {

    private static final Pattern PARAM_PARSE = Pattern.compile("\\?\\{(.*?)\\}");
    private final int    pageNo;
    private final int    perPage;
    private final String tableName;
    private List<Condition> conditions = Lists.newArrayList();
    private boolean shouldPage;

    public Filter(int perPage, int pageNo, M model, Condition... conditions) {

        tableName = TableMapping.me().getTable(model.getClass()).getName();

        this.pageNo = pageNo > 0 ? pageNo : 1;
        this.shouldPage = perPage > 0;
        this.perPage = (perPage < 1) ? Integer.MAX_VALUE : perPage;
        if (conditions != null && conditions.length > 0) {
            this.conditions.addAll(Arrays.asList(conditions));
        }
    }

    public Filter(M model) {
        this(0, 0, model);
    }


    public Filter(M model, Condition... conditions) {
        this(0, 0, model, conditions);
    }

    public Integer getPageNo() {
        return pageNo;
    }


    public Integer getPerPage() {
        return perPage;
    }

    public Integer getStart() {
        return (pageNo - 1) * perPage;
    }

    public Integer getLimit() {
        return perPage;
    }

    public boolean shouldPage() {
        return shouldPage;
    }

    /**
     * @return the conditions
     */
    public List<Condition> getConditions() {
        return conditions;
    }

    /**
     * @param conditions the conditions to set
     */
    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public void addCondition(String name, Object value) {
        addCondition(name, Condition.Operator.eq, value);
    }

    public void addCondition(String name, Condition.Operator operator, Object value) {
        this.conditions.add(new Condition(name, operator, value));
    }


    public Record fetchFirst() {
        return fetchFirst(StringPool.EMPTY);
    }

    public Record fetchFirst(String selectColumns) {
        StringBuilder fetch_sql = new StringBuilder("SELECT ");
        if (Strings.isNullOrEmpty(selectColumns)) {
            fetch_sql.append("*");
        } else {
            fetch_sql.append(selectColumns);
        }
        fetch_sql.append(" FROM ").append(tableName);
        if (conditions != null && !conditions.isEmpty()) {
            fetch_sql.append(" WHERE ");

            final int last_idx = conditions.size() - 1;
            for (int i = 0; i < last_idx; i++) {
                fetch_sql.append(conditions.get(i).constructQuery()).append(" and ");
            }
            fetch_sql.append(conditions.get(last_idx).constructQuery());
        }
        String sql = fetch_sql.toString();
        // parse param.
        List<Object> params = Lists.newArrayList();
        Matcher matcher = PARAM_PARSE.matcher(sql);
        while (matcher.find()) {
            String val = StringUtils.trim(matcher.group(1));
            val = val.contains(StringPool.PERCENT) ? StringPool.PERCENT + val + StringPool.PERCENT : val;
            params.add(val);

            String replace_txt = matcher.group();
            sql = sql.replace(replace_txt, StringPool.EMPTY);
        }

        return Db.queryFirst(sql, params.toArray());
    }

    public List<Record> fetch() {
        return fetch(StringPool.EMPTY);
    }

    public List<Record> fetch(String selectColumns) {
        StringBuilder fetch_sql = new StringBuilder("SELECT ");
        if (Strings.isNullOrEmpty(selectColumns)) {
            fetch_sql.append("*");
        } else {
            fetch_sql.append(selectColumns);
        }
        fetch_sql.append(" FROM ").append(tableName);
        if (conditions != null && !conditions.isEmpty()) {
            fetch_sql.append(" WHERE ");
            final int last_idx = conditions.size() - 1;
            for (int i = 0; i < last_idx; i++) {
                fetch_sql.append(conditions.get(i).constructQuery()).append(" and ");
            }
            fetch_sql.append(conditions.get(last_idx).constructQuery());
        }
        String sql = fetch_sql.toString();
        // parse param.
        List<Object> params = Lists.newArrayList();
        Matcher matcher = PARAM_PARSE.matcher(sql);
        while (matcher.find()) {

            String val = StringUtils.trim(matcher.group(1));
            val = val.contains(StringPool.PERCENT) ? StringPool.PERCENT + val + StringPool.PERCENT : val;
            params.add(val);

            String replace_txt = matcher.group();
            sql = sql.replace(replace_txt, StringPool.QUESTION_MARK);
        }

        return Db.query(sql, params.toArray());
    }
}
