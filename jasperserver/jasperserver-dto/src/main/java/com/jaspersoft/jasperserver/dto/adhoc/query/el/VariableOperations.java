package com.jaspersoft.jasperserver.dto.adhoc.query.el;

import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientDecimal;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientInteger;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientLiteralType;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientBigInteger;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientString;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.membership.ClientIn;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.range.ClientRange;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.range.ClientRangeBoundary;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * <p/>
 * </p>
 *
 * @author Stas Chubar (schubar@tibco.com)
 * @version $Id: VariableOperations.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
public abstract class VariableOperations extends Operations<ClientVariable> {

    public ClientIn in(ClientLiteral... values) {
        List<ClientExpression> exprList = new ArrayList<ClientExpression>();
        Collections.addAll(exprList, values);
        return new ClientIn(getMe(), exprList);
    }

    public ClientIn in(String... values) {
        return new ClientIn(getMe(), valuesToLiteralList(ClientLiteralType.STRING, values));
    }

    public ClientIn in(Boolean... values) {
        return new ClientIn(getMe(), valuesToLiteralList(ClientLiteralType.BOOLEAN, values));
    }

    public ClientIn in(Integer... values) {
        return new ClientIn(getMe(), valuesToLiteralList(ClientLiteralType.INTEGER, values));
    }

    public ClientIn in(Double... values) {
        return new ClientIn(getMe(), valuesToLiteralList(ClientLiteralType.DECIMAL, values));
    }

    public ClientIn in(BigDecimal... values) {
        return new ClientIn(getMe(), valuesToLiteralList(ClientLiteralType.DECIMAL, values));
    }


    public ClientIn in(ClientRange range) {
        return new ClientIn(getMe(), range);
    }

    public ClientIn inRange(Integer start, Integer end) {
        return new ClientIn(getMe(),
                new ClientRange(new ClientRangeBoundary(new ClientInteger(start)),
                        new ClientRangeBoundary(new ClientInteger(end))
                )
        );
    }

    public ClientIn inRange(BigInteger start, BigInteger end) {
        return new ClientIn(getMe(),
                new ClientRange(new ClientRangeBoundary(new ClientBigInteger(start)),
                        new ClientRangeBoundary(new ClientBigInteger(end))
                )
        );
    }

    public ClientIn inRange(Long start, Long end) {
        return new ClientIn(getMe(),
                new ClientRange(new ClientRangeBoundary(ClientBigInteger.valueOf(start)),
                        new ClientRangeBoundary(ClientBigInteger.valueOf(end))
                )
        );
    }

    public ClientIn inRange(Double start, Double end) {
        return new ClientIn(getMe(),
                new ClientRange(new ClientRangeBoundary(new ClientDecimal(start)),
                        new ClientRangeBoundary(new ClientDecimal(end))
                )
        );
    }

    public ClientIn inRange(BigDecimal start, BigDecimal end) {
        return new ClientIn(getMe(),
                new ClientRange(new ClientRangeBoundary(new ClientDecimal(start.doubleValue())),
                        new ClientRangeBoundary(new ClientDecimal(end.doubleValue()))
                )
        );
    }

    public ClientIn inRange(String start, String end) {
        return new ClientIn(getMe(),
                new ClientRange(new ClientRangeBoundary(new ClientString(start)),
                        new ClientRangeBoundary(new ClientString(end))
                )
        );
    }

}
