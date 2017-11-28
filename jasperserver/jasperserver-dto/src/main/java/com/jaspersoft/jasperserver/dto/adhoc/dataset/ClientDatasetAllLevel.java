/*
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved.
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
package com.jaspersoft.jasperserver.dto.adhoc.dataset;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * @author Volodya Sabadosh
 * @version $Id: ClientDatasetAllLevel.java 64626 2016-09-26 13:25:24Z vzavadsk $
 */
public class ClientDatasetAllLevel extends AbstractClientDatasetLevel {

    public ClientDatasetAllLevel() {}

    public ClientDatasetAllLevel(ClientDatasetAllLevel level) {
        super(level);
    }

    @Override
    @XmlElementWrapper(name = "aggregations")
    @XmlElement(name = "aggregation")
    public List<ClientDatasetFieldReference> getFieldRefs() {
        return super.getFieldRefs();
    }

    @Override
    public String toString() {
        return "ClientDatasetAllLevel{} " + super.toString();
    }
}
