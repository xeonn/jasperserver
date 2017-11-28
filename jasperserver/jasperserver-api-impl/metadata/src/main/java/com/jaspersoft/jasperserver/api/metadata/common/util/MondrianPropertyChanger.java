/*
 * Copyright (C) 2006 JasperSoft http://www.jaspersoft.com
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed WITHOUT ANY WARRANTY; and without the 
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/gpl.txt 
 * or write to:
 * 
 * Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330,
 * Boston, MA  USA  02111-1307
 */

package com.jaspersoft.jasperserver.api.metadata.common.util;

import java.util.Iterator;
import java.util.Map;

import com.jaspersoft.jasperserver.api.common.properties.PropertyChangerAdapter;
import mondrian.olap.MondrianProperties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jaspersoft.jasperserver.api.metadata.common.service.RepositoryService;
import com.jaspersoft.jasperserver.api.common.properties.PropertyChanger;
import com.jaspersoft.jasperserver.api.logging.audit.context.AuditContext;
import com.jaspersoft.jasperserver.api.logging.audit.domain.AuditEvent;

/**
 * @author sbirney (sbirney@users.sourceforge.net)
 * @version $Id: EditMondrianPropertiesAction.java 8410 2007-05-29 23:34:07Z melih $
 */
public class MondrianPropertyChanger extends PropertyChangerAdapter {

    private static final Log log = LogFactory.getLog(MondrianPropertyChanger.class);

    public void setProperty(String key, String val) {
		log.debug("setting mondrian property: " + key + " - " + val);
		MondrianProperties.instance().setProperty((String)key, (String)val);
	}

	public String getProperty(String key) {
		return MondrianProperties.instance().getProperty(key);
	}

}