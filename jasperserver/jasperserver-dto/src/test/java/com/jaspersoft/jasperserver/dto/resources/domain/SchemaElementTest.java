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
 * @version $Id: SchemaElementTest.java 60548 2016-02-03 14:06:55Z tiefimen $
 * @see
 */
public class SchemaElementTest {


    public static final String ELEMENT_NAME = "name";
    SchemaElement sourceElement;
    SchemaElement clonedElement;

    @BeforeMethod
    public void setUp() {
        sourceElement = new SchemaElement()
                .setName(ELEMENT_NAME);
    }

    @Test
    public void testCloningConstructor() {

        clonedElement = new SchemaElement(sourceElement);

        assertTrue(clonedElement.equals(sourceElement));
        assertFalse(sourceElement == clonedElement);
        assertNotNull(clonedElement.getName());
        assertEquals(ELEMENT_NAME, clonedElement.getName());
    }

}