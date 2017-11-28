/*
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.jaspersoft.jasperserver.dto.authority.hypermedia;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author askorodumov
 * @version $Id: LinkTest.java 59477 2015-12-14 10:19:14Z askorodu $
 */
public class LinkTest {
    @Test
    public void equals_emptyIdentical_success() {
        Link attributeLinks1 = new Link();
        Link attributeLinks2 = new Link();

        assertTrue(attributeLinks1.equals(attributeLinks2));
        assertTrue(attributeLinks2.equals(attributeLinks1));
    }

    @Test
    public void hashCode_emptyIdentical_success() {
        Link attributeLinks1 = new Link();
        Link attributeLinks2 = new Link();

        assertEquals(attributeLinks1.hashCode(), attributeLinks2.hashCode());
    }

    @Test
    public void toString_emptyIdentical_success() {
        Link attributeLinks1 = new Link();
        Link attributeLinks2 = new Link();

        assertEquals(attributeLinks1.toString(), attributeLinks2.toString());
    }

    @Test
    public void equals_identical_success() {
        Link attributeLinks1 = createLink("href1");
        Link attributeLinks2 = new Link(attributeLinks1);

        assertTrue(attributeLinks1.equals(attributeLinks2));
        assertTrue(attributeLinks2.equals(attributeLinks1));
    }

    @Test
    public void hashCode_identical_success() {
        Link attributeLinks1 = createLink("href1");
        Link attributeLinks2 = new Link(attributeLinks1);

        assertEquals(attributeLinks1.hashCode(), attributeLinks2.hashCode());
    }

    @Test
    public void toString_identical_success() {
        Link attributeLinks1 = createLink("href1");
        Link attributeLinks2 = new Link(attributeLinks1);

        assertEquals(attributeLinks1.toString(), attributeLinks2.toString());
    }

    @Test
    public void equals_notIdentical_success() {
        Link attributeLinks1 = createLink("href1");
        Link attributeLinks2 = createLink("href2");
        Link attributeLinks3 = createLink(null);

        assertFalse(attributeLinks1.equals(attributeLinks2));
        assertFalse(attributeLinks2.equals(attributeLinks1));

        assertFalse(attributeLinks1.equals(attributeLinks3));
        assertFalse(attributeLinks3.equals(attributeLinks1));
    }

    @Test
    public void hashCode_notIdentical_success() {
        Link attributeLinks1 = createLink("href1");
        Link attributeLinks2 = createLink("href2");
        Link attributeLinks3 = createLink(null);

        assertNotEquals(attributeLinks1.hashCode(), attributeLinks2.hashCode());
        assertNotEquals(attributeLinks1.hashCode(), attributeLinks3.hashCode());
    }

    @Test
    public void toString_notIdentical_success() {
        Link attributeLinks1 = createLink("href1");
        Link attributeLinks2 = createLink("href2");
        Link attributeLinks3 = createLink(null);

        assertNotEquals(attributeLinks1.toString(), attributeLinks2.toString());
        assertNotEquals(attributeLinks1.toString(), attributeLinks3.toString());
    }

    private Link createLink(String href) {
        Link link = new Link();
        link.setHref(href);
        return link;
    }
}
