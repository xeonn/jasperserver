package com.jaspersoft.jasperserver.api.metadata.jasperreports.domain;

import com.jaspersoft.jasperserver.api.JasperServerAPI;

/**
 * Represents a persistent JasperServer repository data source which corresponds with a
 * Amazon data source which uses JDBC.
 * @author vsabadosh
 */
@JasperServerAPI
public interface AwsReportDataSource extends JdbcReportDataSource {

    String getAWSAccessKey();
    
    String getAWSSecretKey();
    
    String getRoleARN();
    
    String getAWSRegion();

    String getDbName();
    
    String getDbInstanceIdentifier();
    
    String getDbService();

    void setAWSAccessKey(String accessKey);

    void setAWSSecretKey(String secretKey);

    void setRoleARN(String roleARN);

    void setAWSRegion(String region);

    void setDbName(String dbName);
    
    void setDbInstanceIdentifier(String dbInstanceIdentifier);
    
    void setDbService(String dbService);
}
