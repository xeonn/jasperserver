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
import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * <p></p>
 *
 * @author Volodya Sabadosh
 * @version $Id: HypermediaAttribute.java 53247 2015-03-24 12:32:50Z vsabados $
 */
public class HypermediaAttribute extends ClientUserAttribute {
    private HypermediaAttributeEmbeddedContainer embedded;
    private HypermediaAttributeLinks links;

    public HypermediaAttribute(ClientUserAttribute other) {
        super(other);
    }

    public HypermediaAttribute() {
    }

    @XmlElement(name = "_embedded")
    public HypermediaAttributeEmbeddedContainer getEmbedded() {
        return embedded;
    }

    public void setEmbedded(HypermediaAttributeEmbeddedContainer embedded) {
        this.embedded = embedded;
    }

    @XmlElement(name = "_links")
    public HypermediaAttributeLinks getLinks() {
        return links;
    }

    public void setLinks(HypermediaAttributeLinks links) {
        this.links = links;
    }

}
