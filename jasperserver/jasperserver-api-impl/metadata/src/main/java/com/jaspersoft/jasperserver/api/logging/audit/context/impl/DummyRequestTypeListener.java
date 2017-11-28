/*
 * Copyright (C) 2005 - 2011 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com.
 * Licensed under commercial Jaspersoft Subscription License Agreement
 */

package com.jaspersoft.jasperserver.api.logging.audit.context.impl;

import com.jaspersoft.jasperserver.api.logging.audit.context.RequestType;
import com.jaspersoft.jasperserver.api.logging.audit.context.RequestTypeListener;

public class DummyRequestTypeListener implements RequestTypeListener {

	public RequestType getRequestType() {
		return null;
	}

 public void setRequestType(RequestType requestType) {
 }

}

