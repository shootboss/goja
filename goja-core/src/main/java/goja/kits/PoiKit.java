/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.kits;

import com.google.common.base.Preconditions;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-02-12 21:47
 * @since JDK 1.6
 */
public class PoiKit {


    private static final int HEADER_ROW = 1;
    private static final int MAX_ROWS   = 65536;

    private final List<?> data;

    private String sheetName = "new sheet";
    private int    cellWidth = 8000;
    private int headerRow;

    private String[] headers = new String[]{};
    private String[] columns;

    public PoiKit(List<?> data) {
        this.data = data;
    }

    public static PoiKit with(List<?> data) {
        return new PoiKit(data);
    }

    @SuppressWarnings("unchecked")
    private static void processAsMap(String[] columns, HSSFRow row, Object obj) {
        HSSFCell cell;
        Map<String, Object> map = (Map<String, Object>) obj;
        if (columns.length == 0) {// 未设置显示列，默认全部
            Set<String> keys = map.keySet();
            int columnIndex = 0;
            for (String key : keys) {
                cell = row.createCell(columnIndex);
                cell.setCellValue(map.get(key) + "");
                columnIndex++;
            }
        } else {
            for (int j = 0, lenJ = columns.length; j < lenJ; j++) {
                cell = row.createCell(j);
                cell.setCellValue(map.get(columns[j]) + "");
            }
        }
    }

    private static void processAsModel(String[] columns, HSSFRow row, Object obj) {
        HSSFCell cell;
        Model<?> model = (Model<?>) obj;
        Set<Map.Entry<String, Object>> entries = model.getAttrsEntrySet();
        if (columns.length == 0) {// 未设置显示列，默认全部
            int columnIndex = 0;
            for (Map.Entry<String, Object> entry : entries) {
                cell = row.createCell(columnIndex);
                cell.setCellValue(entry.getValue() + "");
                columnIndex++;
            }
        } else {
            for (int j = 0, lenJ = columns.length; j < lenJ; j++) {
                cell = row.createCell(j);
                cell.setCellValue(model.get(columns[j]) + "");
            }
        }
    }

    private static void processAsRecord(String[] columns, HSSFRow row, Object obj) {
        HSSFCell cell;
        Record record = (Record) obj;
        Map<String, Object> map = record.getColumns();
        if (columns.length == 0) {// 未设置显示列，默认全部
            record.getColumns();
            Set<String> keys = map.keySet();
            int columnIndex = 0;
            for (String key : keys) {
                cell = row.createCell(columnIndex);
                cell.setCellValue(record.get(key) + "");
                columnIndex++;
            }
        } else {
            for (int j = 0, lenJ = columns.length; j < lenJ; j++) {
                cell = row.createCell(j);
                cell.setCellValue(map.get(columns[j]) + "");
            }
        }
    }

    public HSSFWorkbook export() {
        Preconditions.checkNotNull(headers, "headers can not be null");
        Preconditions.checkNotNull(columns, "columns can not be null");
        Preconditions.checkArgument(cellWidth >= 0, "cellWidth < 0");
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
        HSSFRow row;
        HSSFCell cell;
        if (headers.length > 0) {
            row = sheet.createRow(0);
            if (headerRow <= 0) {
                headerRow = HEADER_ROW;
            }
            headerRow = Math.min(headerRow, MAX_ROWS);
            for (int h = 0, lenH = headers.length; h < lenH; h++) {
                CellRangeAddress region = new CellRangeAddress(0, (short) h, (short) headerRow - 1, (short) h);// 合并从第rowFrom行columnFrom列
                sheet.addMergedRegion(region);// 到rowTo行columnTo的区域
                // 得到所有区域
                sheet.getNumMergedRegions();
                if (cellWidth > 0) {
                    sheet.setColumnWidth(h, cellWidth);
                }
                cell = row.createCell(h);
                cell.setCellValue(headers[h]);
            }
        }
        if (data.size() == 0) {
            return wb;
        }
        for (int i = 0, len = data.size(); i < len; i++) {
            row = sheet.createRow(i + headerRow);
            Object obj = data.get(i);
            if (obj == null) {
                continue;
            }
            if (obj instanceof Map) {
                processAsMap(columns, row, obj);
            } else if (obj instanceof Model) {
                processAsModel(columns, row, obj);
            } else if (obj instanceof Record) {
                processAsRecord(columns, row, obj);
            }
        }
        return wb;
    }

    public PoiKit sheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    public PoiKit cellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
        return this;
    }

    public PoiKit headerRow(int headerRow) {
        this.headerRow = headerRow;
        return this;
    }

    public PoiKit headers(String[] headers) {
        this.headers = headers;
        return this;
    }

    public PoiKit columns(String[] columns) {
        this.columns = columns;
        return this;
    }
}
