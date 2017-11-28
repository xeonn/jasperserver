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

package com.jaspersoft.jasperserver.api.metadata.user.service.impl;

import com.jaspersoft.jasperserver.api.metadata.common.domain.Folder;
import com.jaspersoft.jasperserver.api.metadata.common.domain.InternalURI;
import com.jaspersoft.jasperserver.api.metadata.common.domain.Resource;

import java.io.Serializable;

/**
 * @author swood
 *
 */
public class InternalURIDefinition implements InternalURI {
	
	private String uri;
	private String folderUri;
	
	public InternalURIDefinition(String uri) {
        this.uri = uri;
		
		if (uri == null || uri.length() == 0) {
			this.folderUri = null;
		} else {
            if (uri.startsWith(getProtocol().concat(":"))) {
                this.uri=uri.substring(getProtocol().length()+1);
            }

            int lastSeparator = this.uri.lastIndexOf(Folder.SEPARATOR);

			if (lastSeparator <= 0) {
				this.folderUri = null;
			} else {
				this.folderUri = this.uri.substring(0, lastSeparator);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.jaspersoft.jasperserver.api.metadata.common.domain.InternalURI#getParentPath()
	 */
	public String getParentPath() {
        if (getParentFolder()==null && uri.lastIndexOf(Folder.SEPARATOR)==0 && uri.length()>1) {
            return Folder.SEPARATOR;
        }
		return getParentFolder() == null ? null : getParentFolder();
	}

	/* (non-Javadoc)
	 * @see com.jaspersoft.jasperserver.api.metadata.common.domain.InternalURI#getParentURI()
	 */
	public String getParentURI() {
		return getParentPath() == null ? null : getProtocol() + ":" + getParentPath();
	}

	public String getParentFolder() {
		return folderUri;
	}

	/* (non-Javadoc)
	 * @see com.jaspersoft.jasperserver.api.metadata.common.domain.InternalURI#getPath()
	 */
	public String getPath() {
		return uri;
	}

	/* (non-Javadoc)
	 * @see com.jaspersoft.jasperserver.api.metadata.common.domain.InternalURI#getProtocol()
	 */
	public String getProtocol() {
		return Resource.URI_PROTOCOL;
	}

	/* (non-Javadoc)
	 * @see com.jaspersoft.jasperserver.api.metadata.common.domain.InternalURI#getURI()
	 */
	public String getURI() {
		return getProtocol() + ":" + getPath();
	}

    @Override
    public Serializable getIdentifier() {
        return getURI();
    }

    @Override
    public String getType() {
        return Folder.class.getName();
    }

    @Override
    public String toString() {
        return getURI();
    }
}
