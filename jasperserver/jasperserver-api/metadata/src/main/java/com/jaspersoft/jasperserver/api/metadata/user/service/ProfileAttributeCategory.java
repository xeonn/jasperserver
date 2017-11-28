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

import com.jaspersoft.jasperserver.api.metadata.common.service.ResourceFactory;
import com.jaspersoft.jasperserver.api.metadata.user.domain.Tenant;
import com.jaspersoft.jasperserver.api.metadata.user.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Represents a category for profile attribute
 *
 * @author Vlad Zavadskii
 * @version $Id: ProfileAttributeCategory.java 51947 2014-12-11 14:38:38Z ogavavka $
 */
public enum ProfileAttributeCategory {
    USER("User Profile") {
        @Override
        public Object getPrincipal(ResourceFactory resourceFactory) {
            return getCurrentUser();
        }
    },

    TENANT("Tenant Profile") {
        @Override
        public Object getPrincipal(ResourceFactory resourceFactory) {
            User user = getCurrentUser();
            Tenant tenant = null;

            if (user != null && user.getTenantId() != null) {
                tenant = (Tenant) resourceFactory.newObject(Tenant.class);
                tenant.setId(user.getTenantId());
            }

            return tenant;
        }
    },

    SERVER("Server Profile") {
        @Override
        public Object getPrincipal(ResourceFactory resourceFactory) {
            return resourceFactory.newObject(Tenant.class);
        }
    },

    HIERARCHICAL("Entire hierarchy") {
        @Override
        public Object getPrincipal(ResourceFactory resourceFactory) {
            return null;
        }
    };

    ProfileAttributeCategory(String label) {
        this.label = label;
    }

    private String label;

    private static User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null) ? (User) auth.getPrincipal() : null;
    }

    public String getLabel() {
        return label;
    }

    public abstract Object getPrincipal(ResourceFactory resourceFactory);
}
