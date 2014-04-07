/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 jfinal app. jfapp Group.
 */

package app.models;

import com.github.sog.annotation.TableBind;
import com.github.sog.plugin.sqlinxml.SqlKit;
import com.jfinal.plugin.activerecord.Model;

/**
 * <p>
 * The table {{tableName}} mapping model.
 * </p>
 */
@TableBind(tableName = "{{tableName}}")
public class {{model}} extends Model<{{model}}> {

    private static final long   serialVersionUID = 1L;
    /**
     * The public dao.
     */
    public static final {{model}} dao = new {{model}}();


    public List<{{model}}> list(){
    	return find(SqlKit.sql("{{lower_model}}.list"))
    }
}