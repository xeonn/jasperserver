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
 * @version $Id: PresentationGroupElementTest.java 63760 2016-07-05 18:59:28Z agodovan $
 * @see
 */
public class PresentationGroupElementTest {

    public static final String ELEMENT_NAME = "Name";
    public static final String DESCRIPTION = "Description";
    public static final String DESCRIPTION_ID = "DescriptionId";
    public static final String LABEL = "Label";
    public static final String LABEL_ID = "LabelId";
    public static final String RESOURCE_PATH = "ResourcePath";
    PresentationGroupElement sourceElement;
    PresentationGroupElement clonedElement;

    @Before
    public void setUp() {
        sourceElement = new PresentationGroupElement()
                .setName(ELEMENT_NAME)
                .setDescription(DESCRIPTION)
                .setDescriptionId(DESCRIPTION_ID)
                .setLabel(LABEL)
                .setLabelId(LABEL_ID)
                .setElements(new ArrayList<PresentationElement>());

    }

    @Test
    public void testCloningConstructor() {

        clonedElement = new PresentationGroupElement(sourceElement);

        assertTrue(clonedElement.equals(sourceElement));
        assertFalse(sourceElement == clonedElement);
        assertFalse(sourceElement.getElements() == clonedElement.getElements());
        assertNotNull(clonedElement.getName());
        assertEquals(ELEMENT_NAME, clonedElement.getName());
        assertNotNull(clonedElement.getDescription());
        assertEquals(DESCRIPTION, clonedElement.getDescription());
        assertNotNull(clonedElement.getDescriptionId());
        assertEquals(DESCRIPTION_ID, clonedElement.getDescriptionId());
        assertNotNull(clonedElement.getLabel());
        assertEquals(LABEL, clonedElement.getLabel());
        assertNotNull(clonedElement.getLabelId());
        assertEquals(LABEL_ID, clonedElement.getLabelId());


    }

}