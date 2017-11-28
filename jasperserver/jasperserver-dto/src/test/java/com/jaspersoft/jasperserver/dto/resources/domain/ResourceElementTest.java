package com.jaspersoft.jasperserver.dto.resources.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id: ResourceElementTest.java 63760 2016-07-05 18:59:28Z agodovan $
 * @see
 */
public class ResourceElementTest {

    public static final String ELEMENT_NAME = "name";
    public static final String SOURCE_NAME = "SourceName";
    ResourceElement<Builder> sourceElement;
    ResourceElement<Builder> clonedElement;

    private static class Builder extends ResourceElement<Builder>{}

    @Before
    public void setUp() {

        sourceElement = new Builder()
                .setName(ELEMENT_NAME)
                .setSourceName(SOURCE_NAME);
    }

    @Test
    public void testCloningConstructor() {

        clonedElement = new ResourceElement<Builder>(sourceElement);

        assertTrue(clonedElement.equals(sourceElement));
        assertFalse(sourceElement == clonedElement);
        assertNotNull(clonedElement.getName());
        assertEquals(ELEMENT_NAME, clonedElement.getName());
        assertNotNull(clonedElement.getSourceName());
        assertEquals(SOURCE_NAME, clonedElement.getSourceName());
    }


}