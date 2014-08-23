/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package goja.plugin.monogodb;

import com.github.sog.config.StringPool;
import com.jfinal.plugin.IPlugin;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

public class MongodbPlugin implements IPlugin {

    public static final String DEFAULT_HOST = StringPool.LOCAL_HOST;
    public static final int    DEFAUL_PORT  = 27017;
    public static final String DEFAULT_PKGS = "app.entitys";

    private final String      host;
    private final int         port;
    private final String      database;
    private final String morphia_pkgs;
    /**
     * 是否启用 morphia
     */
    private final boolean morphia;
    private       MongoClient client;

    /**
     * 初始化MongoDB插件
     *
     * @param database 数据库名称
     */
    public MongodbPlugin(String database) {
        this(database, false);
    }

    /**
     * 初始化MongoDB插件，并确定是否启用Morphia功能
     *
     * @param database     数据库
     * @param morphia_pkgs morphia package name.
     */
    public MongodbPlugin(String database, String morphia_pkgs) {
        this(DEFAULT_HOST, DEFAUL_PORT, database, true, morphia_pkgs);
    }


    /**
     * 初始化MongoDB插件，并确定是否启用Morphia功能
     *
     * @param database 数据库
     * @param morphia  true，启用Morphia，false，不启用
     */
    public MongodbPlugin(String database, boolean morphia) {
        this(DEFAULT_HOST, DEFAUL_PORT, database, morphia, DEFAULT_PKGS);
    }

    /**
     * 给定地址、端口、数据库名称以及是否启动Morphia参数构建MongoDB插件
     *
     * @param host     mongod地址
     * @param port     mongodb端口
     * @param database mongodb数据库
     * @param morphia  是否启用morphia
     */
    public MongodbPlugin(String host, int port, String database, boolean morphia, String morphia_pkgs) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.morphia = morphia;
        this.morphia_pkgs = morphia_pkgs;
    }

    @Override
    public boolean start() {

        try {
            client = new MongoClient(host, port);
        } catch (UnknownHostException e) {
            throw new RuntimeException("can't connect mongodb, please check the host and port:" + host + StringPool.COMMA + port, e);
        }

        MongoKit.init(client, database);
        if (morphia) {
            MorphiaKit.create(client, database, morphia_pkgs);
        }
        return true;
    }

    @Override
    public boolean stop() {
        if (client != null) {
            client.close();
        }
        return true;
    }

}
