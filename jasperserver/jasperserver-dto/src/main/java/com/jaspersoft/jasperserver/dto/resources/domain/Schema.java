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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: Schema.java 62044 2016-03-24 14:56:43Z ykovalch $
 */
public class Schema {
    private List<ResourceElement> resources;
    private List<PresentationGroupElement> presentation;

    public Schema(){}
    public Schema (Schema source){
        final List<PresentationGroupElement> sourcePresentation = source.getPresentation();
        if(sourcePresentation != null) presentation = new ArrayList<PresentationGroupElement>(sourcePresentation);
        final List<ResourceElement> sourceResources = source.getResources();
        if (sourceResources != null) resources = new ArrayList<ResourceElement>(sourceResources);
    }
    @XmlElementWrapper(name = "resources")
    @XmlElements({
            @XmlElement(name = "group", type = ResourceGroupElement.class),
            @XmlElement(name = "queryGroup", type = QueryResourceGroupElement.class),
            @XmlElement(name = "element", type = ResourceSingleElement.class),
            @XmlElement(name = "constantsGroup", type = ConstantsResourceGroupElement.class),
            @XmlElement(name = "joinGroup", type = JoinResourceGroupElement.class)
    })
    public List<ResourceElement> getResources() {
        return resources;
    }

    public Schema setResources(List<ResourceElement> resources) {
        this.resources = resources;
        return this;
    }

    @XmlElementWrapper(name="presentation")
    @XmlElement(name="dataIsland")
    public List<PresentationGroupElement> getPresentation() {
        return presentation;
    }

    public Schema setPresentation(List<PresentationGroupElement> presentation) {
        this.presentation = presentation;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schema)) return false;

        Schema schema = (Schema) o;

        if (presentation != null ? !presentation.equals(schema.presentation) : schema.presentation != null)
            return false;
        if (resources != null  && schema.resources != null && !equalGroupElements(resources, schema.resources)) return false;

        return true;
    }

    private boolean equalGroupElements(List<? extends ResourceElement> groupElements1, List<? extends ResourceElement> groupElements2) {
        if (groupElements1.size() != groupElements2.size()) {
            return false;
        }

        if (!(groupElements1.get(0) instanceof AbstractResourceGroupElement)) {
            return groupElements1.containsAll(groupElements2);
        }

        Comparator<ResourceElement> comparator = new Comparator<ResourceElement>() {
            public int compare(ResourceElement elem1, ResourceElement elem2) {
                return elem1.getName().compareTo(elem2.getName());
            }
        };
        Collections.sort(groupElements1, comparator);
        Collections.sort(groupElements2, comparator);
        boolean result = false;
        for (int i = 0; i < groupElements1.size(); i++) {
            final ResourceElement resourceElement1 = groupElements1.get(i);
            final ResourceElement resourceElement2 = groupElements2.get(i);
            if(resourceElement1.getName().equals(resourceElement2.getName())
                    && resourceElement1.getClass() == resourceElement2.getClass()) {
                if(resourceElement1 instanceof AbstractResourceGroupElement) {
                    result = equalGroupElements(((AbstractResourceGroupElement) resourceElement1).getElements(),
                                    ((AbstractResourceGroupElement) resourceElement2).getElements());
                } else if (resourceElement1 instanceof ConstantsResourceGroupElement) {
                    result =  equalGroupElements(((ConstantsResourceGroupElement) resourceElement1).getElements(),
                            ((ConstantsResourceGroupElement) resourceElement2).getElements());
                }
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        int result = resources != null ? sortResourceGroupElement(resources).hashCode() : 0;
        result = 31 * result + (presentation != null ? presentation.hashCode() : 0);
        return result;
    }

    private List<? extends SchemaElement> sortResourceGroupElement(List<? extends SchemaElement> list) {
        Comparator<SchemaElement> comparator = new Comparator<SchemaElement>() {
            public int compare(SchemaElement elem1, SchemaElement elem2) {
                return elem1.getName().compareTo(elem2.getName());
            }
        };
        Collections.sort(list, comparator);

        if (list.get(0) instanceof AbstractResourceGroupElement) {
            List<AbstractResourceGroupElement> castedList = (List<AbstractResourceGroupElement>) list;
            for (AbstractResourceGroupElement element : castedList) {
                sortResourceGroupElement(element.getElements());
            }
        }
        return list;
    }

    @Override
    public String toString() {
        return "Schema{" +
                "resources=" + resources +
                ", presentation=" + presentation +
                '}';
    }
}
