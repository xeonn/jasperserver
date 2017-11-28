/*
* Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
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
* along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
*/
package com.jaspersoft.jasperserver.dto.resources.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: ConstantsResourceGroupElement.java 61746 2016-03-14 13:23:04Z tiefimen $
 */
public class ConstantsResourceGroupElement extends ResourceElement<ConstantsResourceGroupElement>{
    private List<ResourceSingleElement> elements;

    public ConstantsResourceGroupElement(){}
    public ConstantsResourceGroupElement(ConstantsResourceGroupElement source){
        super(source);
        final List<ResourceSingleElement> sourceElements = source.getElements();
        if(sourceElements != null){
            elements = new ArrayList<ResourceSingleElement>(sourceElements.size());
            for (ResourceSingleElement sourceElement : sourceElements) {
                elements.add(sourceElement.deepClone());
            }
        }
    }

    public List<ResourceSingleElement> getElements() {
        return elements;
    }

    public ConstantsResourceGroupElement setElements(List<ResourceSingleElement> elements) {
        this.elements = elements;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConstantsResourceGroupElement)) return false;
        if (!super.equals(o)) return false;

        ConstantsResourceGroupElement that = (ConstantsResourceGroupElement) o;

        if (elements != null ? !elements.equals(that.elements) : that.elements != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (elements != null ? elements.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ConstantsResourceGroupElement{" +
                "elements=" + elements +
                "} " + super.toString();
    }
}
