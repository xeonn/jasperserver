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

package com.jaspersoft.jasperserver.api.metadata.user.service;

import com.jaspersoft.jasperserver.api.metadata.common.domain.Resource;

/**
 * Resolves profile attributes that used in parametrized repository resources like data sources.
 *
 * @author Volodya Sabadosh
 * @author Vlad Zavadskii
 *
 * @version $Id: ProfileAttributesResolver.java 51908 2014-12-09 23:53:22Z nthapa $
 */
public interface ProfileAttributesResolver {

    /**
     * Substitutes attributes of parametrized resource, by:
     * <ul>
     * <li>serialization to String
     * <li>resolving attributes
     * <li>deserialization back to resource object.
     * </ul>
     *
     * @param resource The parametrized resource
     * @param <T>      resource type
     * @return resource with attribute substitution.
     */
    <T extends Resource> T merge(T resource);

    /**
     * Indicates if provided string contains any <code>attribute</code> placeholder
     *
     * @param str The string to be checked for an attribute
     * @return true if <code>str</code> contains an attribute
     */
    boolean containsAttribute(String str);

    /**
     * Checks whether test resource parametrized ONLY with the profile attributes of given <code>categories</code>.
     * If categories is not specified check whether test resource is parametrized at all.
     *
     * @param resource The parametrized resource
     * @param categories list of profile attributes. If it specified checks whether test resource parametrized ONLY with
     * the attributes of given categories. If categories is not specified check whether test resource is parametrized.
     * @param <T> resource type
     *
     * @return @return true if <code>resource</code> contains an attribute.
     */
    <T extends Resource> boolean isParametrizedResource(T resource, ProfileAttributeCategory... categories);
}