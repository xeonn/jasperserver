/*
 *
 *  JasperReports - Free Java Reporting Library.
 *  Copyright (C) 2001 - 2009 Jaspersoft Corporation. All rights reserved.
 *  http://www.jaspersoft.com.
 *
 *  Unless you have purchased a commercial license agreement from Jaspersoft,
 *  the following license terms apply:
 *
 *  This program is part of JasperReports.
 *
 *  JasperReports is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  JasperReports is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 * /
 */

package com.jaspersoft.jasperserver.api.common.util.diagnostic;

/**
 * @author Yakiv Tymoshenko
 * @version $Id: FilterBy.java 65088 2016-11-03 23:22:01Z gbacon $
 * @since 16.06.14
 */
public enum FilterBy {
    USER_ID, SESSION_ID, RESOURCE_URI;

    public boolean isResourceUri() {
        return name().equals(RESOURCE_URI.name());
    }
}
