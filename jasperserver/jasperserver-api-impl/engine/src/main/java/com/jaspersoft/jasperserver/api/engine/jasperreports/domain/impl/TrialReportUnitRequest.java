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
package com.jaspersoft.jasperserver.api.engine.jasperreports.domain.impl;

import java.util.Map;

import com.jaspersoft.jasperserver.api.common.domain.ExecutionContext;
import com.jaspersoft.jasperserver.api.engine.jasperreports.common.ReportExecuter;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.ReportUnit;


/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: TrialReportUnitRequest.java 22172 2012-02-14 13:38:29Z lchirita $
 */
public class TrialReportUnitRequest extends ReportUnitRequestBase
{
	
	private ReportUnit reportUnit;
	
	/**
	 * 
	 */
	public TrialReportUnitRequest(ReportUnit reportUnit, Map reportParameters)
	{
		super(reportParameters);

		this.reportUnit = reportUnit;
	}
	
	/**
	 * 
	 */
	public ReportUnit getReportUnit()
	{
		return reportUnit;
	}

	public ReportUnitResult execute(ExecutionContext context, ReportExecuter executer) {
		return executer.executeTrialReportUnitRequest(context, this);
	}
}
