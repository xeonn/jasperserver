/*
 * Copyright (C) 2005 - 2011 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.jasperserver.war.common;

import org.apache.commons.httpclient.methods.PostMethod;


/**
 * @author Lucian Chirita
 * @version $Id: HeartbeatCustomDSInfo.java 22650 2012-03-20 08:38:08Z lchirita $
 */
public class HeartbeatCustomDSInfo extends HeartbeatInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String serviceClass = null;

	public String getServiceClass() {
		return serviceClass;
	}

	public void setServiceClass(String serviceClass) {
		this.serviceClass = serviceClass;
	}

	public void contributeToHttpCall(PostMethod post) {
		post.addParameter("customDSClass[]", getServiceClass() == null ? "" : getServiceClass());
		post.addParameter("customDSCount[]", String.valueOf(getCount()));
	}

	public String getKey() {
		return getServiceClass();
	}

}
