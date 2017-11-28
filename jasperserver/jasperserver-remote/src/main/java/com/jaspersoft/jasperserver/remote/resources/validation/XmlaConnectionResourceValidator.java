/*
 * Copyright (C) 2005 - 2013 Jaspersoft Corporation. All rights reserved.
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

package com.jaspersoft.jasperserver.remote.resources.validation;

import com.jaspersoft.jasperserver.api.common.domain.ValidationErrors;
import com.jaspersoft.jasperserver.api.metadata.olap.domain.XMLAConnection;
import org.springframework.stereotype.Component;

import static com.jaspersoft.jasperserver.remote.resources.validation.ValidationHelper.addMandatoryParameterNotFoundError;
import static com.jaspersoft.jasperserver.remote.resources.validation.ValidationHelper.empty;

/**
 * <p></p>
 *
 * @author Zakhar.Tomchenco
 * @version $Id$
 */
@Component
public class XmlaConnectionResourceValidator extends GenericResourceValidator<XMLAConnection> {
    @Override
    protected void internalValidate(XMLAConnection resource, ValidationErrors errors) {
        if (empty(resource.getDataSource())) {
            addMandatoryParameterNotFoundError(errors, "DataSource");
        }

        if (empty(resource.getCatalog())) {
            addMandatoryParameterNotFoundError(errors, "Catalog");
        }

        if (empty(resource.getUsername())) {
            addMandatoryParameterNotFoundError(errors, "Username");
        }

        if (empty(resource.getURI())) {
            addMandatoryParameterNotFoundError(errors, "URL");
        }
    }
}
