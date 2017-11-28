/*
 * Copyright © 2005 - 2016. TIBCO Software Inc. All Rights Reserved.
 */
package com.jaspersoft.jasperserver.dto.bridge;

import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpression;

import java.util.Set;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id: ExpressionParsingBridge.java 64415 2016-09-06 15:41:44Z ykovalch $
 */
public interface ExpressionParsingBridge {
    ClientExpression parseExpression(String expressionString, Set<String> variables);
}
