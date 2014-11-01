/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.mvc.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import goja.mvc.dtos.PageDto;

/**
 * <p>
 * 查询分页过滤器.
 * <p/>
 * usage:
 * <code>
 * <form>
 * <input type="text" name="s-username">
 * <input type="text" name="s-create-time-between">
 * <input type="text" name="s-create-time-and">
 * </form>
 * </code>
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-08-17 16:11
 * @since JDK 1.6
 */
public class FilterInterceptor implements Interceptor {
    private static final String SEARCH_PARAMS = "sp_url";
    public static final  String FILTER_PAGE   = "psf";


    public void intercept(ActionInvocation ai) {
        final Controller controller = ai.getController();
        final PageDto pageDto = PageDto.create(controller);
        controller.setAttr(FILTER_PAGE, pageDto);
        controller.setAttr(SEARCH_PARAMS, pageDto.getQueryUrl());
        ai.invoke();
    }


}
