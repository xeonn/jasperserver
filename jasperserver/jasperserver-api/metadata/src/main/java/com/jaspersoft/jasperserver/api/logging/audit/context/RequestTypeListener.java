/*
 * Copyright (C) 2005 - 2011 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com.
 * Licensed under commercial Jaspersoft Subscription License Agreement
 */
package com.jaspersoft.jasperserver.api.logging.audit.context;

/**
 * @author Sergey Prilukin
 * @version $Id: RequestTypeListener.java 45722 2014-05-14 10:24:22Z sergey.prilukin $
 */
public interface RequestTypeListener {
	
	public RequestType getRequestType();
	
    public void setRequestType(RequestType requestType);
    
}
