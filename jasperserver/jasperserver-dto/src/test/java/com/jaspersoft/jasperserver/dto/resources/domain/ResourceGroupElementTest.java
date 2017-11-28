package com.jaspersoft.jasperserver.dto.resources.domain;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id: ResourceGroupElementTest.java 60839 2016-02-12 15:37:14Z ykovalch $
 * @see
 */
public class ResourceGroupElementTest {

    public static final String ELEMENT_NAME = "name";
    public static final String FILTER_EXPRESSION = "FilterExpression";
    public static final String SOURCE_NAME = "SourceName";
    ResourceGroupElement sourceElement;
    ResourceGroupElement clonedElement;

    @BeforeMethod
    public void setUp() {
        sourceElement = new ResourceGroupElement()
                .setName(ELEMENT_NAME)
                .setElements(new ArrayList<SchemaElement>())
                .setFilterExpression(FILTER_EXPRESSION)
                .setSourceName(SOURCE_NAME);
    }

    @Test
    public void testCloningConstructor() {

        clonedElement = new ResourceGroupElement(sourceElement);

        assertTrue(clonedElement.equals(sourceElement));
        assertFalse(sourceElement == clonedElement);
        assertFalse(sourceElement.getElements() == clonedElement.getElements());
        assertNotNull(clonedElement.getName());
        assertEquals(ELEMENT_NAME, clonedElement.getName());
        assertNotNull(clonedElement.getFilterExpression());
        assertEquals(FILTER_EXPRESSION, clonedElement.getFilterExpression());
        assertNotNull(clonedElement.getSourceName());
        assertEquals(SOURCE_NAME, clonedElement.getSourceName());
    }

}