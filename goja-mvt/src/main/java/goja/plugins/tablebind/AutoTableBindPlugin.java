/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package goja.plugins.tablebind;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.IDataSourceProvider;
import goja.annotation.TableBind;
import goja.init.ctxbox.ClassBox;
import goja.init.ctxbox.ClassType;

import javax.sql.DataSource;
import java.util.List;

public class AutoTableBindPlugin extends ActiveRecordPlugin {

    private final INameStyle nameStyle;

    public AutoTableBindPlugin(String configName, IDataSourceProvider dataSourceProvider, INameStyle nameStyle) {
        super(configName, dataSourceProvider);
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
                    tableName = nameStyle.name(modelClass.getSimpleName());
                    this.addMapping(tableName, modelClass);
                } else {
                    tableName = tb.tableName();
                    if (StrKit.notBlank(tb.pkName())) {
                        this.addMapping(tableName, tb.pkName(), modelClass);
                    } else {
                        this.addMapping(tableName, modelClass);
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

}
