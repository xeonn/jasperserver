package com.jaspersoft.jasperserver.dto.resources.domain;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id: ConstantsResourceGroupElementTest.java 63760 2016-07-05 18:59:28Z agodovan $
 * @see
 */
public class ConstantsResourceGroupElementTest {

    public static final String SOURCE_NAME = "SourceName";
    public static final String ELEMENT_NAME = "name";
    ConstantsResourceGroupElement sourceElement;
    ConstantsResourceGroupElement clonedElement;

    @Before
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