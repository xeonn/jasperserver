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
 * @version $Id: ResourceElementTest.java 60548 2016-02-03 14:06:55Z tiefimen $
 * @see
 */
public class ResourceElementTest {

    public static final String ELEMENT_NAME = "name";
    public static final String SOURCE_NAME = "SourceName";
    ResourceElement<Builder> sourceElement;
    ResourceElement<Builder> clonedElement;

    private static class Builder extends ResourceElement<Builder>{}

    @BeforeMethod
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