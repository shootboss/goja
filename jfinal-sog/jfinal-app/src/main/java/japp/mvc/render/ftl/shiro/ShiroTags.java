/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package japp.mvc.render.ftl.shiro;

import freemarker.template.SimpleHash;
import japp.mvc.render.ftl.shiro.auth.AuthenticatedTag;
import japp.mvc.render.ftl.shiro.auth.GuestTag;
import japp.mvc.render.ftl.shiro.auth.NotAuthenticatedTag;
import japp.mvc.render.ftl.shiro.auth.PrincipalTag;
import japp.mvc.render.ftl.shiro.auth.UserTag;
import japp.mvc.render.ftl.shiro.permission.HasPermissionTag;
import japp.mvc.render.ftl.shiro.permission.LacksPermissionTag;
import japp.mvc.render.ftl.shiro.role.HasAnyRolesTag;
import japp.mvc.render.ftl.shiro.role.HasRoleTag;
import japp.mvc.render.ftl.shiro.role.LacksRoleTag;

/**
 * <p>
 * Shirio权限验证 Freemarker 标签.
 * </p>
 *
 * @author poplar.yfyang
 * @version 1.0 2012-10-27 10:42 AM
 * @since JDK 1.5
 */
public class ShiroTags extends SimpleHash {

    /**
     * Constructs an empty hash that uses the default wrapper set in
     * {@link freemarker.template.WrappingTemplateModel#setDefaultObjectWrapper(freemarker.template.ObjectWrapper)}.
     */
    public ShiroTags() {
        put("authenticated", new AuthenticatedTag());
        put("guest", new GuestTag());
        put("hasAnyRoles", new HasAnyRolesTag());
        put("hasPermission", new HasPermissionTag());
        put("hasRole", new HasRoleTag());
        put("lacksPermission", new LacksPermissionTag());
        put("lacksRole", new LacksRoleTag());
        put("notAuthenticated", new NotAuthenticatedTag());
        put("principal", new PrincipalTag());
        put("user", new UserTag());
    }
}
