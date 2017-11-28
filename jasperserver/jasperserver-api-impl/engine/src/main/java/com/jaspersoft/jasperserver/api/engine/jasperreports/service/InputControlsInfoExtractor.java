package com.jaspersoft.jasperserver.api.engine.jasperreports.service;

import com.jaspersoft.jasperserver.api.common.domain.ExecutionContext;
import com.jaspersoft.jasperserver.api.engine.common.service.ReportInputControlsInformation;
import com.jaspersoft.jasperserver.api.metadata.common.domain.Resource;
import net.sf.jasperreports.engine.JasperReport;

import java.util.Map;

public interface InputControlsInfoExtractor {
    ReportInputControlsInformation extractInputControlsInfo(ExecutionContext context, Resource resource, Map initialParameters);
    JasperReport getJasperReport(ExecutionContext context, Resource resource);
}
