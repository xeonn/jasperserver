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
package com.jaspersoft.jasperserver.api.security;

import org.owasp.csrfguard.CsrfGuard;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author dlitvak
 * @version $Id: JSSessionFixationProtectionStrategy.java 63866 2016-07-12 22:39:01Z schubar $
 */
public class JSSessionFixationProtectionStrategy extends SessionFixationProtectionStrategy {
    /**
     * Remove any attributes from the map that should not be migrated from unauth. session to auth one.
     *
     * @param session
     * @return
     */
    @Override
    protected Map<String, Object> extractAttributes(HttpSession session) {
        Map<String, Object> attrMap = super.extractAttributes(session);
        attrMap.remove(CsrfGuard.getInstance().getSessionKey());
        return attrMap;
    }
}
