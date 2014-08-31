/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.db.dialect;

import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.dialect.Dialect;
import goja.Goja;
import goja.StringPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * MysqlDialect.
 */
public class MysqlDialect extends Dialect {
    private static final Logger logger = LoggerFactory.getLogger(MysqlDialect.class);

    public String forTableBuilderDoBuild(String tableName) {
        return "SELECT * FROM `" + tableName + "` WHERE 1 = 2";
    }

    public void forModelSave(Table table, Map<String, Object> attrs, StringBuilder sql, List<Object> paras) {
        sql.append("insert into `").append(table.getName()).append("`(");
        StringBuilder temp = new StringBuilder(") values(");
        for (Entry<String, Object> e : attrs.entrySet()) {
            String colName = e.getKey();
            if (table.hasColumnLabel(colName)) {
                if (paras.size() > 0) {
                    sql.append(", ");
                    temp.append(", ");
                }
                sql.append("`").append(colName).append("`");
                temp.append("?");
				paras.add(e.getValue());
			}
		}
		sql.append(temp.toString()).append(")");
	}
	
	public String forModelDeleteById(Table table) {
		String primaryKey = table.getPrimaryKey();
        return "DELETE FROM `" + table.getName() + "` WHERE `" + primaryKey + "` = ?";
	}
	
	public void forModelUpdate(Table table, Map<String, Object> attrs, Set<String> modifyFlag, String primaryKey, Object id, StringBuilder sql, List<Object> paras) {
		sql.append("update `").append(table.getName()).append("` set ");
		for (Entry<String, Object> e : attrs.entrySet()) {
			String colName = e.getKey();
			if (!primaryKey.equalsIgnoreCase(colName) && modifyFlag.contains(colName) && table.hasColumnLabel(colName)) {
				if (paras.size() > 0)
					sql.append(", ");
				sql.append("`").append(colName).append("` = ? ");
				paras.add(e.getValue());
			}
		}
		sql.append(" where `").append(primaryKey).append("` = ?");	// .append(" limit 1");
		paras.add(id);
	}
	
	public String forModelFindById(Table table, String columns) {
		StringBuilder sql = new StringBuilder("SELECT ");
		if (columns.trim().equals(StringPool.ASTERISK)) {
			sql.append(Goja.mode.isDev() ? columns : table.getColumnSelectSql());
		}
		else {
			String[] columnsArray = columns.split(StringPool.COMMA);
			for (int i=0; i<columnsArray.length; i++) {
				if (i > 0)
					sql.append(", ");
				sql.append("`").append(columnsArray[i].trim()).append("`");
			}
		}
		sql.append(" FROM `");
		sql.append(table.getName());
		sql.append("` WHERE `").append(table.getPrimaryKey()).append("` = ?");
		return sql.toString();
	}
	
	public String forDbFindById(String tableName, String primaryKey, String columns) {
		StringBuilder sql = new StringBuilder("SELECT ");
		if (columns.trim().equals(StringPool.ASTERISK)) {
			sql.append(columns);
		}
		else {
			String[] columnsArray = columns.split(StringPool.COMMA);
			for (int i=0; i<columnsArray.length; i++) {
				if (i > 0)
					sql.append(", ");
				sql.append("`").append(columnsArray[i].trim()).append("`");
			}
		}
		sql.append(" FROM `");
		sql.append(tableName.trim());
		sql.append("` WHERE `").append(primaryKey).append("` = ?");
		return sql.toString();
	}
	
	public String forDbDeleteById(String tableName, String primaryKey) {
        return "DELETE FROM `" + tableName.trim() + "` WHERE `" + primaryKey + "` = ?";
	}
	
	public void forDbSave(StringBuilder sql, List<Object> paras, String tableName, Record record) {
		sql.append("INSERT INTO `");
		sql.append(tableName.trim()).append("`(");
		StringBuilder temp = new StringBuilder();
		temp.append(") VALUES (");
		
		for (Entry<String, Object> e: record.getColumns().entrySet()) {
			if (paras.size() > 0) {
				sql.append(", ");
				temp.append(", ");
			}
			sql.append("`").append(e.getKey()).append("`");
			temp.append("?");
			paras.add(e.getValue());
		}
		sql.append(temp.toString()).append(")");
	}
	
	public void forDbUpdate(String tableName, String primaryKey, Object id, Record record, StringBuilder sql, List<Object> paras) {
		sql.append("UPDATE `").append(tableName.trim()).append("` SET ");
		for (Entry<String, Object> e: record.getColumns().entrySet()) {
			String colName = e.getKey();
			if (!primaryKey.equalsIgnoreCase(colName)) {
				if (paras.size() > 0) {
					sql.append(", ");
				}
				sql.append("`").append(colName).append("` = ? ");
				paras.add(e.getValue());
			}
		}
		sql.append(" WHERE `").append(primaryKey).append("` = ?");	// .append(" limit 1");
		paras.add(id);
	}
	
	public void forPaginate(StringBuilder sql, int pageNumber, int pageSize, String select, String sqlExceptSelect) {
		int offset = pageSize * (pageNumber - 1);
		sql.append(select).append(" ");
		sql.append(sqlExceptSelect);
		sql.append(" LIMIT ").append(offset).append(", ").append(pageSize);	// limit can use one or two '?' to pass paras
	}

    public void fillStatement(PreparedStatement pst, List<Object> paras) throws SQLException {
        int size = paras.size();
        if (Goja.mode.isDev()) {
            logger.debug("The sql paramters : {}", size == 0 ? "Empty" : size);
            for (int i=0; i<size; i++) {
                final Object value = paras.get(i);
                pst.setObject(i + 1, value);
                logger.debug("The param index: {}, param type is {}, param value is {}", i, value.getClass().getSimpleName(), value);
            }
        } else {
            for (int i=0; i<size; i++) {
                pst.setObject(i + 1, paras.get(i));
            }
        }
    }

    public void fillStatement(PreparedStatement pst, Object... paras) throws SQLException {
        int size = paras.length;
        if (Goja.mode.isDev()) {
            logger.debug("The sql paramters : {}", size == 0 ? "Empty" : size);
            for (int i=0; i<size; i++) {
                final Object value = paras[i];
                pst.setObject(i + 1, value);
                logger.debug("The param index: {}, param type is {}, param value is {}", i, value.getClass().getSimpleName(), value);
            }
        } else {
            for (int i=0; i<size; i++) {
                pst.setObject(i + 1, paras[i]);
            }
        }
    }
}
