/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.mvc.render;

import goja.kits.lang.PoiKit;
import com.google.common.base.Strings;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;
import goja.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * Excel file Render
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-02-12 21:05
 * @since JDK 1.6
 */
public class ExcelRender extends Render {
    private static final long serialVersionUID = -3568563627255175353L;
    private final static String CONTENT_TYPE = "application/msexcel;charset=" + getEncoding();
    private final List<?>  data;
    private       String[] headers;
    private String sheetName = "sheet1";
    private int cellWidth;
    private String[] columns  = new String[]{};
    private String   fileName = "file1.xls";
    private int headerRow;

    public ExcelRender(List<?> data) {
        this.data = data;
    }

    public static ExcelRender me(List<?> data) {
        return new ExcelRender(data);
    }

    private static String encodeChineseDownloadFileName(HttpServletRequest request, String filename) {
        String agent = request.getHeader("USER-AGENT");
        try {
            if (!Strings.isNullOrEmpty(agent) && agent.contains("MSIE")) {
                filename = URLEncoder.encode(filename, "utf-8");
            } else {
                filename = new String(filename.getBytes("utf-8"), "iso8859-1");
            }
        } catch (UnsupportedEncodingException ignored) {

        }
        return filename;
    }

    @Override
    public void render() {
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=" + encodeChineseDownloadFileName(request, fileName));
        response.setContentType(CONTENT_TYPE);
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            PoiKit.with(data).sheetName(sheetName).headerRow(headerRow).headers(headers).columns(columns)
                    .cellWidth(cellWidth).export().write(os);
        } catch (Exception e) {
            throw new RenderException(e);
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                Logger.error(e.getMessage(), e);
            }

        }
    }

    public ExcelRender headers(String... headers) {
        this.headers = headers;
        return this;
    }

    public ExcelRender headerRow(int headerRow) {
        this.headerRow = headerRow;
        return this;
    }

    public ExcelRender columns(String... columns) {
        this.columns = columns;
        return this;
    }

    public ExcelRender sheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    public ExcelRender cellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
        return this;
    }

    public ExcelRender fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

}
