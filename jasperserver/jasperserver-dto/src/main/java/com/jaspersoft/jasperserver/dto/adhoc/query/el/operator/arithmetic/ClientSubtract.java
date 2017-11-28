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
package com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.arithmetic;

import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ast.ClientELVisitor;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpression;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientOperator;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.ClientOperation;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @author Stas Chubar <schubar@tibco.com>
 * @version $Id $
 */
@XmlRootElement(name = ClientSubtract.OPERATOR_ID)
public class ClientSubtract extends ClientOperator<ClientSubtract> {

    public static final String OPERATOR_ID = "subtract";
    public static final String DOMEL_OPERATOR = "-";

    public ClientSubtract() {
        super(ClientOperation.SUBTRACT.getName());
    }

    public ClientSubtract(List<ClientExpression> operands) {
        super(ClientOperation.SUBTRACT.getName(), operands);
    }

    public ClientSubtract(ClientSubtract difference) {
        super(difference.getOperator(), difference.getOperands(), difference.paren);
    }

    @Override
    public String toString() {
        final String lhs = (getOperands() != null && !getOperands().isEmpty()) ? getOperands().get(0).toString() : ClientExpressions.MISSING_REPRESENTATION;
        final String rhs = (getOperands() != null && getOperands().size() > 0) ? getOperands().get(1).toString() : ClientExpressions.MISSING_REPRESENTATION;
        final String composedExpression = lhs + " " + DOMEL_OPERATOR + " " + rhs;

        return hasParen() ? "(" + composedExpression + ")" : composedExpression;
    }

    @Override
    public ClientSubtract setParen() {
        super.setParen();
        return this;
    }

    @Override
    public void accept(ClientELVisitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }

    @Override
    public ClientSubtract deepClone() {
        return new ClientSubtract(this);
    }
}
