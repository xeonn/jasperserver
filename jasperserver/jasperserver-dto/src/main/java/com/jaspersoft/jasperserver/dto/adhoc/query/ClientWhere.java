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
package com.jaspersoft.jasperserver.dto.adhoc.query;

import com.jaspersoft.jasperserver.dto.adhoc.query.ast.ClientQueryClause;
import com.jaspersoft.jasperserver.dto.adhoc.query.ast.ClientQueryVisitor;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpression;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientList;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ParameterExpressionsMapXmlAdapter;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientBigInteger;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientBoolean;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientDate;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientDecimal;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientInteger;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientNull;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientRelativeDateRange;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientRelativeTimestampRange;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientString;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientTime;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientTimestamp;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.ClientFunction;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.comparison.ClientEquals;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.comparison.ClientGreater;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.comparison.ClientGreaterOrEqual;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.comparison.ClientLess;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.comparison.ClientLessOrEqual;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.comparison.ClientNotEqual;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.logical.ClientAnd;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.logical.ClientNot;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.logical.ClientOr;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.membership.ClientIn;
import com.jaspersoft.jasperserver.dto.adhoc.query.validation.ParameterMap;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stas on 4/8/15.
 */
@XmlRootElement(name = "where")
public class ClientWhere implements ClientQueryClause {

    private String filterExpression;

    @Valid
    private ClientExpression filterExpressionObject;

    @ParameterMap
    private Map<String, ClientExpression> parameters;

    public ClientWhere() {

    }

    public ClientWhere(ClientWhere where) {
        if (where != null) {
            this
                    .setFilterExpression(where.getFilterExpression())
                    .setParameters(
                            where.getParameters() != null ? new HashMap<String, ClientExpression>(where.getParameters()) : null);
        }
    }

    public ClientWhere(ClientExpression filters) {
        this.filterExpressionObject = filters;
    }

    public ClientWhere(String expression) {
        this.filterExpression = expression;
    }

    public ClientWhere(ClientExpression filters, Map<String, ClientExpression> parameters) {
        this.filterExpressionObject = filters;
        this.parameters = parameters;
    }

    public String getFilterExpression() {
        return filterExpression;
    }

    public ClientWhere setFilterExpression(String filterExpression) {
        this.filterExpression = filterExpression;
        return this;
    }

    @XmlElements(value = {
            @XmlElement(name = "not",
                    type = ClientNot.class),
            @XmlElement(name = "and",
                    type = ClientAnd.class),
            @XmlElement(name = "or",
                    type = ClientOr.class),
            @XmlElement(name = "greater",
                    type = ClientGreater.class),
            @XmlElement(name = "greaterOrEqual",
                type = ClientGreaterOrEqual.class),
            @XmlElement(name = "less",
                type = ClientLess.class),
            @XmlElement(name = "lessOrEqual",
                type = ClientLessOrEqual.class),
            @XmlElement(name = "notEqual",
                type = ClientNotEqual.class),
            @XmlElement(name = "equals",
                type = ClientEquals.class),
            @XmlElement(name = "function",
                type = ClientFunction.class),
            @XmlElement(name = "in",
                type = ClientIn.class),
            @XmlElement(name = "list",
                type = ClientList.class)
    })
    public ClientExpression getFilterExpressionObject() {
        return filterExpressionObject;
    }

    public ClientWhere setFilterExpressionObject(ClientExpression filterExpressionObject) {
        this.filterExpressionObject = filterExpressionObject;
        return this;
    }

    @XmlJavaTypeAdapter(ParameterExpressionsMapXmlAdapter.class)
    @XmlElements(value = {
            @XmlElement(name = "NULL",
                    type = ClientNull.class),
            @XmlElement(name = "boolean",
                    type = ClientBoolean.class),
            @XmlElement(name = "integer",
                    type = ClientInteger.class),
            @XmlElement(name = "bigInteger",
                    type = ClientBigInteger.class),
            @XmlElement(name = "string",
                    type = ClientString.class),
            @XmlElement(name = "date",
                    type = ClientDate.class),
            @XmlElement(name = "time",
                    type = ClientTime.class),
            @XmlElement(name = "timestamp",
                    type = ClientTimestamp.class),
            @XmlElement(name = "decimal",
                    type = ClientDecimal.class),
            @XmlElement(name = "list",
                    type = ClientList.class),
            @XmlElement(name = "relativeDateRange",
                    type = ClientRelativeDateRange.class),
            @XmlElement(name = "relativeTimestampRange",
                    type = ClientRelativeTimestampRange.class)
    })
    public Map<String, ClientExpression> getParameters() {
        return parameters;
    }

    @ParameterMap
    public ClientWhere setParameters(Map<String, ClientExpression> parameters) {
        this.parameters = parameters;
        return this;
    }

    @Override
    public void accept(ClientQueryVisitor visitor) {
        if (this.filterExpressionObject != null) {
            this.filterExpressionObject.accept(visitor);
        }
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientWhere where = (ClientWhere) o;

        if (getFilterExpressionObject() != null ? !getFilterExpressionObject().equals(where.getFilterExpressionObject()) : where.getFilterExpressionObject() != null) return false;
        if (getFilterExpression() != null ? !getFilterExpression().equals(where.getFilterExpression()) : where.getFilterExpression() != null)
            return false;
        return getParameters() != null ? getParameters().equals(where.getParameters()) : where.getParameters() == null;

    }

    @Override
    public int hashCode() {
        int result = getFilterExpressionObject() != null ? getFilterExpressionObject().hashCode() : 0;
        result = 31 * result + (getFilterExpression() != null ? getFilterExpression().hashCode() : 0);
        result = 31 * result + (getParameters() != null ? getParameters().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClientWhere{" +
                "filterExpression='" + filterExpression + '\'' +
                ", filterExpressionObject=" + filterExpressionObject +
                ", parameters=" + parameters +
                '}';
    }
}
