/*
* Copyright (C) 2005 - 2009 Jaspersoft Corporation. All rights  reserved.
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
* along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
*/
package com.jaspersoft.jasperserver.jaxrs.common;

/**
 * @author Volodya Sabadosh
 * @version $Id: AttributesConfig.java 50011 2014-10-09 16:57:26Z vzavadskii $
 */
public class AttributesConfig {
    private int maxLengthAttrName = 255;
    private int maxLengthAttrValue = 255;

    public int getMaxLengthAttrName() {
        return maxLengthAttrName;
    }

    public void setMaxLengthAttrName(int maxLengthAttrName) {
        this.maxLengthAttrName = maxLengthAttrName;
    }

    public int getMaxLengthAttrValue() {
        return maxLengthAttrValue;
    }

    public void setMaxLengthAttrValue(int maxLengthAttrValue) {
        this.maxLengthAttrValue = maxLengthAttrValue;
    }
}
