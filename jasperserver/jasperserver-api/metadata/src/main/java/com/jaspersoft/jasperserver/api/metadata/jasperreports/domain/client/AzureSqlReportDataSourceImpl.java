package com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.client;

import com.jaspersoft.jasperserver.api.JasperServerAPI;
import com.jaspersoft.jasperserver.api.metadata.common.domain.ResourceReference;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.AzureSqlReportDataSource;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.JdbcReportDataSource;

public class AzureSqlReportDataSourceImpl extends JdbcReportDataSourceImpl implements AzureSqlReportDataSource {

    private static final long serialVersionUID = -3561229455787471028L;

    private String subscriptionId;
    private ResourceReference keyStoreResource;
    private String keyStoreType;
    private String keyStorePassword;
    private String serverName;
    private String dbName;

    public AzureSqlReportDataSourceImpl() {
        super();
    }

    protected Class getImplementingItf() {
        return AzureSqlReportDataSource.class;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public ResourceReference getKeyStoreResource() {
        return keyStoreResource;
    }

    public void setKeyStoreResource(ResourceReference keyStoreResource) {
        this.keyStoreResource = keyStoreResource;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public String getKeyStoreType() {
        return keyStoreType;
    }

    public void setKeyStoreType(String keyStoreType) {
        this.keyStoreType = keyStoreType;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {this.dbName = dbName; }

}
