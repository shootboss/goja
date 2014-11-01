/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.mvc.security;

import com.jfinal.plugin.activerecord.Model;
import goja.mvc.security.shiro.AppUser;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-11-01 17:46
 * @since JDK 1.6
 */
public final class SecurityUser implements Serializable {
    private static final long serialVersionUID = 5593524165492186447L;

    public static class LogerUser<L extends Model, U extends Model> implements Serializable {
        private static final long serialVersionUID = 4562084055642717363L;
        private final AppUser<L, U> appUser;

        private final String password;

        private final String salt;

        public LogerUser(AppUser<L, U> appUser, String password, String salt) {
            this.appUser = appUser;
            this.password = password;
            this.salt = salt;
        }

        public AppUser<L, U> getAppUser() {
            return appUser;
        }

        public String getPassword() {
            return password;
        }

        public String getSalt() {
            return salt;
        }
    }


    public static class Auth {
        private final List<String> roles;

        private final List<String> permissions;

        public Auth(List<String> roles, List<String> permissions) {
            this.roles = roles;
            this.permissions = permissions;
        }

        public List<String> getRoles() {
            return roles;
        }

        public List<String> getPermissions() {
            return permissions;
        }
    }

}
