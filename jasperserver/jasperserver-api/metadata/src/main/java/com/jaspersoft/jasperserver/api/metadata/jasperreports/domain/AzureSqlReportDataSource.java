package com.jaspersoft.jasperserver.api.metadata.jasperreports.domain;

import com.jaspersoft.jasperserver.api.JasperServerAPI;
import com.jaspersoft.jasperserver.api.metadata.common.domain.ResourceReference;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.client.JdbcReportDataSourceImpl;

@JasperServerAPI
public interface AzureSqlReportDataSource extends JdbcReportDataSource {

    public String getSubscriptionId();

    public void setSubscriptionId(String subscriptionId);

    public ResourceReference getKeyStoreResource();

    public void setKeyStoreResource(ResourceReference keyStoreResource);

    public String getKeyStorePassword();

    public void setKeyStorePassword(String keyStorePassword);

    public String getKeyStoreType();

    public void setKeyStoreType(String keyStoreType);

    public String getServerName();

    public void setServerName(String serverName);

    public String getDbName();

    public void setDbName(String dbName);
}

