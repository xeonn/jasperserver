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
package com.jaspersoft.jasperserver.dto.adhoc.dataset;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Volodya Sabadosh
 * @version $Id: ClientDatasetAllLevelNode.java 62480 2016-04-12 16:20:24Z vspachyn $
 */
public class ClientDatasetAllLevelNode extends AbstractClientDatasetLevelNode<ClientDatasetAllLevelNode, List<String>> {

    private List<String> data;

    public ClientDatasetAllLevelNode() {

    }

    public ClientDatasetAllLevelNode(ClientDatasetAllLevelNode other) {
        if (other.getData() != null) {
            this.data = new ArrayList<String>(data);
        }
    }

    @Override
    @XmlElementWrapper(name = "data")
    @XmlElement(name = "item")
    public List<String> getData() {
        return this.data;
    }

    public ClientDatasetAllLevelNode setData(List<String> data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "ClientDatasetAllLevelNode{} " + super.toString();
    }
}
