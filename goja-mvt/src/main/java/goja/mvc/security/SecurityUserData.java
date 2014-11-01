/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.mvc.security;

import com.jfinal.plugin.activerecord.Model;
import goja.mvc.security.shiro.AppUser;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-11-01 17:39
 * @since JDK 1.6
 */
public interface SecurityUserData<U extends Model, L extends Model> {


    SecurityUser.Auth auth(AppUser<U, L> principal);

    SecurityUser.LogerUser<U, L> user(String loginName);
}
