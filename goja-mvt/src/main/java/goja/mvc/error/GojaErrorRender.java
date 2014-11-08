/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.mvc.error;

import com.google.common.base.Splitter;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.jfinal.core.Const;
import com.jfinal.core.JFinal;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;
import com.jfinal.render.RenderFactory;
import goja.Goja;
import goja.StringPool;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-11-08 20:54
 * @since JDK 1.6
 */
public class GojaErrorRender extends Render {

    protected static final String contentType = "text/html; charset=" + getEncoding();

    protected static final String version = "<center><a href='http://www.jfinal.com?f=ev'><b>Powered by JFinal " + Const.JFINAL_VERSION + "</b></a></center>";

    protected static final String html404 = "<html><head><title>404 Not Found</title></head><body bgcolor='white'><center><h1>404 Not Found</h1></center><hr>" + version + "</body></html>";
    protected static final String html500 = "<html><head><title>500 Internal Server Error</title></head><body bgcolor='white'><center><h1>500 Internal Server Error</h1></center><hr>" + version + "</body></html>";

    protected static final String html401    = "<html><head><title>401 Unauthorized</title></head><body bgcolor='white'><center><h1>401 Unauthorized</h1></center><hr>" + version + "</body></html>";
    protected static final String html403    = "<html><head><title>403 Forbidden</title></head><body bgcolor='white'><center><h1>403 Forbidden</h1></center><hr>" + version + "</body></html>";
    public static final    String GOJA_ERROR = "goja_error";

    protected int errorCode;

    protected final Map<Integer, String> dev_error_code;


    public GojaErrorRender(int errorCode, String view) {
        this.errorCode = errorCode;
        this.view = view;
        if (Goja.mode.isDev()) {
            dev_error_code = Maps.newHashMap();
            try {
                dev_error_code.put(SC_INTERNAL_SERVER_ERROR, IOUtils.toString(Resources.getResource("/META-INF/views/" + SC_INTERNAL_SERVER_ERROR + ".html").openStream()));
                dev_error_code.put(SC_NOT_FOUND, IOUtils.toString(Resources.getResource("/META-INF/views/" + SC_NOT_FOUND + ".html").openStream()));
            } catch (IOException e) {
                dev_error_code.put(SC_INTERNAL_SERVER_ERROR, html404);
                dev_error_code.put(SC_NOT_FOUND, html500);
            }
        } else {
            dev_error_code = null;
        }
    }

    public void render() {
        response.setStatus(getErrorCode());    // HttpServletResponse.SC_XXX_XXX

        // render with view
        String view = getView();
        if (view != null) {
            RenderFactory.me().getRender(view).setContext(request, response).render();
            return;
        }


        // render with html content
        PrintWriter writer = null;
        try {
            response.setContentType(contentType);
            writer = response.getWriter();
            writer.write(getErrorHtml());
            writer.flush();
        } catch (IOException e) {
            throw new RenderException(e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    public String getErrorHtml() {
        if (Goja.mode.isDev()) {
            int errorCode = getErrorCode();
            switch (errorCode) {
                case SC_INTERNAL_SERVER_ERROR:
                    Throwable te = (Throwable) request.getAttribute(GOJA_ERROR);
                    String error_msg = Throwables.getStackTraceAsString(te);
                    List<String> html_lines = Splitter.on(StringPool.NEWLINE).splitToList(error_msg);
                    StringBuilder erro_html = new StringBuilder();
                    erro_html.append("<h2>").append(html_lines.get(0)).append("</h2>");
                    for (int i = 1; i < html_lines.size(); i++) {
                        erro_html.append("<div class=\"line \"><pre>").append(html_lines.get(i)).append("</pre></div>");
                    }
                    return dev_error_code.get(SC_INTERNAL_SERVER_ERROR).replace("${{error}}", erro_html.toString());
                case SC_NOT_FOUND:
                    final List<String> allActionKeys = JFinal.me().getAllActionKeys();
                    StringBuilder routes = new StringBuilder();
                    for (String allActionKey : allActionKeys) {
                        routes.append("<li>").append(allActionKey).append("</li>");
                    }
                    return dev_error_code.get(SC_NOT_FOUND).replace("${{routes}}", routes);
                default:
                    return "<html><head><title>" + errorCode + " Error</title></head><body bgcolor='white'><center><h1>" + errorCode + " Error</h1></center><hr>" + version + "</body></html>";
            }

        } else {
            int errorCode = getErrorCode();
            if (errorCode == SC_NOT_FOUND)
                return html404;
            if (errorCode == SC_INTERNAL_SERVER_ERROR)
                return html500;
            if (errorCode == 401)
                return html401;
            if (errorCode == 403)
                return html403;
            return "<html><head><title>" + errorCode + " Error</title></head><body bgcolor='white'><center><h1>" + errorCode + " Error</h1></center><hr>" + version + "</body></html>";
        }


    }

    public int getErrorCode() {
        return errorCode;
    }
}