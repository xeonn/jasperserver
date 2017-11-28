package com.jaspersoft.jasperserver.dto.resources.domain;

import java.util.ArrayList;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id: JoinResourceGroupElementTest.java 60548 2016-02-03 14:06:55Z tiefimen $
 * @see
 */
public class JoinResourceGroupElementTest {

    public static final String FILTER_EXPRESSION = "FilterExpression";
    public static final String SOURCE_NAME = "SourceName";
    public static final String ELEMENT_NAME = "Name";
    JoinResourceGroupElement sourceElement;
    JoinResourceGroupElement clonedElement;

    @BeforeMethod
    public void setUp() {
        sourceElement = new JoinResourceGroupElement()
                .setJoinInfo(new JoinInfo()
                        .setIncludeAllJoinsForQueryFieldTables(true)
                        .setJoins(new ArrayList<Join>()))
                .setElements(new ArrayList<SchemaElement>())
                .setFilterExpression(FILTER_EXPRESSION)
                .setSourceName(SOURCE_NAME)
                .setName(ELEMENT_NAME);

    }

    @Test
    public void testCloningConstructor() throws Exception {

        clonedElement = new JoinResourceGroupElement(sourceElement);

        assertTrue(clonedElement.equals(sourceElement));
        assertFalse(sourceElement == clonedElement);
        assertFalse(sourceElement.getJoinInfo() == clonedElement.getJoinInfo());
        assertEquals(sourceElement.getJoinInfo(), clonedElement.getJoinInfo());
        assertFalse(sourceElement.getElements() == clonedElement.getElements());
        assertNotNull(clonedElement.getFilterExpression());
        assertEquals(FILTER_EXPRESSION, clonedElement.getFilterExpression());
        assertNotNull(clonedElement.getSourceName());
        assertEquals(SOURCE_NAME, clonedElement.getSourceName());
        assertNotNull(clonedElement.getName());
        assertEquals(ELEMENT_NAME, clonedElement.getName());

    }
}