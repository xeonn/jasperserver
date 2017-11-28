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
 * @version $Id: QueryResourceGroupElementTest.java 64791 2016-10-12 15:08:37Z ykovalch $
 * @see
 */
public class QueryResourceGroupElementTest {

    public static final String ELEMENT_NAME = "name";
    public static final ClientExpressionContainer FILTER_EXPRESSION = new ClientExpressionContainer().setString("FilterExpression");
    public static final String QUERY = "query";
    public static final String SOURCE_NAME = "SourceName";
    QueryResourceGroupElement sourceElement;
    QueryResourceGroupElement clonedElement;

    @Before
    public void setUp() {
        sourceElement = new QueryResourceGroupElement()
                .setName(ELEMENT_NAME)
                .setElements(new ArrayList<SchemaElement>())
                .setFilterExpression(FILTER_EXPRESSION)
                .setQuery(QUERY)
                .setSourceName(SOURCE_NAME);

    }

    @Test
    public void testCloningConstructor() {

        clonedElement = new QueryResourceGroupElement(sourceElement);

        assertTrue(clonedElement.equals(sourceElement));
        assertFalse(sourceElement == clonedElement);
        assertFalse(sourceElement.getElements() == clonedElement.getElements());
        assertNotNull(clonedElement.getName());
        assertEquals(ELEMENT_NAME, clonedElement.getName());
        assertNotNull(clonedElement.getFilterExpression());
        assertEquals(FILTER_EXPRESSION, clonedElement.getFilterExpression());
        assertNotNull(clonedElement.getQuery());
        assertEquals(QUERY, clonedElement.getQuery());
        assertNotNull(clonedElement.getSourceName());
        assertEquals(SOURCE_NAME, clonedElement.getSourceName());

    }
}