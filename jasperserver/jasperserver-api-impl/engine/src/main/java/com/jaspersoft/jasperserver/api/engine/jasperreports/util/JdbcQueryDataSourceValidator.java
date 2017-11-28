package com.jaspersoft.jasperserver.api.engine.jasperreports.util;


import com.jaspersoft.jasperserver.api.common.domain.impl.ExecutionContextImpl;
import com.jaspersoft.jasperserver.api.metadata.common.domain.ResourceReference;
import com.jaspersoft.jasperserver.api.metadata.common.service.RepositoryService;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.CustomReportDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;

import javax.annotation.Resource;
import java.io.File;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ichan
 * Date: 2/12/14
 * Time: 2:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class JdbcQueryDataSourceValidator implements CustomDataSourceValidator {

    private final static Log log = LogFactory.getLog(XlsDataSourceValidator.class);

    public void validatePropertyValues(CustomReportDataSource ds, Errors errors) {
        String query = null;
        String urlField = null;
        String driver = null;
        Map props = ds.getPropertyMap();

        if (props != null) {
            urlField = (String) ds.getPropertyMap().get("url");
            query = (String) ds.getPropertyMap().get("query");
            driver = (String) ds.getPropertyMap().get("driver");
        }

        if (urlField == null || urlField.length() == 0) {
            reject(errors, "url", "Please enter jdbc url");
        } else if (!urlField.toLowerCase().startsWith("jdbc:")) {
            reject(errors, "url", "Invalid jdbc url");
        }

        if (query == null || query.length() == 0) {
            reject(errors, "query", "Please enter query");
        } else if (!query.toLowerCase().startsWith("select")) {
            reject(errors, "query", "Invalid jdbc query");
        }

        if (driver == null || driver.length() == 0) {
            reject(errors, "driver", "Please enter jdbc driver");
        }
    }

    // first arg is the path of the property which has the error
    // for custom DS's this will always be in the form "reportDataSource.propertyMap[yourPropName]"
    protected void reject(Errors errors, String name, String reason) {
        if (errors != null) errors.rejectValue("jdbcQueryDataSource.propertyMap[" + name + "]",  reason);
        else log.debug("jdbcQueryDataSource.propertyMap[" + name + "] - " + reason);
    }


}
