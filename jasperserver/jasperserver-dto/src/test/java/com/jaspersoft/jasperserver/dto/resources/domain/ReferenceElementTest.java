package com.jaspersoft.jasperserver.dto.resources.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;



/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id: ReferenceElementTest.java 63760 2016-07-05 18:59:28Z agodovan $
 * @see
 */
public class ReferenceElementTest {

    public static final String ELEMENT_NAME = "name";
    public static final String REFERENCE_PATH = "ReferencePath";
    ReferenceElement sourceElement;
    ReferenceElement clonedElement;

    @Before
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