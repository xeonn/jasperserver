package com.jaspersoft.jasperserver.dto.resources.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id: SchemaElementTest.java 63760 2016-07-05 18:59:28Z agodovan $
 * @see
 */
public class SchemaElementTest {


    public static final String ELEMENT_NAME = "name";
    SchemaElement sourceElement;
    SchemaElement clonedElement;

    @Before
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