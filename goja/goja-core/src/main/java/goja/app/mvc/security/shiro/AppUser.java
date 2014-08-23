/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.app.mvc.security.shiro;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.jfinal.plugin.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * .
 * </p>
 *
 * @author walter yang
 * @version 1.0 2013-10-26 9:57 AM
 * @since JDK 1.5
 */
public class AppUser<L extends Model, U extends Model> implements Serializable {
    private static final long serialVersionUID = -4452393798317565037L;
    /**
     * The Id.
     */
    public final int    id;
    /**
     * The Name.
     */
    public final String name;
    /**
     * The nick name.
     */
    public final String nickName;
    /**
     * The User Type
     */
    public final int    type;

    /**
     * The user object.
     */
    public final U user;

    /**
     * the login object.
     */
    public final L login;

    /**
     * Instantiates a new Shiro user.
     *
     * @param id       the id
     * @param name     the name
     * @param nickName the nick name
     * @param user     the db model.
     */
    @JSONCreator
    public AppUser(@JSONField(name = "id") int id
            , @JSONField(name = "name") String name
            , @JSONField(name = "nickName") String nickName
            , @JSONField(name = "type") int type
            , @JSONField(name = "user") U user) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.user = user;
        this.type = type;
        this.login = null;
    }


    /**
     * Instantiates a new Shiro user.
     *
     * @param id       the id
     * @param name     the name
     * @param nickName the nick name
     * @param user     the db model.
     */
    @JSONCreator
    public AppUser(@JSONField(name = "id") int id
            , @JSONField(name = "name") String name
            , @JSONField(name = "nickName") String nickName
            , @JSONField(name = "type") int type
            , @JSONField(name = "user") U user
            , @JSONField(name = "login") L login) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.user = user;
        this.type = type;
        this.login = login;
    }


    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets nick name.
     *
     * @return the nick name
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    public U getUser() {
        return user;
    }

    public int getType() {
        return type;
    }

    public L getLogin() {
        return login;
    }

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppUser)) return false;

        AppUser appUser = (AppUser) o;

        if (id != appUser.id) return false;
        if (type != appUser.type) return false;
        if (login != null ? !login.equals(appUser.login) : appUser.login != null) return false;
        if (name != null ? !name.equals(appUser.name) : appUser.name != null) return false;
        if (nickName != null ? !nickName.equals(appUser.nickName) : appUser.nickName != null) return false;
        if (user != null ? !user.equals(appUser.user) : appUser.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + type;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        return result;
    }
}
