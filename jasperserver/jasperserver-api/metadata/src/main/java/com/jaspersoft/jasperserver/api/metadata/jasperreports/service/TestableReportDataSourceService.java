package com.jaspersoft.jasperserver.api.metadata.jasperreports.service;

import com.jaspersoft.jasperserver.api.JasperServerAPI;

/**
 * User: carbiv
 * Date: 3/6/12
 * Time: 12:14 PM
 */
@JasperServerAPI
public interface TestableReportDataSourceService extends ReportDataSourceService {
    boolean testConnection();
}
