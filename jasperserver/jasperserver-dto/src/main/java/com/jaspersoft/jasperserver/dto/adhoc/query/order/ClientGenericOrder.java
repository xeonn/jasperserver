/*
 * Copyright (C) 2005 - 2016 TIBCO Software Inc. All rights reserved.
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
package com.jaspersoft.jasperserver.dto.adhoc.query.order;


import com.jaspersoft.jasperserver.dto.adhoc.query.ClientFieldReference;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author Andriy Godovanets
 */
public class ClientGenericOrder implements ClientOrder, ClientFieldReference {
    private Boolean isAscending;
    @NotNull
    private String fieldRef;

    public ClientGenericOrder() {
        // no op
    }

    public ClientGenericOrder(ClientGenericOrder sorting) {
        if (sorting != null) {
            this
                    .setAscending(sorting.isAscending())
                    .setFieldReference(sorting.getFieldReference());
        }
    }

    @Override
    public Boolean isAscending() {
        return isAscending;
    }

    public ClientGenericOrder setAscending(Boolean isAscending) {
        this.isAscending = isAscending;
        return this;
    }
    /**
     * @return Query Field Identifier or Metadata Field Name to order by
     */
    @XmlElement(name = "fieldRef")
    public String getFieldReference() {
        return fieldRef;
    }

    public ClientGenericOrder setFieldReference(String fieldReference) {
        this.fieldRef = fieldReference;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientGenericOrder that = (ClientGenericOrder) o;

        if (isAscending() != that.isAscending()) return false;
        if (getFieldReference() != null ? !getFieldReference().equals(that.getFieldReference()) : that.getFieldReference() != null)
            return false;
        return true;

    }

    @Override
    public int hashCode() {
        int result = (this.isAscending != null && isAscending ? 1 : 0);
        result = 31 * result + (getFieldReference() != null ? getFieldReference().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BaseSorting{" +
                "isAscending=" + isAscending +
                ", fieldReference='" + fieldRef + '\'' +
                '}';
    }
}
