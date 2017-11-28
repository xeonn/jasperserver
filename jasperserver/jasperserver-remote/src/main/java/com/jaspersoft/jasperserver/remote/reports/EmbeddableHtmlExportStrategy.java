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
package com.jaspersoft.jasperserver.remote.reports;

import com.jaspersoft.jasperserver.remote.services.ExportExecution;
import com.jaspersoft.jasperserver.remote.services.ReportExecution;
import net.sf.jasperreports.engine.ReportContext;
import net.sf.jasperreports.engine.export.AbstractHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import org.springframework.stereotype.Service;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id: EmbeddableHtmlExportStrategy.java 50801 2014-10-29 00:20:56Z inesterenko $
 */
@Service
public class EmbeddableHtmlExportStrategy extends AbstractHtmlExportStrategy {
    @Override
    protected AbstractHtmlExporter prepareExporter(ReportExecution reportExecution, ExportExecution exportExecution, String contextPath) {
        final AbstractHtmlExporter exporter = super.prepareExporter(reportExecution, exportExecution, contextPath);
        exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, "");
        exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, "");
        ReportContext reportContext = reportExecution.getFinalReportUnitResult().getReportContext();
        if (reportContext != null) {
            reportContext.setParameterValue(ReportContext.REQUEST_PARAMETER_APPLICATION_DOMAIN, exportExecution.getOptions().getBaseUrl());
            exporter.setReportContext(reportContext);
        }
        return exporter;
    }
}
