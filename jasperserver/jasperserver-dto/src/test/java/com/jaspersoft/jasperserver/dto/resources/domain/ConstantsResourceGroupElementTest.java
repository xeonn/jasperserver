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
 * @version $Id: ConstantsResourceGroupElementTest.java 60548 2016-02-03 14:06:55Z tiefimen $
 * @see
 */
public class ConstantsResourceGroupElementTest {

    public static final String SOURCE_NAME = "SourceName";
    public static final String ELEMENT_NAME = "name";
    ConstantsResourceGroupElement sourceElement;
    ConstantsResourceGroupElement clonedElement;

    @BeforeMethod
    public void setUp() {

        sourceElement = new ConstantsResourceGroupElement()
                .setSourceName(SOURCE_NAME)
                .setName(ELEMENT_NAME)
                .setElements(new ArrayList<ResourceSingleElement>());
    }

    @Test
    public void testCloningConstructor() {

        clonedElement = new ConstantsResourceGroupElement(sourceElement);

        assertTrue(clonedElement.equals(sourceElement));
        assertFalse(sourceElement == clonedElement);
        assertFalse(sourceElement.getElements() == clonedElement.getElements());
        assertNotNull(clonedElement.getName());
        assertEquals(ELEMENT_NAME, clonedElement.getName());
        assertNotNull(clonedElement.getSourceName());
        assertEquals(SOURCE_NAME, clonedElement.getSourceName());

    }
}