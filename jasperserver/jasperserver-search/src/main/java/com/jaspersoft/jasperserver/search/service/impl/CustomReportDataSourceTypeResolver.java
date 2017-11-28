package com.jaspersoft.jasperserver.search.service.impl;

import com.jaspersoft.jasperserver.api.metadata.common.domain.Resource;
import com.jaspersoft.jasperserver.api.metadata.common.domain.ResourceLookup;
import com.jaspersoft.jasperserver.api.metadata.common.service.RepositoryService;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.CustomReportDataSource;
import com.jaspersoft.jasperserver.search.service.ResourceTypeResolver;


/**
 * Created by stas on 3/7/14.
 */
public class CustomReportDataSourceTypeResolver implements ResourceTypeResolver {

    @javax.annotation.Resource
    protected RepositoryService repositoryService;

    @Override
    public String getResourceType(Resource resource) {
        if (resource == null) { return null; }

        if (resource instanceof CustomReportDataSource) {
            return getResourceType((CustomReportDataSource) resource);

        } else if (resource.getResourceType().equals(CustomReportDataSource.class.getCanonicalName())) {
            return getResourceType(resource.getURIString());

        } else {
            return resource.getResourceType();
        }
    }

    private String getResourceType(String uri) {
        if (uri == null) { return null; }

        CustomReportDataSource dataSource = (CustomReportDataSource) repositoryService.getResource(null, uri);
        return (dataSource == null) ? null : getResourceType(dataSource);
    }

    protected String getResourceType(CustomReportDataSource dataSource) {
        return dataSource.getServiceClass();
    }

    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }
}
