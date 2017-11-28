package com.jaspersoft.jasperserver.export.datasource;

import org.apache.commons.dbcp.BasicDataSource;

import java.util.Properties;

/**
 * User: Andrew Sokolnikov
 * Date: May 22, 2011
 */
public class ConfigurableDataSource extends BasicDataSource {

    public void setExtraProperties(Properties props) {
        connectionProperties.putAll(props);
    }

}
