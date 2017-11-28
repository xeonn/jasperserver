/*
 * Copyright (C) 2005 - 2011 Jaspersoft Corporation. All rights reserved.
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
package com.jaspersoft.jasperserver.api.engine.jasperreports.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JarFileEntry.java 19922 2010-12-11 14:59:51Z tmatyashovsky $
 */
public class JarFileEntry {
	private final JarFile jar;
	private final JarEntry jarEntry;
	
	protected JarFileEntry(JarFile jar, JarEntry jarEntry) {
		this.jar = jar;
		this.jarEntry = jarEntry;
	}
	
	public long getSize() {
		return jarEntry.getSize();
	}
	
	public long getTime() {
		return jarEntry.getTime();
	}
	
	public InputStream getInputStream() throws IOException {
		return jar.getInputStream(jarEntry);
	}
	
	public JarFile getJarFile() {
		return jar;
	}
	
	public JarEntry getEntry() {
		return jarEntry;
	}
}
