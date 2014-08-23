/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.mvc.security;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Objects;

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
public class AppUser implements Serializable {
    private static final long serialVersionUID = -4452393798317565037L;
    /** The Id. */
    public final long    id;
    /** The Name. */
    public final String  name;
    /** The nick name. */
    public final String  nickName;
    /** The Email. */
    public final String  email;
    /** The Avator. */
    public final String  avator;
    /**
     * The User Type
     */
    public final boolean type;

    /**
     * Instantiates a new Shiro user.
     *
     * @param id       the id
     * @param name     the name
     * @param email    the email
     * @param nickName the nick name
     * @param avator   the avator
     */
    @JSONCreator
    public AppUser(@JSONField(name = "id") long id
            , @JSONField(name = "name") String name
            , @JSONField(name = "email") String email
            , @JSONField(name = "nickName") String nickName
            , @JSONField(name = "avator") String avator) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.nickName = nickName;
        this.avator = avator;
        this.type = false;
    }

    /**
     * Instantiates a new Shiro user.
     *
     * @param id       the id
     * @param name     the name
     * @param email    the email
     * @param nickName the nick name
     * @param avator   the avator
     */
    @JSONCreator
    public AppUser(@JSONField(name = "id") long id
            , @JSONField(name = "name") String name
            , @JSONField(name = "email") String email
            , @JSONField(name = "nickName") String nickName
            , @JSONField(name = "avator") String avator
            , @JSONField(name = "type") boolean type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.nickName = nickName;
        this.avator = avator;
        this.type = type;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
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
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets avator.
     *
     * @return the avator
     */
    public String getAvator() {
        return avator;
    }

    /** 本函数输出将作为默认的<shiro:principal/>输出. */
    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, nickName, email, avator);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AppUser other = (AppUser) obj;
        return Objects.equal(this.id, other.id) && Objects.equal(this.name, other.name) && Objects.equal(this.nickName, other.nickName) && Objects.equal(this.email, other.email) && Objects.equal(this.avator, other.avator);
    }
}
