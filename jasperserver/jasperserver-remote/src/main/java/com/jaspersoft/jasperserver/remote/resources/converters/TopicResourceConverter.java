package com.jaspersoft.jasperserver.remote.resources.converters;

import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.ReportUnit;
import com.jaspersoft.jasperserver.dto.resources.ClientReportUnit;

public class TopicResourceConverter extends GenericReportUnitResourceConverter<ReportUnit, ClientReportUnit> {
    @Override
    public String getClientResourceType() {
        return "topic";
    }

    @Override
    public String getServerResourceType() {
        return "com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.Topic";
    }
}