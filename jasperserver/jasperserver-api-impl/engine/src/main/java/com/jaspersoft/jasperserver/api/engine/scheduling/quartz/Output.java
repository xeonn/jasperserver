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

package com.jaspersoft.jasperserver.api.engine.scheduling.quartz;

import java.io.Serializable;
import java.util.Locale;

import net.sf.jasperreports.engine.JRPropertiesHolder;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHyperlinkProducerFactory;

import org.quartz.JobExecutionException;

import com.jaspersoft.jasperserver.api.common.domain.ExecutionContext;
import com.jaspersoft.jasperserver.api.engine.common.service.EngineService;
import com.jaspersoft.jasperserver.api.metadata.common.domain.DataContainer;
import com.jaspersoft.jasperserver.api.metadata.common.service.RepositoryService;

/**
 * @author sanda zaharia (shertage@users.sourceforge.net)
 * @version $Id: Output.java 44903 2014-04-22 12:24:57Z tdanciu $
 */
public interface Output extends Serializable 
{
	public Boolean isIgnorePagination();
	
	public boolean isCompress();

	public ReportOutput getOutput(
			EngineService engineService, 
			ExecutionContext executionContext, 
			String reportUnitURI, 
			DataContainer dataContainer,
			JRHyperlinkProducerFactory hyperlinkProducerFactory,
			RepositoryService repositoryService,
			JasperPrint jasperPrint, 
			String baseFilename,
			Locale locale,
			String characterEncoding) throws JobExecutionException;

	public Boolean isPaginationPreferred(JRPropertiesHolder propertiesHolder);
}
