package com.jaspersoft.jasperserver.dto.resources.domain;

import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressionContainer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id: ResourceMetadataSingleElementTest.java 64791 2016-10-12 15:08:37Z ykovalch $
 * @see
 */
public class ResourceMetadataSingleElementTest {

    public static final String SOURCE_NAME = "SourceName";
    public static final String ELEMENT_NAME = "name";
    public static final String TYPE = "Type";
    public static final ClientExpressionContainer EXPRESSION = new ClientExpressionContainer().setString("Expression");
    public static final String REFERENCE = "Reference";
    ResourceMetadataSingleElement sourceElement;
    ResourceMetadataSingleElement clonedElement;

    @Before
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