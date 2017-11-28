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

package com.jaspersoft.jasperserver.export;


/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: ImportCommand.java 19925 2010-12-11 15:06:41Z tmatyashovsky $
 */
public class ImportCommand extends BaseExportImportCommand {
	
	public static final String DEFAULT_COMMAND_BEAN_NAME = "importCommandBean";
	public static final String METADATA_BEAN_NAME = "importCommandMetadata";
	
	protected ImportCommand() {
		super(DEFAULT_COMMAND_BEAN_NAME, METADATA_BEAN_NAME);
	}
	
	public static void main(String[] args) {
		debugArgs(args);
	
		boolean success = false;
		try {
			success = new ImportCommand().process(args);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace(System.err);
		}
		System.exit(success ? 0 : -1);
	}

}
