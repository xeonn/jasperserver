/*
* Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
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
package com.jaspersoft.jasperserver.dto.adhoc.query.el;

import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientBigInteger;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientBoolean;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientDate;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientDecimal;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientInteger;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientNull;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientRelativeDateRange;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientString;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientTime;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientTimestamp;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.ClientFunction;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.arithmetic.ClientAdd;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.arithmetic.ClientDivide;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.arithmetic.ClientMultiply;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.arithmetic.ClientSubtract;
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
import com.jaspersoft.jasperserver.dto.adhoc.query.el.range.ClientRange;
import com.jaspersoft.jasperserver.dto.adhoc.query.validation.ConsistentExpressionRepresentations;
import com.jaspersoft.jasperserver.dto.common.DeepCloneable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id$
 */
@ConsistentExpressionRepresentations
@XmlRootElement(name = "expression")
public class ClientExpressionContainer implements DeepCloneable<ClientExpressionContainer> {
    private ClientExpression object;
    private String string;

    public ClientExpressionContainer(){}
    public ClientExpressionContainer(ClientExpressionContainer source){
        this.object = source.getObject() != null ? (ClientExpression) source.getObject()
                .deepClone() : null;
        this.string = source.getString();
    }

    @XmlElements(value = {
            @XmlElement(name = "NULL",
                    type = ClientNull.class),
            @XmlElement(name = "boolean",
                    type = ClientBoolean.class),
            @XmlElement(name = "integer",
                    type = ClientInteger.class),
            @XmlElement(name = "bigInteger",
                    type = ClientBigInteger.class),
            @XmlElement(name = "relativeDateRange",
                    type = ClientRelativeDateRange.class),
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
            @XmlElement(name = "variable",
                    type = ClientVariable.class),
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
            @XmlElement(name = "range",
                    type = ClientRange.class),
            @XmlElement(name = "add",
                    type = ClientAdd.class),
            @XmlElement(name = "subtract",
                    type = ClientSubtract.class),
            @XmlElement(name = "multiply",
                    type = ClientMultiply.class),
            @XmlElement(name = "divide",
                    type = ClientDivide.class),
            @XmlElement(name = "list",
                    type = ClientList.class)
    })
    public ClientExpression getObject() {
        return object;
    }

    public ClientExpressionContainer setObject(ClientExpression expressionObject) {
        this.object = expressionObject;
        return this;
    }

    public String getString() {
        return string;
    }

    public ClientExpressionContainer setString(String expressionString) {
        this.string = expressionString;
        return this;
    }

    @XmlTransient
    public String getExpression(){
        return object != null ? object.toString() : string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientExpressionContainer)) return false;

        ClientExpressionContainer that = (ClientExpressionContainer) o;

        if (object != null ? !object.equals(that.object) : that.object != null)
            return false;
        return string != null ? string.equals(that.string) : that.string == null;

    }

    @Override
    public int hashCode() {
        int result = object != null ? object.hashCode() : 0;
        result = 31 * result + (string != null ? string.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClientExpressionContainer{" +
                "object=" + object +
                ", string='" + string + '\'' +
                '}';
    }

    @Override
    public ClientExpressionContainer deepClone() {
        return new ClientExpressionContainer(this);
    }
}
