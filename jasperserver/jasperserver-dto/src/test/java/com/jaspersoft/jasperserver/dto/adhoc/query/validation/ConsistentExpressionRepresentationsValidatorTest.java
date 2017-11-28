/*
 * Copyright © 2005 - 2016. TIBCO Software Inc. All Rights Reserved.
 */
package com.jaspersoft.jasperserver.dto.adhoc.query.validation;

import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressionContainer;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientBoolean;
import com.jaspersoft.jasperserver.dto.bridge.BridgeRegistry;
import com.jaspersoft.jasperserver.dto.bridge.ExpressionParsingBridge;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id: ConsistentExpressionRepresentationsValidatorTest.java 64415 2016-09-06 15:41:44Z ykovalch $
 */
public class ConsistentExpressionRepresentationsValidatorTest {
    private ConsistentExpressionRepresentationsValidator validator = new ConsistentExpressionRepresentationsValidator();
    private static ExpressionParsingBridge beforeTestsBridge;

    private static ExpressionParsingBridge bridgeMock = mock(ExpressionParsingBridge.class);

    @BeforeClass
    public static void beforeClass(){
        beforeTestsBridge = BridgeRegistry.getBridge(ExpressionParsingBridge.class);
        BridgeRegistry.registerBridge(ExpressionParsingBridge.class, bridgeMock);
    }

    @Before
    public void beforeMethod(){
        reset(bridgeMock);
    }

    @AfterClass
    public static void afterClass(){
        BridgeRegistry.registerBridge(ExpressionParsingBridge.class, beforeTestsBridge);
    }

    @Test
    public void isValid_stringOnly_valid(){
        validator.isValid(new ClientExpressionContainer().setString("some string"), null);
        verify(bridgeMock, never()).parseExpression(any(String.class), any(Set.class));
    }

    @Test
    public void isValid_objectOnly_valid(){
        validator.isValid(new ClientExpressionContainer().setObject(new ClientBoolean(true)), null);
        verify(bridgeMock, never()).parseExpression(any(String.class), any(Set.class));
    }

    @Test
    public void isValid_both_stringEquals_valid(){
        validator.isValid(new ClientExpressionContainer().setObject(new ClientBoolean(true)).setString("true"), null);
        verify(bridgeMock, never()).parseExpression(any(String.class), any(Set.class));
    }

    @Test
    public void isValid_both_stringNotEquals_noBridge_valid(){
        BridgeRegistry.registerBridge(ExpressionParsingBridge.class, null);
        validator.isValid(new ClientExpressionContainer().setObject(new ClientBoolean(true)).setString("false"), null);
        BridgeRegistry.registerBridge(ExpressionParsingBridge.class, bridgeMock);

    }

    @Test
    public void isValid_both_stringNotEquals_withBridge_valid(){
        when(bridgeMock.parseExpression(any(String.class), any(Set.class))).thenReturn(new ClientBoolean(true));
        validator.isValid(new ClientExpressionContainer().setObject(new ClientBoolean(true)).setString("false"), null);
    }

    @Test
    public void isValid_both_stringNotEquals_withBridge_invalid(){
        when(bridgeMock.parseExpression(any(String.class), any(Set.class))).thenReturn(new ClientBoolean(false));
        validator.isValid(new ClientExpressionContainer().setObject(new ClientBoolean(true)).setString("false"), null);
    }
}
