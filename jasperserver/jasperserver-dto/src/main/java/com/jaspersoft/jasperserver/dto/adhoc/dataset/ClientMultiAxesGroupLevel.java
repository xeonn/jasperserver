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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vasyl Spachynskyi
 * @version $Id: ClientMultiAxesGroupLevel.java 64626 2016-09-26 13:25:24Z vzavadsk $
 * @since 08.07.2016
 */
public class ClientMultiAxesGroupLevel extends ClientMultiAxesDatasetLevel {
    private ClientMultiAxesLevelReference referenceObject;
    private String type;

    public ClientMultiAxesGroupLevel() {
    }

    public ClientMultiAxesGroupLevel(ClientMultiAxesGroupLevel level) {
        super(level);
        referenceObject = new ClientMultiAxesLevelReference(level.getReferenceObject());
        type = level.getType();
    }

    public ClientMultiAxesLevelReference getReferenceObject() {
        return referenceObject;
    }

    public ClientMultiAxesGroupLevel setReferenceObject(ClientMultiAxesLevelReference referenceObject) {
        this.referenceObject = referenceObject;
        return this;
    }

    public String getType() {
        return type;
    }

    public ClientMultiAxesGroupLevel setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ClientMultiAxesGroupLevel that = (ClientMultiAxesGroupLevel) o;

        if (referenceObject != null ? !referenceObject.equals(that.referenceObject) : that.referenceObject != null)
            return false;
        return !(type != null ? !type.equals(that.type) : that.type != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (referenceObject != null ? referenceObject.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClientMultiAxesGroupLevel{" +
                "referenceObject=" + referenceObject +
                ", type='" + type + '\'' +
                ", members='" + getMembers() + '\'' +
                '}';
    }
}
