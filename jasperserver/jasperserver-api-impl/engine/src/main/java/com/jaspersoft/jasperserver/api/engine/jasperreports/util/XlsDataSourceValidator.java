package com.jaspersoft.jasperserver.api.engine.jasperreports.util;


import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.CustomReportDataSource;
import org.springframework.validation.Errors;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: ichan
 * Date: 2/12/14
 * Time: 2:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class XlsDataSourceValidator implements CustomDataSourceValidator {

	public void validatePropertyValues(CustomReportDataSource ds, Errors errors) {
		String filePath = null;
        String useFirstRowAsHeader = null;

		Map props = ds.getPropertyMap();
		if (props != null) {
			filePath = (String) ds.getPropertyMap().get("fileName");
            useFirstRowAsHeader = (String) ds.getPropertyMap().get("useFirstRowAsHeader");
		}
		if (filePath == null || filePath.length() == 0) {
			reject(errors, "fileName", "Please enter file path");
		} else {
            if (filePath.toLowerCase().startsWith("repo:/")) {
                // organization token is optional in regexp below because organization is null for superuser
                if(!Pattern.matches("repo:/([^|]+)(\\|.+)?", filePath)) {
                    reject(errors, "fileName", "Missing file path.  Please follow REPO syntax for REPO path:  repo:/[PATH]/[FILENAME]|[ORGANIZATION] eg. \"repo:/reports/interactive/CsvData|organization_1\"");
                }
            }
            if (filePath.toLowerCase().startsWith("ftp://")) {
                int idx1 = filePath.indexOf(":", 6);
                int idx2 = filePath.indexOf("@", 6);
                int idx3 = filePath.indexOf("/", idx1);
                if ((idx1 <= 0) || (idx2 <= 0) || (idx3 <= 0) || (idx3 == (idx2 + 1))) {
                    reject(errors, "fileName", "Please follow FTP syntax for FTP path: ftp://[USRNAME]:[PASSWORD]@[HOST]:[PORT]/[PATH]/[FILENAME]");
                }
            }
        }

        if (useFirstRowAsHeader == null || useFirstRowAsHeader.length() == 0) {
            reject(errors, "useFirstRowAsHeader", "Please enter 'true' or 'false' for using first row as header");
        } else if (!(useFirstRowAsHeader.toLowerCase().equals("true") || useFirstRowAsHeader.toLowerCase().equals("false"))) {
            reject(errors, "useFirstRowAsHeader", "Please enter 'true' or 'false' for using first row as header");
        }
	}

	// first arg is the path of the property which has the error
	// for custom DS's this will always be in the form "reportDataSource.propertyMap[yourPropName]"
	protected void reject(Errors errors, String name, String reason) {
		if (errors != null) errors.rejectValue("textDataSource.propertyMap[" + name + "]",  reason);
        else System.out.println("textDataSource.propertyMap[" + name + "] - " + reason);
	}


}
