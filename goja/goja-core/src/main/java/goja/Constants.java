/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-02-22 14:54
 * @since JDK 1.6
 */
public class Constants {

    /**
     * 整数常量
     */
    public static final int INTEGER_NORMAL     = 0;
    /**
     * 负整数常量
     */
    public static final int INTEGER_NEGATIVE   = -1;
    /**
     * 非负整数常量
     */
    public static final int INTEGER_UNNEGATIVE = 10;
    /**
     * 正整数常量
     */
    public static final int INTEGER_POSITIVE   = 1;
    /**
     * 非正整数常量
     */
    public static final int INTEGER_UNPOSITIVE = -10;
    /**
     * 浮点数常量
     */
    public static final int FLOAT_NORMAL       = INTEGER_NORMAL;
    /**
     * 负浮点数常量
     */
    public static final int FLOAT_NEGATIVE     = INTEGER_NEGATIVE;
    /**
     * 非负浮点数常量
     */
    public static final int FLOAT_UNNEGATIVE   = INTEGER_UNNEGATIVE;
    /**
     * 正浮点数常量
     */
    public static final int FLOAT_POSITIVE     = INTEGER_POSITIVE;
    /**
     * 非正浮点数常量
     */
    public static final int FLOAT_UNPOSITIVE   = INTEGER_UNPOSITIVE;

    public static class ValidatorRegex {
        /**
         * 全中文验证
         */
        public static final String CONTAINCHINESE    = "[\\w\\W\\s]*([\u4E00-\u9FA5]|[\uFE30-\uFFA0])+[\\s\\W\\w]*";
        /**
         * 包含全角符号
         */
        public static final String FULLSHAPED        = "[^\\x00-\\xff]";
        /**
         * 含有空格
         */
        public static final String CONTAINSPACE      = "^.*[\\s]+.*$";
        /**
         * 含有英文
         */
        public static final String CONTAINENGLISH    = "^.*[a-zA-Z]+.*$";
        /**
         * 含有数字
         */
        public static final String CONTAININTEGER    = "^.*[0-9]+.*$";
        /**
         * 全为半角字符
         */
        public static final String FULLDBC           = "^[\\x00-\\xff]*$";
        /**
         * 含有半角字符
         */
        public static final String CONTAINDBC        = "[\\x00-\\xff]{1}";
        /**
         * 含有特殊字符
         */
        public static final String CONTAINSPECIAL    = "^.*[\\[|\\]|<|>|=|!|+|\\-|*|/|\\\\|%|_|(|)|'|\"|:|.|^|||,|;|$|&|~|#|{|}|?]+.*$";
        /**
         * 标准字符串
         */
        public static final String STANDARSTRING     = "^(?:[\\u4e00-\\u9fa5]*\\w*\\s*)+$";
        /**
         * 严格合法的网址
         */
        public static final String URLSTRICT         = "^(http://|https://)[^.].*\\.(\\w{3}|\\w{2}|\\w{4}|\\w{1})$";
        /**
         * 一般合法网址
         */
        public static final String URL               = "^(www.)[^.]\\w+\\.(\\w{3}|\\w{2}|\\w{4}|\\w{1})$";
        /**
         * 合法ip
         */
        public static final String IP                = "^((25[0-5]|2[0-4]\\d|1?\\d?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1?\\d?\\d)(:\\d+)?$";
        /**
         * 整数验证
         */
        public static final String INTEGER           = "(^[-+]?[1-9]\\d*$)|(^0$)";
        /**
         * 负整数
         */
        public static final String NEGATIVEINTEGER   = "^-[1-9]\\d*$";
        /**
         * 非正整数
         */
        public static final String UNPOSITIVEINTEGER = "(^-[1-9]\\d*$)|(^0$)";
        /**
         * 正整数
         */
        public static final String POSITIVEINTEGER   = "^[0-9]*[1-9][0-9]*$";
        /**
         * 非负整数
         */
        public static final String UNNEGATIVEINTEGER = "\\d+";
        /**
         * 浮点数
         */
        public static final String FLOAT             = "^[-+]?(0|([1-9]\\d*))\\.\\d+$";
        /**
         * 正浮点数
         */
        public static final String POSITIVEFLOAT     = "^\\+?(0|([1-9]\\d*))\\.\\d+$";
        /**
         * 负浮点数
         */
        public static final String NEGATIVEFLOAT     = "^-(0|([1-9]\\d*))\\.\\d+$";
        /**
         * 非负浮点数
         */
        public static final String UNNEGATIVEFLOAT   = "^\\+?(0|([1-9]\\d*))\\.\\d+$";
        /**
         * 非正浮点数
         */
        public static final String UNPOSITIVEFLOAT   = "^-(0|([1-9]\\d*))\\.\\d+$";
        /**
         * email规则
         */
        public static final String EMAIL             = "^[a-zA-Z0-9]+([_.]?[a-zA-Z0-9])*@([a-zA-Z0-9]+\\.)+[a-zA-Z0-9]{2,3}$";
        /**
         * 属性名称规则
         */
        public static final String ATTRIBUTENAME     = "^([a-zA-Z]+\\.?[a-zA-Z]+)+$";
        /**
         * 英文姓名
         */
        public static final String ENGLISHNAME       = "^[a-zA-Z\\s]+\\.?\\s?[a-zA-Z\\s]+$";
        /**
         * 身份证简单验证
         */
        public static final String PID               = "^([1-9]\\d{17,17}|[1-9]\\d{14,14}|[1-9]\\d{16,16}X)$";
        /**
         * 身份证严格验证
         */
        public static final String STANDARDPID       = "^([1-9][0-9]{5})(18|19|20)?(\\d{2})([01]\\d)([0123]\\d)(\\d{3})(\\d|X)?$";
        /**
         * 电话号码规则
         */
        public static final String TEL               = "^\\d(-?\\d)*$";
        /**
         * 手机号码规则
         */
        public static final String MOBILE            = "^\\+?\\d(-?\\d)*$";
        /**
         * 验证登录名
         */
        public static final String LOGINID           = "^[a-zA-Z][A-Za-z0-9_]+$";
        /**
         * 密码格式
         */
        public static final String PWFORMAT          = "^[\\x20-\\x7e]{6,18}$";
        /**
         * 全英文验证
         */
        public static final String ALLENGLISH        = "^[A-Za-z]+$";
        /**
         * 全中文验证
         */
        public static final String ALLCHINESE        = "^[\u4E00-\u9FA0]+$";
        /**
         * 全数字验证
         */
        public static final String ALLNUMBER         = "\\d+";
        /**
         * 邮编规则
         */
        public static final String POSTCODE          = "\\d{6}";
        /**
         * 区号验证
         */
        public static final String CITYCODE          = "\\d{3}|\\d{4}";
        /**
         * 验证qq
         */
        public static final String QQ                = "[1-9][0-9]{4,}";
        /**
         * 时间格式
         */
        public static final String TIME              = "([01][0-9]|[2][0-3])\\:([0-5][0-9])(\\:([0-5][0-9]))?";
        /**
         * 所有字母大写
         */
        public static final String ALLUPPERCASE      = "\\s*[A-Z][A-Z\\s]*";
        /**
         * 所有字母小写
         */
        public static final String ALLLOWERCASE      = "\\s*[a-z][a-z\\s]*";
        /**
         * 日期格式
         */
        public static final String DATE              = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";

    }
}
