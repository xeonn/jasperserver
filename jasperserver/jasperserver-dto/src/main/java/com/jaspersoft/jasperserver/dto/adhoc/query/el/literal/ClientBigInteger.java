/*
 * Copyright © 2005 - 2016. TIBCO Software Inc. All Rights Reserved.
 */
package com.jaspersoft.jasperserver.dto.adhoc.query.el.literal;

import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientLiteral;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ast.ClientELVisitor;

import javax.xml.bind.annotation.XmlRootElement;

import java.math.BigInteger;

import static com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientBigInteger.LITERAL_ID;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id: ClientBigInteger.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
@XmlRootElement(name = LITERAL_ID)
public class ClientBigInteger extends ClientLiteral<BigInteger, ClientBigInteger> {
    public static final String LITERAL_ID = "bigInteger";

    public ClientBigInteger(){
    }

    public ClientBigInteger(ClientBigInteger source){
        super(source);
    }

    public ClientBigInteger(BigInteger value){
        super(value);
    }

    @Override
    public ClientBigInteger setValue(BigInteger value) {
        this.value = value;
        return this;
    }

    @Override
    public BigInteger getValue() {
        return value;
    }

    @Override
    public ClientBigInteger deepClone() {
        return new ClientBigInteger(this);
    }

    public static ClientBigInteger valueOf(String numericString){
        return new ClientBigInteger().setValue(new BigInteger(numericString));
    }

    public static ClientBigInteger valueOf(Long value){
        return new ClientBigInteger().setValue(BigInteger.valueOf(value));
    }

    @Override
    public void accept(ClientELVisitor visitor) {
        visitor.visit(this);
        super.accept(visitor);
    }
}
