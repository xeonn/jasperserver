package com.jaspersoft.jasperserver.dto.resources.domain;

import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressionContainer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id: JoinResourceGroupElementTest.java 64791 2016-10-12 15:08:37Z ykovalch $
 * @see
 */
public class JoinResourceGroupElementTest {

    public static final ClientExpressionContainer FILTER_EXPRESSION = new ClientExpressionContainer().setString("FilterExpression");
    public static final String SOURCE_NAME = "SourceName";
    public static final String ELEMENT_NAME = "Name";
    JoinResourceGroupElement sourceElement;
    JoinResourceGroupElement clonedElement;

    @Before
    public void setUp() {
        sourceElement = new JoinResourceGroupElement()
                .setJoinInfo(new JoinInfo()
                        .setIncludeAllJoinsForQueryFieldTables(true)
                        .setJoins(new ArrayList<Join>()))
                .setElements(new ArrayList<SchemaElement>())
                .setFilterExpression(FILTER_EXPRESSION)
                .setSourceName(SOURCE_NAME)
                .setName(ELEMENT_NAME);

    }

    @Test
    public void testCloningConstructor() throws Exception {

        clonedElement = new JoinResourceGroupElement(sourceElement);

        assertTrue(clonedElement.equals(sourceElement));
        assertFalse(sourceElement == clonedElement);
        assertFalse(sourceElement.getJoinInfo() == clonedElement.getJoinInfo());
        assertEquals(sourceElement.getJoinInfo(), clonedElement.getJoinInfo());
        assertFalse(sourceElement.getElements() == clonedElement.getElements());
        assertNotNull(clonedElement.getFilterExpression());
        assertEquals(FILTER_EXPRESSION, clonedElement.getFilterExpression());
        assertNotNull(clonedElement.getSourceName());
        assertEquals(SOURCE_NAME, clonedElement.getSourceName());
        assertNotNull(clonedElement.getName());
        assertEquals(ELEMENT_NAME, clonedElement.getName());

    }
}