package com.jaspersoft.jasperserver.dto.resources.domain;

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
 * @version $Id: ResourceMetadataSingleElementTest.java 60548 2016-02-03 14:06:55Z tiefimen $
 * @see
 */
public class ResourceMetadataSingleElementTest {

    public static final String SOURCE_NAME = "SourceName";
    public static final String ELEMENT_NAME = "name";
    public static final String TYPE = "Type";
    public static final String EXPRESSION = "Expression";
    public static final String REFERENCE = "Reference";
    ResourceMetadataSingleElement sourceElement;
    ResourceMetadataSingleElement clonedElement;

    @BeforeMethod
    public void setUp() {

        sourceElement = new ResourceMetadataSingleElement()
                .setType(TYPE)
                .setExpression(EXPRESSION)
                .setSourceName(SOURCE_NAME)
                .setName(ELEMENT_NAME)
                .setIsIdentifier(true)
                .setReferenceTo(REFERENCE);
    }

    @Test
    public void testCloningConstructor() {

        clonedElement = new ResourceMetadataSingleElement(sourceElement);

        assertTrue(clonedElement.equals(sourceElement));
        assertFalse(sourceElement == clonedElement);
        assertNotNull(clonedElement.getName());
        assertEquals(ELEMENT_NAME, clonedElement.getName());
        assertNotNull(clonedElement.getSourceName());
        assertEquals(SOURCE_NAME, clonedElement.getSourceName());
        assertNotNull(clonedElement.getExpression());
        assertEquals(EXPRESSION, clonedElement.getExpression());
        assertNotNull(clonedElement.getType());
        assertEquals(TYPE, clonedElement.getType());
        assertNotNull(clonedElement.getIsIdentifier());
        assertEquals(Boolean.TRUE, clonedElement.getIsIdentifier());
        assertNotNull(clonedElement.getReferenceTo());
        assertEquals(REFERENCE, clonedElement.getReferenceTo());

    }
}