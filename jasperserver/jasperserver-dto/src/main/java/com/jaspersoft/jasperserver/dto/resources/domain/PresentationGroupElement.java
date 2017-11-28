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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: PresentationGroupElement.java 63380 2016-05-26 20:56:46Z mchan $
 */
@XmlRootElement(name = "group")
public class PresentationGroupElement extends PresentationElement<PresentationGroupElement> {
    private List<PresentationElement> elements;

    public PresentationGroupElement(){}

    public PresentationGroupElement(PresentationGroupElement source){
        super(source);
        final List<PresentationElement> sourceElements = source.getElements();
        if(sourceElements != null){
            elements = new ArrayList<PresentationElement>(sourceElements.size());
            for (PresentationElement<? extends PresentationElement> sourceElement : sourceElements) {
                elements.add(sourceElement.deepClone());


            }
        }
    }
    @XmlElementWrapper(name = "elements")
    @XmlElements({
            @XmlElement(name="group", type = PresentationGroupElement.class),
            @XmlElement(name="element", type = PresentationSingleElement.class)
    })
    public List<PresentationElement> getElements() {
        return elements;
    }

    public PresentationGroupElement setElements(List<PresentationElement> elements) {
        this.elements = elements;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PresentationGroupElement)) return false;
        if (!super.equals(o)) return false;

        PresentationGroupElement that = (PresentationGroupElement) o;

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
        return "PresentationGroupElement{" +
                "elements=" + elements +
                "} " + super.toString();
    }
}
