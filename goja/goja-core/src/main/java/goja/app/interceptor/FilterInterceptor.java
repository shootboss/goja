/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.app.interceptor;

import com.google.common.base.Strings;
import com.google.common.primitives.Ints;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import goja.app.StringPool;
import goja.app.mvc.dtos.PageDto;
import goja.init.ConfigProperties;
import org.apache.commons.lang3.StringUtils;

import java.util.Enumeration;

/**
 * <p>
 * 查询分页过滤器.
 * <p/>
 * usage:
 * <code>
 *              <form>
 *                  <input type="text" name="s-username">
 *                  <input type="text" name="s-create-time-between">
 *                  <input type="text" name="s-create-time-and">
 *              </form>
 *  </code>
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-08-17 16:11
 * @since JDK 1.6
 */
public class FilterInterceptor implements Interceptor {
    private static final String  SEARCH_PARAMS     = "sp_url";
    public static final  String  FILTER_PAGE       = "psf";
    public static final  Integer DEFAULT_PAGE_SIZE = Ints.tryParse(ConfigProperties.getProperty("app.page.size", "15"));

    public void intercept(ActionInvocation ai) {
        final Controller controller = ai.getController();
        final Enumeration<String> paraNames = controller.getParaNames();
        final int current_page = controller.getParaToInt("p", 1);
        final int page_size = controller.getParaToInt("s", DEFAULT_PAGE_SIZE);
        final PageDto pageDto = new PageDto(current_page, page_size);
        StringBuilder sb = new StringBuilder();

        while (paraNames.hasMoreElements()) {
            String p_key = paraNames.nextElement();
            if (!Strings.isNullOrEmpty(p_key) && StringUtils.startsWith(p_key, "s-")) {
                final String req_val = controller.getPara(p_key);
                if (!Strings.isNullOrEmpty(req_val)) {
                    String[] param_array = StringUtils.split(p_key, "-");
                    if (param_array.length >= 2) {

                        String name = param_array[1];
                        String condition = param_array.length == 2 ? PageDto.Condition.EQ.toString() : param_array[2];
                        condition = Strings.isNullOrEmpty(condition) ? PageDto.Condition.EQ.toString() : condition.toUpperCase();
                        if (StringUtils.equals(condition, PageDto.Condition.BETWEEN.toString())) {
                            String req_val2 = controller.getPara(StringUtils.replace(p_key, PageDto.Condition.BETWEEN.toString(), "AND"));
                            pageDto.putTwoVal(name, req_val, req_val2, condition);
//                            if(ValidatorKit.isDate(req_val)){
//                                pageDto.putTwoVal(name, DateTimeKit.parseYmd2LongSqlDate(req_val), DateTimeKit.parseYmd2LongSqlDate(req_val2), condition);
//                            }
                        } else {
                            pageDto.put(name, req_val, condition);
                        }
                        sb.append(StringPool.AMPERSAND).append(p_key).append(StringPool.EQUALS).append(req_val);

                    }
                }

            }
        }
        controller.setAttr(FILTER_PAGE, pageDto);
        controller.setAttr(SEARCH_PARAMS, sb.toString());
        ai.invoke();
    }


}
