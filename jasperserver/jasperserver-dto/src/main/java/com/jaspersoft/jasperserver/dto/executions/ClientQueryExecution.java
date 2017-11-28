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
package com.jaspersoft.jasperserver.dto.executions;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientQuery;

import javax.validation.constraints.NotNull;

/**
 * @author Vasyl Spachynskyi
 * @version $Id: ClientQueryExecution.java 62804 2016-04-25 17:28:45Z vsabados $
 * @since 13.01.2016
 */
public abstract class ClientQueryExecution<Query extends ClientQuery, T extends ClientQueryExecution<Query, T>>
        extends AbstractClientExecution<T> {

    public ClientQueryExecution() {
    }

    public ClientQueryExecution(ClientQueryExecution<Query, T> clientExecution) {
        super(clientExecution);
        setParams(new ClientQueryParams(clientExecution.getParams()));
    }

    @NotNull
    public abstract Query getQuery();

    @Override
    public ClientQueryParams getParams() {
        return super.getParams();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ClientQueryExecution that = (ClientQueryExecution) o;

        return !(getQuery() != null ? !getQuery().equals(that.getQuery()) : that.getQuery() != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getQuery() != null ? getQuery().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClientQueryExecution{" +
                "query=" + getQuery() +
                '}';
    }
}