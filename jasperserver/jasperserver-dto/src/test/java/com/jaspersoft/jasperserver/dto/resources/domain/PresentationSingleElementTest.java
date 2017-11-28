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
 * @version $Id: PresentationSingleElementTest.java 60548 2016-02-03 14:06:55Z tiefimen $
 * @see
 */
public class PresentationSingleElementTest {

    public static final String ELEMENT_NAME = "Name";
    public static final String DESCRIPTION = "Description";
    public static final String DESCRIPTION_ID = "DescriptionId";
    public static final String MASK = "Mask";
    public static final String LABEL = "Label";
    public static final String LABEL_ID = "LabelId";
    public static final String RESOURCE_PATH = "ResourcePath";
    public static final String AGGREGATION = "Aggregation";

    PresentationSingleElement sourceElement;
    PresentationSingleElement clonedElement;

    @BeforeMethod
    public void setUp() {
        sourceElement = new PresentationSingleElement()
                .setName(ELEMENT_NAME)
                .setDescription(DESCRIPTION)
                .setDescriptionId(DESCRIPTION_ID)
                .setKind(PresentationSingleElement.Kind.dimension)
                .setMask(MASK)
                .setLabel(LABEL)
                .setLabelId(LABEL_ID)
                .setResourcePath(RESOURCE_PATH)
                .setAggregation(AGGREGATION);

    }

    @Test
    public void testCloningConstructor() {

        clonedElement = new PresentationSingleElement(sourceElement);

        assertTrue(clonedElement.equals(sourceElement));
        assertFalse(sourceElement == clonedElement);
        assertNotNull(clonedElement.getName());
        assertEquals(ELEMENT_NAME, clonedElement.getName());
        assertNotNull(clonedElement.getDescription());
        assertEquals(DESCRIPTION, clonedElement.getDescription());
        assertNotNull(clonedElement.getDescriptionId());
        assertEquals(DESCRIPTION_ID, clonedElement.getDescriptionId());
        assertNotNull(clonedElement.getKind());
        assertEquals(PresentationSingleElement.Kind.dimension, clonedElement.getKind());
        assertNotNull(clonedElement.getMask());
        assertEquals(MASK, clonedElement.getMask());
        assertNotNull(clonedElement.getLabel());
        assertEquals(LABEL, clonedElement.getLabel());
        assertNotNull(clonedElement.getLabelId());
        assertEquals(LABEL_ID, clonedElement.getLabelId());
        assertNotNull(clonedElement.getResourcePath());
        assertEquals(RESOURCE_PATH, clonedElement.getResourcePath());
        assertNotNull(clonedElement.getAggregation());
        assertEquals(AGGREGATION, clonedElement.getAggregation());

    }

}