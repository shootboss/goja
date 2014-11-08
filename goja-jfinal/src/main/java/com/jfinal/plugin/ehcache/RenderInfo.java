/**
 * Copyright (c) 2011-2015, James Zhan 詹波 (jfinal@126.com).
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

package com.jfinal.plugin.ehcache;

import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.JspRender;
import com.jfinal.render.Render;
import com.jfinal.render.XmlRender;

import java.io.Serializable;

import static com.jfinal.plugin.ehcache.RenderType.FREE_MARKER_RENDER;
import static com.jfinal.plugin.ehcache.RenderType.JSP_RENDER;
import static com.jfinal.plugin.ehcache.RenderType.XML_RENDER;

/**
 * RenderInfo.
 */
public class RenderInfo implements Serializable {

    private static final long serialVersionUID = -7299875545092102194L;

    private String  view;
    private Integer renderType;

    public RenderInfo(Render render) {
        if (render == null)
            throw new IllegalArgumentException("Render can not be null.");

        view = render.getView();
        /* # edit by sogyf. */
        /* @description: fixed xml render ok! and remove velocity */
        if (render instanceof XmlRender)
            renderType = FREE_MARKER_RENDER;
        else if (render instanceof FreeMarkerRender)
            renderType = XML_RENDER;
        else if (render instanceof JspRender)
            renderType = JSP_RENDER;
        //        else if (render instanceof VelocityRender)
        //            renderType = VELOCITY_RENDER;
        /* # end edited. */
        else
            throw new IllegalArgumentException("CacheInterceptor can not support the render of the type : " + render.getClass().getName());
    }

    public Render createRender() {
        /* # edit by sogyf. */
        /* @description: switch java code and remove velocity render. */
        switch (renderType) {
            case FREE_MARKER_RENDER:
                return new FreeMarkerRender(view);
            case JSP_RENDER:
                return new JspRender(view);
            case XML_RENDER:
                return new XmlRender(view);
            default:
                throw new IllegalArgumentException("CacheInterceptor can not support the renderType of the value : " + renderType);

        }
        /* # end edited. */
    }
}
