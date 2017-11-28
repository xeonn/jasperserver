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

import java.util.List;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: DomainSchemaHelper.java 62168 2016-03-29 16:15:34Z ykovalch $
 */
public class DomainSchemaHelper {
    public static <T extends SchemaElement> T findResourceElement(String resourcePath, List<ResourceElement> resources){
        SchemaElement result = null;
        List<? extends SchemaElement> currentElements = resources;
        if(resources != null && resourcePath != null){
            final String[] pathTokens = resourcePath.split("\\.");
            for(int i = 0; i < pathTokens.length; i++){
                String name = pathTokens[i];
                SchemaElement currentResult = null;
                for(SchemaElement element : currentElements){
                    if(name.equals(element.getName())){
                        currentResult = element;
                        break;
                    }
                }
                if(i == pathTokens.length - 1){
                    result = currentResult;
                } else if(currentResult instanceof AbstractResourceGroupElement){
                    currentElements = ((AbstractResourceGroupElement) currentResult).getElements();
                } else if(currentResult instanceof ConstantsResourceGroupElement){
                    currentElements = ((ConstantsResourceGroupElement) currentResult).getElements();
                } else if(currentResult instanceof ReferenceElement){
                    SchemaElement referenced = findResourceElement(((ReferenceElement) currentResult).getReferencePath(), resources);
                    if(referenced != null && referenced instanceof AbstractResourceGroupElement){
                        currentElements = ((AbstractResourceGroupElement) referenced).getElements();
                    }
                } else {
                    // not found
                    break;
                }
            }
        }
        return (T)result;
    }
}
