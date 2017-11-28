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
package com.jaspersoft.jasperserver.dto.adhoc.query.field;

import com.jaspersoft.jasperserver.dto.adhoc.datasource.ClientDataSourceField;
import com.jaspersoft.jasperserver.dto.adhoc.query.ClientAggregate;
import com.jaspersoft.jasperserver.dto.adhoc.query.ClientField;
import com.jaspersoft.jasperserver.dto.adhoc.query.ClientFieldReference;
import com.jaspersoft.jasperserver.dto.adhoc.query.ClientIdentifiable;
import com.jaspersoft.jasperserver.dto.adhoc.query.ast.ClientQueryExpression;
import com.jaspersoft.jasperserver.dto.adhoc.query.ast.ClientQueryVisitor;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpression;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientVariable;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.*;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.ClientFunction;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.arithmetic.ClientAdd;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.arithmetic.ClientDivide;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.arithmetic.ClientMultiply;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.arithmetic.ClientSubtract;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.comparison.*;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.logical.ClientAnd;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.logical.ClientNot;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.logical.ClientOr;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.membership.ClientIn;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.range.ClientRange;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Andriy Godovanets
 */
public class ClientQueryAggregatedField implements ClientField, ClientAggregate, ClientFieldReference, ClientIdentifiable<String>, ClientQueryExpression {
    private String id;
    private String aggregateFunction;
    private String aggregateExpression;
    private ClientExpression aggregateExpressionObject;
    //TODO-Andriy Remove
    private String aggregateType;
    //TODO-Andriy Remove
    private String aggregateArg;

    @NotNull
    private String fieldRef;

    public ClientQueryAggregatedField() {
    }

    public ClientQueryAggregatedField(ClientQueryAggregatedField field) {
        if (field != null) {
            this
                    .setId(field.getId())
                    .setFieldReference(field.getFieldReference())
                    .setAggregateFunction(field.getAggregateFunction())
                    .setAggregateExpression(field.getAggregateExpression())
                    .setAggregateExpressionObject(field.getAggregateExpressionObject())
                    .setAggregateType(field.getAggregateType())
                    .setAggregateArg(field.getAggregateArg());
        }
    }

    @Override
    public String getId() {
        return id;
    }

    public ClientQueryAggregatedField setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    @XmlTransient
    public String getName() {
        return fieldRef;
    }

    @Override
    @XmlElement(name = "type")
    public String getType() {
        return getAggregateType();
    }

    @Override
    @XmlElement(name = "fieldRef")
    public String getFieldReference() {
        return fieldRef;
    }

    public ClientQueryAggregatedField setFieldReference(String fieldRef) {
        this.fieldRef = fieldRef;
        return this;
    }

    @Override
    @XmlElement(name = "functionName")
    public String getAggregateFunction() {
        return aggregateFunction;
    }

    public ClientQueryAggregatedField setAggregateFunction(String aggregateFunction) {
        this.aggregateFunction = aggregateFunction;
        return this;
    }

    @Override
    @XmlElement(name = "expression")
    public String getAggregateExpression() {
        return aggregateExpression;
    }

    public ClientQueryAggregatedField setAggregateExpression(String aggregateExpression) {
        this.aggregateExpression = aggregateExpression;
        return this;
    }

//    @XmlElement(name = "expressionObject")
    @XmlElements(value = {
            @XmlElement(name = "add",
                    type = ClientAdd.class),
            @XmlElement(name = "subtract",
                    type = ClientSubtract.class),
            @XmlElement(name = "multiply",
                    type = ClientMultiply.class),
            @XmlElement(name = "divide",
                    type = ClientDivide.class),
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
                    type = ClientRange.class)
    })
    public ClientExpression getAggregateExpressionObject() {
        return aggregateExpressionObject;
    }

    public ClientQueryAggregatedField setAggregateExpressionObject(ClientExpression aggregateExpressionObject) {
        this.aggregateExpressionObject = aggregateExpressionObject;
        return this;
    }

    /**
     * {@link #setAggregateArg(String)}
     * @return
     */
    @Deprecated
    @Override
    @XmlElement(name = "arg")
    public String getAggregateArg() {
        return aggregateArg;
    }

    /**
     * TODO-Andriy Bob suggested to remove it.
     * TODO-Andriy Because this field is used in case of function is WeightedAverage. We propose to make WeightedAverage available only if it's was defined in metadata as default function.
     * That means we cant specify it in the aggregate function but we will be able to use it as default or in custom expression
     *
     * @return
     */
    @Deprecated
    public ClientQueryAggregatedField setAggregateArg(String aggregateArg) {
        this.aggregateArg = aggregateArg;
        return this;
    }

    @Override
    @XmlTransient
    public String getAggregateType() {
        return aggregateType;
    }

    public ClientQueryAggregatedField setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
        return this;
    }

    public ClientQueryAggregatedField setDataSourceField(ClientDataSourceField field) {
        setFieldReference(field.getName());
        return this;
    }

    /**
     * {@link #setAggregateArg(String)}
     * @return
     */
    @Deprecated
    public String getSecondAggregateField() {
        return getAggregateArg();
    }

    @XmlTransient
    @AssertTrue(message="query.aggregate.definition.error")
    public boolean isValidAggregate() {
        boolean isDefault = (getAggregateFunction() == null && getAggregateExpression() == null);
        boolean isBuiltin = (getAggregateFunction() != null && !getAggregateFunction().isEmpty());
        boolean isCustom = (getAggregateExpression() != null && !getAggregateExpression().isEmpty())
                || (getAggregateExpressionObject() != null);


        return isDefault || (isBuiltin && !isCustom) || (isCustom && !isBuiltin);
    }

    @Override
    public void accept(ClientQueryVisitor visitor) {
        if (this.aggregateExpressionObject != null) {
            this.aggregateExpressionObject.accept(visitor);
        }
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientQueryAggregatedField that = (ClientQueryAggregatedField) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (aggregateFunction != null ? !aggregateFunction.equals(that.aggregateFunction) : that.aggregateFunction != null)
            return false;
        if (aggregateExpression != null ? !aggregateExpression.equals(that.aggregateExpression) : that.aggregateExpression != null)
            return false;
        if (aggregateExpressionObject != null ? !aggregateExpressionObject.equals(that.aggregateExpressionObject) : that.aggregateExpressionObject != null)
            return false;
        if (aggregateType != null ? !aggregateType.equals(that.aggregateType) : that.aggregateType != null)
            return false;
        return aggregateArg != null ? aggregateArg.equals(that.aggregateArg) : that.aggregateArg == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (aggregateFunction != null ? aggregateFunction.hashCode() : 0);
        result = 31 * result + (aggregateExpression != null ? aggregateExpression.hashCode() : 0);
        result = 31 * result + (aggregateExpressionObject != null ? aggregateExpressionObject.hashCode() : 0);
        result = 31 * result + (aggregateType != null ? aggregateType.hashCode() : 0);
        result = 31 * result + (aggregateArg != null ? aggregateArg.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClientQueryAggregatedField{" +
                "id='" + id + '\'' +
                ", aggregateFunction='" + aggregateFunction + '\'' +
                ", aggregateExpression='" + aggregateExpression + '\'' +
                ", aggregateExpressionObject=" + aggregateExpressionObject +
                ", aggregateType='" + aggregateType + '\'' +
                ", aggregateArg='" + aggregateArg + '\'' +
                ", name='" + getName() + '\'' +
                '}';
    }
}
