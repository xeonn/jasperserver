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
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id$
 */
@XmlRootElement(name = "dataIslands")
public class DataIslandsContainer extends SchemaElement<DataIslandsContainer> {
    private List<PresentationGroupElement> dataIslands;

    public DataIslandsContainer(){}
    public DataIslandsContainer(DataIslandsContainer source){
        super(source);
        final List<PresentationGroupElement> sourceDataIslands = source.getDataIslands();
        if(sourceDataIslands != null){
            dataIslands = new ArrayList<PresentationGroupElement>();
            for (PresentationGroupElement dataIsland : sourceDataIslands) {
                dataIslands.add(new PresentationGroupElement(dataIsland));
            }
        }
    }
    @XmlElement(name = "dataIsland")
    public List<PresentationGroupElement> getDataIslands() {
        return dataIslands;
    }

    public DataIslandsContainer setDataIslands(List<PresentationGroupElement> dataIslands) {
        this.dataIslands = dataIslands;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataIslandsContainer)) return false;
        if (!super.equals(o)) return false;

        DataIslandsContainer that = (DataIslandsContainer) o;

        return dataIslands != null ? dataIslands.equals(that.dataIslands) : that.dataIslands == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (dataIslands != null ? dataIslands.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DataIslandsContainer{" +
                "dataIslands=" + dataIslands +
                "} " + super.toString();
    }
}
