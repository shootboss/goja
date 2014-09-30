/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jfinal.plugin.activerecord.dialect;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.Table;

/**
 * OracleDialect.
 */
public class OracleDialect extends Dialect {
	
	public String forTableBuilderDoBuild(String tableName) {
		return "SELECT * FROM " + tableName + " WHERE rownum = 0";
	}
	
	// insert into table (id,name) values(seq.nextval, ？)
	public void forModelSave(Table table, Map<String, Object> attrs, StringBuilder sql, List<Object> paras) {
		sql.append("INSERT INTO ").append(table.getName()).append("(");
		StringBuilder temp = new StringBuilder(") VALUES(");
		String pKey = table.getPrimaryKey();
		int count = 0;
		for (Entry<String, Object> e: attrs.entrySet()) {
			String colName = e.getKey();
			if (table.hasColumnLabel(colName)) {
				if (count++ > 0) {
					sql.append(", ");
					temp.append(", ");
				}
				sql.append(colName);
				Object value = e.getValue();
				if(value instanceof String && colName.equalsIgnoreCase(pKey) && ((String)value).endsWith(".nextval")) {
				    temp.append(value);
				}else{
				    temp.append("?");
				    paras.add(value);
				}
			}
		}
		sql.append(temp.toString()).append(")");
	}
	
	public String forModelDeleteById(Table table) {
		String pKey = table.getPrimaryKey();
        return "DELETE FROM " + table.getName() + " WHERE " + pKey + " = ?";
	}
	
	public void forModelUpdate(Table table, Map<String, Object> attrs, Set<String> modifyFlag, String pKey, Object id, StringBuilder sql, List<Object> paras) {
		sql.append("UPDATE ").append(table.getName()).append(" SET ");
		for (Entry<String, Object> e : attrs.entrySet()) {
			String colName = e.getKey();
			if (!pKey.equalsIgnoreCase(colName) && modifyFlag.contains(colName) && table.hasColumnLabel(colName)) {
				if (paras.size() > 0)
					sql.append(", ");
				sql.append(colName).append(" = ? ");
				paras.add(e.getValue());
			}
		}
		sql.append(" WHERE ").append(pKey).append(" = ?");
		paras.add(id);
	}
	
	public String forModelFindById(Table table, String columns) {
		StringBuilder sql = new StringBuilder("SELECT ");
		if (columns.trim().equals("*")) {
			sql.append(columns);
		}
		else {
			String[] columnsArray = columns.split(",");
			for (int i=0; i<columnsArray.length; i++) {
				if (i > 0)
					sql.append(", ");
				sql.append(columnsArray[i].trim());
			}
		}
		sql.append(" FROM ");
		sql.append(table.getName());
		sql.append(" WHERE ").append(table.getPrimaryKey()).append(" = ?");
		return sql.toString();
	}
	
	public String forDbFindById(String tableName, String primaryKey, String columns) {
		StringBuilder sql = new StringBuilder("SELECT ");
		if (columns.trim().equals("*")) {
			sql.append(columns);
		}
		else {
			String[] columnsArray = columns.split(",");
			for (int i=0; i<columnsArray.length; i++) {
				if (i > 0)
					sql.append(", ");
				sql.append(columnsArray[i].trim());
			}
		}
		sql.append(" FROM ");
		sql.append(tableName.trim());
		sql.append(" WHERE ").append(primaryKey).append(" = ?");
		return sql.toString();
	}
	
	public String forDbDeleteById(String tableName, String primaryKey) {
        return "DELETE FROM " + tableName.trim() + " WHERE " + primaryKey + " = ?";
	}
	
	public void forDbSave(StringBuilder sql, List<Object> paras, String tableName, Record record) {
		sql.append("INSERT INTO ");
		sql.append(tableName.trim()).append("(");
		StringBuilder temp = new StringBuilder();
		temp.append(") VALUES(");
		
		int count = 0;
		for (Entry<String, Object> e: record.getColumns().entrySet()) {
			if (count++ > 0) {
				sql.append(", ");
				temp.append(", ");
			}
			sql.append(e.getKey());
			
			Object value = e.getValue();
			if(value instanceof String && (((String)value).endsWith(".nextval"))) {
			    temp.append(value);
			}else{
				temp.append("?");
				paras.add(value);
			}
		}
		sql.append(temp.toString()).append(")");
	}
	
	public void forDbUpdate(String tableName, String primaryKey, Object id, Record record, StringBuilder sql, List<Object> paras) {
		sql.append("UPDATE ").append(tableName.trim()).append(" SET ");
		for (Entry<String, Object> e: record.getColumns().entrySet()) {
			String colName = e.getKey();
			if (!primaryKey.equalsIgnoreCase(colName)) {
				if (paras.size() > 0) {
					sql.append(", ");
				}
				sql.append(colName).append(" = ? ");
				paras.add(e.getValue());
			}
		}
		sql.append(" WHERE ").append(primaryKey).append(" = ?");
		paras.add(id);
	}
	
	public void forPaginate(StringBuilder sql, int pageNumber, int pageSize, String select, String sqlExceptSelect) {
		int satrt = (pageNumber - 1) * pageSize + 1;
		int end = pageNumber * pageSize;
		sql.append("SELECT * FROM ( SELECT row_.*, rownum rownum_ FROM (  ");
		sql.append(select).append(" ").append(sqlExceptSelect);
		sql.append(" ) row_ WHERE rownum <= ").append(end).append(") table_alias");
		sql.append(" WHERE table_alias.rownum_ >= ").append(satrt);
	}
	
	public boolean isOracle() {
		return true;
	}
	
	public void fillStatement(PreparedStatement pst, List<Object> paras) throws SQLException {
		for (int i=0, size=paras.size(); i<size; i++) {
			Object value = paras.get(i);
			if (value instanceof java.sql.Date)
				pst.setDate(i + 1, (java.sql.Date)value);
			else
				pst.setObject(i + 1, value);
		}
	}
	
	public void fillStatement(PreparedStatement pst, Object... paras) throws SQLException {
		for (int i=0; i<paras.length; i++) {
			Object value = paras[i];
			if (value instanceof java.sql.Date)
				pst.setDate(i + 1, (java.sql.Date)value);
			else
				pst.setObject(i + 1, value);
		}
	}
	
	public String getDefaultPrimaryKey() {
		return "ID";
	}
}
