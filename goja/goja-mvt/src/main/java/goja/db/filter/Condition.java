/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.db.filter;


import goja.Func;
import goja.StringPool;

import java.util.List;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-04-02 23:06
 * @since JDK 1.6
 */
public class Condition {

    public static final String PARAM_CHAR = "?{";
    private String name;
    private Object value;
    private Operator operator;

    /**
     * Default constructor
     */
    public Condition() {
    }

    /**
     * @param name
     * @param operator
     * @param value
     */
    public Condition(String name, Operator operator, Object value) {
        this.name = name;
        this.value = value;
        this.operator = operator;
    }

    /**
     * @param name
     * @param value
     */
    public Condition(String name, Object value) {
        this(name, Operator.eq, value);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * @return the operator
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * @param operator the operator to set
     */
    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String constructQuery() {
        return operator.constructCondition(name, value);
    }

    public enum Operator {
        eq("="),
        ne("!="),
        le("<="),
        lt("<"),
        ge(">="),
        gt(">"),
        /**
         * betwwen and param is must be string[].
         */
        between(" between "),
        in(" in "),
        like(" like ");


        private final String operator;

        Operator(String operator) {
            this.operator = operator;
        }

        public String constructCondition(String name, Object value) {

            switch (this) {
                case between:
                    String[] param = (String[]) value;
                    return name + operator + PARAM_CHAR + param[0] + "} AND ?{" + param[1] + StringPool.RIGHT_BRACE;
                case like:
                    return name + operator + "?{%" + value + StringPool.RIGHT_BRACE;
                case in:
                    List<String> params = Func.COMMA_SPLITTER.splitToList(String.valueOf(value));
                    StringBuilder condition = new StringBuilder(name);
                    condition.append(operator).append(StringPool.LEFT_BRACKET);
                    if (params != null && !params.isEmpty()) {
                        final int last_idx = params.size() - 1;
                        for (int i = 0; i < last_idx; i++) {
                            String _param = params.get(i);
                            condition.append(PARAM_CHAR).append(_param).append(StringPool.RIGHT_BRACE)
                                    .append(StringPool.COMMA);
                        }
                        condition.append(PARAM_CHAR)
                                .append(params.get(last_idx))
                                .append(StringPool.RIGHT_BRACE);
                    }
                    condition.append(StringPool.RIGHT_BRACKET);
                    return condition.toString();
            }

            return name + operator + PARAM_CHAR + value + StringPool.RIGHT_BRACE;
        }
    }
}
