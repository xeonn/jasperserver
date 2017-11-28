package com.jaspersoft.jasperserver.api.metadata.jasperreports.domain;

/**
 * Created by IntelliJ IDEA.
 * User: ichan
 * Date: 9/16/14
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CustomDomainMetaDataProvider {

    /*
     * This function is used for retrieving the metadata layer of custom data source in form of CustomDomainMetaData
     * CustomDomainMetaData contains information JRFields, query, query language and field name mapping (actual JRField name, name used in domain)
     *
     */
    public CustomDomainMetaData getCustomDomainMetaData(CustomReportDataSource customReportDataSource) throws Exception;
}
