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
 * @version $Id: ResourceSingleElementTest.java 64791 2016-10-12 15:08:37Z ykovalch $
 * @see
 */
public class ResourceSingleElementTest {

    public static final String SOURCE_NAME = "SourceName";
    public static final String ELEMENT_NAME = "name";
    public static final String TYPE = "Type";
    public static final ClientExpressionContainer EXPRESSION = new ClientExpressionContainer().setString("Expression");
    ResourceSingleElement sourceElement;
    ResourceSingleElement clonedElement;

    @Before
    public void setUp() {

        sourceElement = new ResourceSingleElement()
                .setType(TYPE)
                .setExpression(EXPRESSION)
                .setSourceName(SOURCE_NAME)
                .setName(ELEMENT_NAME);
    }

    @Test
    public void testCloningConstructor() {

        clonedElement = new ResourceSingleElement(sourceElement);

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

    }

}