/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package app.models;

import com.jfinal.plugin.activerecord.Model;
import goja.annotation.TableBind;

import static goja.plugins.sqlinxml.SqlKit.sql;

/**
 * <p>
 * The database users Controller.
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-11-01 17:20
 * @since JDK 1.6
 */
@TableBind(tableName = "users")
public class User extends Model<User> {

    /**
     * The public dao.
     */
    public static final User dao = new User();

    private static final long serialVersionUID = 7727695571894783324L;

    public User findByUsername(String username) {
        return findFirst(sql("user.findByUsername"), username);
    }
}