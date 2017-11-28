package com.jaspersoft.jasperserver.dto.resources.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id: PresentationElementTest.java 63760 2016-07-05 18:59:28Z agodovan $
 * @see
 */
public class PresentationElementTest {


    public static final String ELEMENT_NAME = "name";
    public static final String RESOURCE_PATH = "ResourcePath";
    public static final String LABEL = "Label";
    public static final String LABEL_ID = "LabelId";
    public static final String DESCRIPTION = "Description";
    public static final String DESCRIPTION_ID = "DescriptionId";

    PresentationElement sourceElement;
    PresentationElement clonedElement;

    private static class Builder extends PresentationElement<Builder>{}

    @Before
    public void setUp() {
        sourceElement = new Builder()
                .setName(ELEMENT_NAME)
                .setLabel(LABEL)
                .setLabelId(LABEL_ID)
                .setDescription(DESCRIPTION)
                .setDescriptionId(DESCRIPTION_ID);
    }

    @Test
    public void testCloningConstructor() {

        clonedElement = new PresentationElement(sourceElement);

        assertTrue(clonedElement.equals(sourceElement));
        assertFalse(sourceElement == clonedElement);
        assertNotNull(clonedElement.getName());
        assertEquals(ELEMENT_NAME, clonedElement.getName());
        assertNotNull(clonedElement.getLabel());
        assertEquals(LABEL, clonedElement.getLabel());
        assertNotNull(clonedElement.getLabelId());
        assertEquals(LABEL_ID, clonedElement.getLabelId());
        assertNotNull(clonedElement.getDescription());
        assertEquals(DESCRIPTION, clonedElement.getDescription());
        assertNotNull(clonedElement.getDescriptionId());
        assertEquals(DESCRIPTION_ID, clonedElement.getDescriptionId());
    }


}