/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package app;

import app.models.User;
import com.jfinal.plugin.activerecord.Model;
import goja.mvc.security.SecurityUser;
import goja.mvc.security.SecurityUserData;
import goja.mvc.security.shiro.AppUser;

import java.util.Collections;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-11-01 18:08
 * @since JDK 1.6
 */
public class AppSecurityUserData implements SecurityUserData {
    @Override
    public SecurityUser.Auth auth(AppUser principal) {
        return new SecurityUser.Auth(Collections.EMPTY_LIST, Collections.EMPTY_LIST);
    }

    @Override
    public SecurityUser.LogerUser<User, Model> user(String loginName) {
        User user = User.dao.findByUsername(loginName);
        if (user == null) {
            return null;
        } else {
            AppUser<User, Model> shiroEmployee = new AppUser<User, Model>(
                    user.getInt("id"),
                    user.getStr("name"),
                    user.getStr("nickname"),
                    0, user, null);
            return new SecurityUser.LogerUser<User, Model>(shiroEmployee, user.getStr("password"), user.getStr("salt"));
        }
    }
}
