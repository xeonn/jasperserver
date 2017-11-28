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
package com.jaspersoft.jasperserver.dto.adhoc.query.el;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Volodya Sabadosh
 * @version $Id: ParameterExpressionsMapXmlAdapter.java 64626 2016-09-26 13:25:24Z vzavadsk $
 */
public class ParameterExpressionsMapXmlAdapter extends XmlAdapter<List<ClientParameterExpression>, Map<String, ClientExpression>> {

    @Override
    public List<ClientParameterExpression> marshal(Map<String, ClientExpression> v) throws Exception {
        List<ClientParameterExpression> result = null;
        if (v != null) {
            result = new ArrayList<ClientParameterExpression>();
            for (String name : v.keySet()) {
                result.add(new ClientParameterExpression(name, v.get(name)));
            }
        }

        return result == null ? null : result;
    }

    @Override
    public Map<String, ClientExpression> unmarshal(List<ClientParameterExpression> v) throws Exception {
        Map<String, ClientExpression> result = null;
        if(v != null) {
            result = new HashMap<String, ClientExpression>();
            for(ClientParameterExpression currentResource : v){
                result.put(currentResource.getName(), currentResource.getExpression());
            }
        }

        return result;
    }
}
