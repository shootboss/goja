/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package app.module;

import goja.annotation.TableBind;
import com.jfinal.plugin.activerecord.Model;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-02-24 17:18
 * @since JDK 1.6
 */
@TableBind(tableName = "ss_task")
public class Task extends Model<Task> {
    public static Task dao = new Task();
}