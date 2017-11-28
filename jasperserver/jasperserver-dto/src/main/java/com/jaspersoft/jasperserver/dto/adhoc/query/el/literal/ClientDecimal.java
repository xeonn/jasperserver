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
package com.jaspersoft.jasperserver.dto.adhoc.query.el.literal;

import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientLiteral;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ast.ClientELVisitor;
import com.jaspersoft.jasperserver.dto.common.ValueAcceptor;

import javax.xml.bind.annotation.XmlRootElement;

import java.math.BigDecimal;

import static com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientDecimal.LITERAL_ID;

/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @author Stas Chubar <schubar@tibco.com>
 * @version $Id $
 */
@XmlRootElement(name = LITERAL_ID)
public class ClientDecimal extends ClientLiteral<Double, ClientDecimal> implements ValueAcceptor<ClientDecimal> {

    public static final String LITERAL_ID = "decimal";

    public ClientDecimal() {
    }

    public ClientDecimal(Double value) {
        super(value);
    }

    public ClientDecimal(ClientDecimal source) {
        super(source);
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public ClientDecimal setValue(Double value) {
        this.value = value;
        return this;
    }

    public static ClientDecimal valueOf(String string){
        return new ClientDecimal().setValue(new BigDecimal(string).doubleValue());
    }

    public static ClientDecimal valueOf(BigDecimal bigDecimal){
        return new ClientDecimal().setValue(bigDecimal.doubleValue());
    }

    @Override
    public void accept(ClientELVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public ClientDecimal deepClone() {
        return new ClientDecimal(this);
    }

    @Override
    public ClientDecimal acceptValue(Object object) {
        if(object instanceof String){
            value = valueOf((String) object).getValue();
        } else if(object instanceof BigDecimal){
            value = valueOf((BigDecimal) object).getValue();
        } else {
            throw new IllegalArgumentException("Value of type [" + object.getClass().getName() + "] is not supported");
        }
        return this;
    }
}
