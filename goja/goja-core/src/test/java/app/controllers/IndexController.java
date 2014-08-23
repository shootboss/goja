/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package app.controllers;

import app.module.Task;
import com.jfinal.core.Controller;
import goja.Logger;
import goja.app.db.filter.Condition;
import goja.app.db.filter.Filter;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-02-24 11:00
 * @since JDK 1.6
 */
public class IndexController extends Controller {

    public void index() {
        Task.dao.findById(1);
        Logger.debug("The Index With Task by {}", 1);
        renderJson("abc");
    }


    public void filter() {
        Filter<Task> filter = new Filter<Task>(Task.dao);
//        filter.addCondition("title", Condition.Operator.eq, "Try SpringFuse");
        filter.addCondition("title", Condition.Operator.like, "Try");
//        filter.addCondition("user_id", Condition.Operator.gt, 1);
//        filter.addCondition("user_id", Condition.Operator.between, new String[]{"1","3"});
        filter.addCondition("id", Condition.Operator.in, "1,2,3");
        renderJson(filter.fetch("id, title"));
    }
}
