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

package com.jaspersoft.jasperserver.export.modules.common;

import com.jaspersoft.jasperserver.api.metadata.user.domain.ProfileAttribute;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: ProfileAttributeBean.java 19925 2010-12-11 15:06:41Z tmatyashovsky $
 */
public class ProfileAttributeBean {

	private String name;
	private String value;
	
	public void copyFrom(ProfileAttribute attribute) {
		setName(attribute.getAttrName());
		setValue(attribute.getAttrValue());
	}
	
	public void copyTo(ProfileAttribute attribute) {
		attribute.setAttrName(getName());
		attribute.setAttrValue(getValue());
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}