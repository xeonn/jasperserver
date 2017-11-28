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

import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRPropertiesHolder;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRHyperlinkProducerFactory;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionException;

import com.jaspersoft.jasperserver.api.JSExceptionWrapper;
import com.jaspersoft.jasperserver.api.common.domain.ExecutionContext;
import com.jaspersoft.jasperserver.api.engine.common.service.EngineService;
import com.jaspersoft.jasperserver.api.engine.jasperreports.common.DocxExportParametersBean;
import com.jaspersoft.jasperserver.api.engine.jasperreports.common.OdsExportParametersBean;
import com.jaspersoft.jasperserver.api.metadata.common.domain.ContentResource;
import com.jaspersoft.jasperserver.api.metadata.common.domain.DataContainer;
import com.jaspersoft.jasperserver.api.metadata.common.service.RepositoryService;


/**
 * @author sanda zaharia (shertage@users.sourceforge.net)
 * @version $Id: OdsReportOutput.java 44903 2014-04-22 12:24:57Z tdanciu $
 */
public class OdsReportOutput extends AbstractReportOutput 
{

	private static final Log log = LogFactory.getLog(OdsReportOutput.class);

	private OdsExportParametersBean exportParams;

	public OdsReportOutput()
	{
	}

	/** 
	 * @see com.jaspersoft.jasperserver.api.engine.scheduling.quartz.Output#getOutput()
	 */
	public ReportOutput getOutput(
			EngineService engineService, 
			ExecutionContext executionContext, 
			String reportUnitURI, 
			DataContainer odsData,
			JRHyperlinkProducerFactory hyperlinkProducerFactory,
			RepositoryService repositoryService,
			JasperPrint jasperPrint, 
			String baseFilename,
			Locale locale,
			String characterEncoding) throws JobExecutionException
	{
		try {
			JROdsExporter exporter = new JROdsExporter(getJasperReportsContext());
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			
			boolean close = false;
			OutputStream odsDataOut = odsData.getOutputStream();
			try {
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, odsDataOut);
				exporter.setParameter(JExcelApiExporterParameter.CREATE_CUSTOM_PALETTE, Boolean.TRUE);

				if(exportParams != null)
				{
					if(exportParams.getOnePagePerSheet() != null);
						exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, exportParams.getOnePagePerSheet());
					if(exportParams.getDetectCellType() != null);
						exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, exportParams.getDetectCellType());
					if(exportParams.getRemoveEmptySpaceBetweenRows() != null);
						exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, exportParams.getRemoveEmptySpaceBetweenRows());
					if(exportParams.getRemoveEmptySpaceBetweenColumns() != null);
						exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, exportParams.getRemoveEmptySpaceBetweenColumns());
					if(exportParams.getWhitePageBackground() != null);
						exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, exportParams.getWhitePageBackground());
					if(exportParams.getIgnoreGraphics() != null);
						exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, exportParams.getIgnoreGraphics());
					if(exportParams.getCollapseRowSpan() != null);
						exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, exportParams.getCollapseRowSpan());
					if(exportParams.getIgnoreCellBorder() != null);
						exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, exportParams.getIgnoreCellBorder());
					if(exportParams.getFontSizeFixEnabled() != null);
						exporter.setParameter(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED, exportParams.getFontSizeFixEnabled());
					if(exportParams.getMaximumRowsPerSheet() != null);
						exporter.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, exportParams.getMaximumRowsPerSheet());
					if(exportParams.getXlsFormatPatternsMap() != null && !exportParams.getXlsFormatPatternsMap().isEmpty());
						exporter.setParameter(JRXlsExporterParameter.FORMAT_PATTERNS_MAP, exportParams.getXlsFormatPatternsMap());
				}	
				exporter.exportReport();

				close = false;
				odsDataOut.close();
				
				String filename = baseFilename + ".ods";
				return new ReportOutput(odsData, ContentResource.TYPE_ODS, filename);
			} catch (IOException e) {
				throw new JSExceptionWrapper(e);
			} finally {
				if (close) {
					try {
						odsDataOut.close();
					} catch (IOException e) {
						log.error("Error closing stream", e);
					}
				}
			}
		} catch (JRException e) {
			throw new JSExceptionWrapper(e);
		}
	}

	/**
	 * @return Returns the exportParams.
	 */
	public OdsExportParametersBean getExportParams() {
		return exportParams;
	}

	/**
	 * @param exportParams The exportParams to set.
	 */
	public void setExportParams(OdsExportParametersBean exportParams) {
		this.exportParams = exportParams;
	}
	
	@Override
	public Boolean isPaginationPreferred(JRPropertiesHolder propertiesHolder){
		Boolean isPaginationPreferred = super.isPaginationPreferred(propertiesHolder);
		if (isPaginationPreferred == null)
		{
			if (propertiesHolder != null)
			{
				isPaginationPreferred = JRPropertiesUtil.getInstance(getJasperReportsContext()).getBooleanProperty(propertiesHolder.getPropertiesMap(), OdsExportParametersBean.PROPERTY_XLS_PAGINATED);
			}
		}
		return isPaginationPreferred;
	}
}
