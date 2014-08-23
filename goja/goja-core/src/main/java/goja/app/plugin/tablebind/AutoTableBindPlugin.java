/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package goja.app.plugin.tablebind;

import goja.annotation.TableBind;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.IDataSourceProvider;
import goja.app.StringPool;
import goja.init.ctxbox.ClassBox;
import goja.init.ctxbox.ClassType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.List;

public class AutoTableBindPlugin extends ActiveRecordPlugin {

    private static final Logger log = LoggerFactory.getLogger(AutoTableBindPlugin.class);
    private final INameStyle nameStyle;
    private boolean autoScan = true;

    private String _config_name = DbKit.MAIN_CONFIG_NAME;

    public AutoTableBindPlugin(DataSource dataSource) {
        this(dataSource, SimpleNameStyles.DEFAULT);
    }

    public AutoTableBindPlugin(DataSource dataSource, INameStyle nameStyle) {
        super(dataSource);
        this.nameStyle = nameStyle;
    }

    public AutoTableBindPlugin(IDataSourceProvider dataSourceProvider) {
        this(dataSourceProvider, SimpleNameStyles.DEFAULT);
    }

    public AutoTableBindPlugin(IDataSourceProvider dataSourceProvider, INameStyle nameStyle) {
        super(dataSourceProvider);
        this.nameStyle = nameStyle;
    }

    public AutoTableBindPlugin(String configName, IDataSourceProvider dataSourceProvider, INameStyle nameStyle) {
        super(configName, dataSourceProvider);
        this._config_name = configName;
        this.nameStyle = nameStyle;
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public boolean start() {
        List<Class> modelClasses = ClassBox.getInstance().getClasses(ClassType.MODEL);
        if (modelClasses != null && !modelClasses.isEmpty()) {
            TableBind tb;
            for (Class modelClass : modelClasses) {
                tb = (TableBind) modelClass.getAnnotation(TableBind.class);
                String tableName;
                if (tb == null) {
                    if (!autoScan) {
                        continue;
                    }
                    tableName = nameStyle.name(modelClass.getSimpleName());
                    this.addMapping(tableName, modelClass);
                    log.debug("addMapping(" + tableName + ", " + modelClass.getName() + StringPool.RIGHT_BRACKET);
                } else {
                    tableName = tb.tableName();
                    String configName = tb.configName();
                    if (StringUtils.equalsIgnoreCase(this._config_name, configName)) {
                        if (StrKit.notBlank(tb.pkName())) {
                            this.addMapping(tableName, tb.pkName(), modelClass);
                            log.debug("addMapping(" + tableName + ", " + tb.pkName() + StringPool.COMMA + modelClass.getName() + StringPool.RIGHT_BRACKET);
                        } else {
                            this.addMapping(tableName, modelClass);
                            log.debug("addMapping(" + tableName + ", " + modelClass.getName() + StringPool.RIGHT_BRACKET);
                        }
                    }
                }
            }
        }
        return super.start();
    }

    @Override
    public boolean stop() {
        return super.stop();
    }

    public AutoTableBindPlugin autoScan(boolean autoScan) {
        this.autoScan = autoScan;
        return this;
    }
}
