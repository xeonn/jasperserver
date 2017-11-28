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
package com.jaspersoft.jasperserver.dto.adhoc.query.el;

import com.jaspersoft.jasperserver.dto.adhoc.query.el.ast.ClientELVisitor;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientLiteralType;
import com.jaspersoft.jasperserver.dto.common.ValueAcceptor;
import com.jaspersoft.jasperserver.dto.common.ValueHolder;

/**
 * @author Stas Chubar <schubar@tibco.com>
 * @author Grant Bacon <gbacon@tibco.com>
 * @version $Id $
 */
public abstract class ClientLiteral<T, F extends ClientLiteral<T, F>> implements ValueHolder<T,F>, ClientExpression<F> {

    protected T value;
    private Boolean paren = null;

    protected ClientLiteral() {

    }

    protected ClientLiteral(T value) {
        this.value = value;
    }

    protected ClientLiteral(ClientLiteral<T, F> source){
        value = source.getValue();
    }

    public static ClientLiteral createLiteral(String type, Object value){
        final ClientLiteral clientLiteral = ClientLiteralType.fromString(type).getLiteralInstance();
        try {
            clientLiteral.setValue(value);
        } catch (ClassCastException e){
            // indirect literal type. Let's try to accept such value
            if(clientLiteral instanceof ValueAcceptor){
                ((ValueAcceptor) clientLiteral).acceptValue(value);
            } else {
                throw new IllegalArgumentException("Value of type [" + value.getClass().getName() + "] is not supported");
            }
        }
        return clientLiteral;
    }

    /**
     * Abstract method declaration is required here, because otherwise clientLiteral.deepClone() returns Object
     * @return
     */
    public abstract F deepClone();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientLiteral)) return false;

        ClientLiteral<?, ?> that = (ClientLiteral<?, ?>) o;

        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        return paren != null ? paren.equals(that.paren) : that.paren == null;

    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (paren != null ? paren.hashCode() : 0);
        return result;
    }

    public static boolean isSupported(String text) {
        return ClientLiteralType.isSupported(text);
    }

    @Override
    public String toString() {
        return value == null ? ClientExpressions.MISSING_REPRESENTATION : value.toString();
    }

    public Boolean isParen() {
        return (paren == null) ? null : paren;
    }

    @Override
    public Boolean hasParen() {
        return isParen() != null && paren;
    }

    @Override
    public void accept(ClientELVisitor visitor) {
        visitor.visit(this);
    }
}
