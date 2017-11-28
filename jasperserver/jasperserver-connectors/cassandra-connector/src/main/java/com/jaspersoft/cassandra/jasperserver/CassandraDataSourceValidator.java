package com.jaspersoft.cassandra.jasperserver;

import com.jaspersoft.jasperserver.api.engine.jasperreports.util.CustomDataSourceValidator;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.CustomReportDataSource;
import org.springframework.validation.Errors;

import java.util.Map;

/**
 * 
 * @author Eric Diaz
 * 
 */
public class CassandraDataSourceValidator implements CustomDataSourceValidator {

	@Override
	public void validatePropertyValues(CustomReportDataSource customReportDataSource, Errors errors) {
		Map<?, ?> propertyMap = customReportDataSource.getPropertyMap();
//		if (jdbcURL == null || jdbcURL.length() == 0) {
//			errors.rejectValue("reportDataSource.propertyMap[jdbcURL]", "HiveDataSource.jdbcURL.required");
//		}
	}
}
