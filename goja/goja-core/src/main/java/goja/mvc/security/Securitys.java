/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.mvc.security;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * <p>
 * .
 * </p>
 *
 * @author walter yang
 * @version 1.0 2013-10-29 11:05 PM
 * @since JDK 1.5
 */
public class Securitys {
    /**
     * Get login member.
     *
     * @return the member usr
     */
    public static AppUser getLoginMember() {

        return (AppUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * Is login.
     *
     * @return the boolean
     */
    public static boolean isLogin() {
        Subject subject = SecurityUtils.getSubject();

        return subject.getPrincipal() != null && subject.isAuthenticated();
    }

    /**
     * Is permitted.
     *
     * @param permission the permission
     * @return the boolean
     */
    public static boolean isPermitted(String permission) {
        return SecurityUtils.getSubject().isPermitted(permission);
    }

    /**
     * Is role.
     *
     * @param role the role
     * @return the boolean
     */
    public static boolean isRole(String role) {
        return SecurityUtils.getSubject().hasRole(role);
    }
}
