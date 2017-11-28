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

import com.jaspersoft.jasperserver.dto.resources.ClientResource;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: ClientDomainSchema.java 62441 2016-04-11 14:52:39Z ykovalch $
 */
@XmlRootElement(name = "domainSchema")
public class ClientDomainSchema extends ClientResource<ClientDomainSchema> {
    private final Schema schema;

    public ClientDomainSchema(){
        schema = new Schema();
    }
    public ClientDomainSchema(Schema schema){
        this.schema = schema != null ? new Schema(schema) : new Schema();
    }
    public ClientDomainSchema(ClientDomainSchema source){
        super(source);
        this.schema = source.getSchema() != null ? new Schema(source.getSchema()) : new Schema();
    }

    @XmlTransient
    public Schema getSchema() {
        return schema;
    }

    public ClientDomainSchema setSchema(Schema schema){
        this.schema.setPresentation(schema.getPresentation());
        this.schema.setResources(schema.getResources());
        return this;
    }

    @XmlElementWrapper(name = "resources")
    @XmlElements({
            @XmlElement(name="group", type = ResourceGroupElement.class),
            @XmlElement(name="queryGroup", type = QueryResourceGroupElement.class),
            @XmlElement(name="element", type = ResourceSingleElement.class),
            @XmlElement(name = "constantsGroup", type = ConstantsResourceGroupElement.class),
            @XmlElement(name="joinGroup", type = JoinResourceGroupElement.class)
    })
    public List<ResourceElement> getResources() {
        return schema.getResources();
    }

    public ClientDomainSchema setResources(List<ResourceElement> resources) {
        schema.setResources(resources);
        return this;
    }

    @XmlElementWrapper(name="presentation")
    @XmlElement(name="dataIsland")
    public List<PresentationGroupElement> getPresentation() {
        return schema.getPresentation();
    }

    public ClientDomainSchema setPresentation(List<PresentationGroupElement> presentation) {
        schema.setPresentation(presentation);
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientDomainSchema)) return false;
        if (!super.equals(o)) return false;

        ClientDomainSchema that = (ClientDomainSchema) o;

        if (schema != null ? !schema.equals(that.schema) : that.schema != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (schema != null ? schema.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClientSchema{" +
                "schema=" + schema +
                "} " + super.toString();
    }
}
