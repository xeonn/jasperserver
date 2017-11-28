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
 * @version $Id: ReferenceElementTest.java 60548 2016-02-03 14:06:55Z tiefimen $
 * @see
 */
public class ReferenceElementTest {

    public static final String ELEMENT_NAME = "name";
    public static final String REFERENCE_PATH = "ReferencePath";
    ReferenceElement sourceElement;
    ReferenceElement clonedElement;

    @BeforeMethod
    public void setUp() {
        sourceElement = new ReferenceElement()
                .setName(ELEMENT_NAME)
                .setReferencePath(REFERENCE_PATH);
    }

    @Test
    public void testCloningConstructor() {

        clonedElement = new ReferenceElement(sourceElement);

        assertTrue(clonedElement.equals(sourceElement));
        assertFalse(sourceElement == clonedElement);
        assertNotNull(clonedElement.getName());
        assertEquals(ELEMENT_NAME, clonedElement.getName());
        assertNotNull(clonedElement.getReferencePath());
        assertEquals(REFERENCE_PATH, clonedElement.getReferencePath());
    }

}