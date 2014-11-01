/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package app.controllers;

import app.models.User;
import com.google.common.base.Strings;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.Restful;
import com.jfinal.plugin.activerecord.Model;
import goja.Logger;
import goja.StringPool;
import goja.mvc.Controller;
import goja.mvc.security.shiro.AppUser;
import goja.mvc.security.shiro.Securitys;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-11-01 14:42
 * @since JDK 1.6
 */
@Before(Restful.class)
public class LoginController extends Controller {

    /**
     * The index route.
     */
    public void index() {
        if (Securitys.isLogin()) {
            redirect("/main");

        } else {
            int mask = getParaToInt("error", 0);
            String name = getPara("name", StringPool.EMPTY);
            setAttr("name", name);
            if (mask > 0) {
                setAttr("error", (mask == 2) ? "用户名或者密码错误!" : "请输入登录账号和登录密码");
            }
            render("/login.ftl");
        }
        render("/login.ftl");
    }


    public void save() {
        String password = getPara(FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM);
        String username = getPara(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password)) {
            redirect("/login?error=1&name=" + username);
            return;
        }
        boolean rememberMe = StringUtils.equals(getPara(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, "off"), "on");
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        final Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            Logger.error("用户名或者密码错误！", e);
            redirect("/login?error=2&name=" + username);
            return;
        }
        redirect("/main");
    }
}