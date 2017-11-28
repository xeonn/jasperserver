package com.jaspersoft.jasperserver.core.util;

import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextListener;

public class TolerantRequestContextListener extends RequestContextListener {

	@Override
	public void requestInitialized(ServletRequestEvent requestEvent) {
		super.requestInitialized(
				new ServletRequestEvent(
						requestEvent.getServletContext(), 
						new TolerantRequest((HttpServletRequest)requestEvent.getServletRequest())
				));
	}

	@Override
	public void requestDestroyed(ServletRequestEvent requestEvent) {
		super.requestDestroyed(
				new ServletRequestEvent(
						requestEvent.getServletContext(), 
						new TolerantRequest((HttpServletRequest)requestEvent.getServletRequest())
				));
	}

}
