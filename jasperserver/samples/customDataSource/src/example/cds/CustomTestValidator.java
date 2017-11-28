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

package example.cds;

import org.springframework.validation.Errors;

import com.jaspersoft.jasperserver.api.engine.jasperreports.util.CustomDataSourceValidator;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.CustomReportDataSource;

/**
 * @author bob
 *
 */
public class CustomTestValidator implements CustomDataSourceValidator {

	/* check the values in the map; call rejectValue if tests don't pass
	 */
	public void validatePropertyValues(CustomReportDataSource ds, Errors errors) {
		String foo = null;
		if (ds.getPropertyMap() != null) {
			foo = (String) ds.getPropertyMap().get("foo");
		}
		if (foo == null || foo.length() == 0) {
			// first arg is the path of the property which has the error
			// for custom DS's this will always be in the form "reportDataSource.propertyMap[yourPropName]"
			errors.rejectValue("reportDataSource.propertyMap[foo]", "myCustomDataSource.foo.required");
		}
	}

}
