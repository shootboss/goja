/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package com.jfinal.render;

import javax.servlet.http.HttpServletResponse;

/**
 * Redirect301Render.
 */
public class Redirect301Render extends Render {
	
	private static final long serialVersionUID = -115860447207423482L;
	private String url;
	private boolean withQueryString;
	private static final String contextPath = RedirectRender.getContxtPath();
	
	public Redirect301Render(String url) {
		this.url = url;
		this.withQueryString = false;
	}
	
	public Redirect301Render(String url, boolean withQueryString) {
		this.url = url;
		this.withQueryString = withQueryString;
	}
	
	public void render() {
		if (contextPath != null && !url.contains("://"))
			url = contextPath + url;
		
		if (withQueryString) {
			String queryString = request.getQueryString();
			if (queryString != null)
				url = url + "?" + queryString;
		}
		
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		// response.sendRedirect(url);	// always 302
		response.setHeader("Location", url);
		response.setHeader("Connection", "close");
	}
}
