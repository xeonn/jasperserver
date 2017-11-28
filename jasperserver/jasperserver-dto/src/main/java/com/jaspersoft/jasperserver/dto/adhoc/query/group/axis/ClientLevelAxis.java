/*
 * Copyright (C) 2005 - 2016 TIBCO Software Inc. All rights reserved.
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
package com.jaspersoft.jasperserver.dto.adhoc.query.group.axis;

import com.jaspersoft.jasperserver.dto.adhoc.query.expansion.ClientExpandable;
import com.jaspersoft.jasperserver.dto.adhoc.query.expansion.ClientLevelExpansion;
import com.jaspersoft.jasperserver.dto.adhoc.query.expansion.ClientMemberExpansion;
import com.jaspersoft.jasperserver.dto.adhoc.query.field.ClientQueryLevel;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Andriy Godovanets
 */
@XmlRootElement(name = "axis")
public class ClientLevelAxis extends ClientBaseAxis<ClientQueryLevel> {
    @Valid
    private List<ClientExpandable> expansions;

    public ClientLevelAxis() {
        super();
    }

    public ClientLevelAxis(ClientLevelAxis axis) {
        this();
        if (axis != null) {
            if (axis.getItems() != null) {
                for (ClientQueryLevel level : axis.getItems()) {
                    this.getItems().add(new ClientQueryLevel(level));
                }
            }
            if (axis.getExpansions() != null) {
                this.expansions = new ArrayList<ClientExpandable>();

                for (ClientExpandable expandable : axis.getExpansions()) {
                    this.expansions.add(new ClientLevelExpansion((ClientLevelExpansion) expandable));
                }
            }
        }

    }

    public ClientLevelAxis(Collection<? extends ClientQueryLevel> c) {
        super(c);
    }

    public ClientLevelAxis(Collection<? extends ClientQueryLevel> c, List<ClientExpandable> expansions) {
        super(c);
        setExpansions(expansions);
    }

    @XmlElementWrapper
    @XmlElements({
            @XmlElement(name = "member", type = ClientMemberExpansion.class),
            @XmlElement(name = "level", type = ClientLevelExpansion.class)
    })
    public List<ClientExpandable> getExpansions() {
        return expansions;
    }

    public ClientLevelAxis setExpansions(List<ClientExpandable> expansions) {
        this.expansions = expansions;
        return this;
    }

    @Valid
    @XmlElementWrapper
    @XmlElements({
            @XmlElement(name = "level", type = ClientQueryLevel.class),
            @XmlElement(name = "allLevel", type = ClientQueryLevel.ClientAllLevel.class),
            @XmlElement(name = "aggregations", type = ClientQueryLevel.ClientLevelAggregationsRef.class),
    })
    @Override
    public List<ClientQueryLevel> getItems() {
        return super.getItems();
    }

    @Override
    public ClientBaseAxis setItems(List<ClientQueryLevel> items) {
        return super.setItems(items);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ClientLevelAxis{");
        sb.append("items=").append(getItems());
        sb.append("expansions=").append(expansions);
        sb.append('}');
        return sb.toString();
    }
}
