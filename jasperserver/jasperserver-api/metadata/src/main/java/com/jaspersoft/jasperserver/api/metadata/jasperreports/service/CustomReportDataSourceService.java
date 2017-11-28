package com.jaspersoft.jasperserver.api.metadata.jasperreports.service;

import com.jaspersoft.jasperserver.api.JasperServerAPI;
import net.sf.jasperreports.engine.JRException;

/**
 * User: carbiv
 * Date: 3/7/12
 * Time: 8:24 PM
 */
@JasperServerAPI
public interface CustomReportDataSourceService extends ReportDataSourceService {
    boolean testConnection() throws JRException;
}