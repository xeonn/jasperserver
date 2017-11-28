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

import javax.xml.bind.annotation.XmlRootElement;

import static com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientInteger.LITERAL_ID;

/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @author Stas Chubar <schubar@tibco.com>
 * @version $Id: ClientInteger.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
@XmlRootElement(name = LITERAL_ID)
public class ClientInteger extends ClientLiteral<Integer, ClientInteger> {

    public static final String LITERAL_ID = "integer";

    public ClientInteger() {
    }

    public ClientInteger(ClientInteger literal) {
        super(literal);
    }

    public ClientInteger(Integer integer) {
        super(integer);
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public ClientInteger setValue(Integer value) {
        this.value = value;
        return this;
    }

    public static ClientInteger valueOf(String string){
        return new ClientInteger().setValue(Integer.valueOf(string));
    }


    @Override
    public void accept(ClientELVisitor visitor) {
        visitor.visit(this);
        super.accept(visitor);
    }

    @Override
    public ClientInteger deepClone() {
        return new ClientInteger(this);
    }
}
