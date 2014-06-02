/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package japp.mvc.render;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.RenderException;
import freemarker.template.Template;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Map;

@SuppressWarnings("serial")
/**
 *  <http://my.oschina.net/alvinte/blog/69030>
 * @author alvinte
 *
 */
public class FreeMarkerXMLRender extends FreeMarkerRender {
    private static final String CONTENT_TYPE = "text/xml; charset=" + getEncoding();

    public FreeMarkerXMLRender(String view) {
        super(view);
    }

    @Override
    public void render() {
        response.setContentType(CONTENT_TYPE);

        Enumeration<String> attrs = request.getAttributeNames();
        Map<String, Object> root = Maps.newHashMap();
        while (attrs.hasMoreElements()) {
            String attrName = attrs.nextElement();
            root.put(attrName, request.getAttribute(attrName));
        }

        Writer writer = null;
        try {
            writer = response.getWriter();
            Template template = getConfiguration().getTemplate(view);
            template.process(root, writer); // Merge the data-model and the template
        } catch (Exception e) {
            throw new RenderException(e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                Throwables.propagate(e);
            }
        }
    }
}
