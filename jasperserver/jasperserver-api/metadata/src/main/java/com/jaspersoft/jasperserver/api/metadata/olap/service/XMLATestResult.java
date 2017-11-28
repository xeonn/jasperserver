package com.jaspersoft.jasperserver.api.metadata.olap.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLATestResult {
	public enum XMLATestCode {
		OK,
		URI_CONNECTION_FAILED,
		BAD_URI,
		BAD_CREDENTIALS,
		BAD_DATASOURCE,
		BAD_CATALOG,
		OTHER;
	}
	
	protected XMLATestCode status;
	protected String message;
	protected String details;
	protected String[] options;

    protected static final String regexUri = "(.*//.*:).*(@.*)";

    protected static final Set<XMLATestCode> ioExceptionTestCodes = new HashSet<XMLATestCode>(Arrays.asList(
            XMLATestCode.URI_CONNECTION_FAILED,
            XMLATestCode.BAD_URI,
            XMLATestCode.BAD_CREDENTIALS));


    public XMLATestResult(XMLATestCode status, String message, String[] options, Exception e) {
		this.status = status;
		this.options = options;

        if (e != null) {
            // In some cases the message contains the plain password.
            // Obfuscate the password if possible, otherwise - set the XMLATestCode name. Bug #34979.
            Matcher m = Pattern.compile(regexUri).matcher(message);

            boolean obfuscatePassword = ioExceptionTestCodes.contains(status) && m.matches();

            // set the status.name() in case if ioExceptionTestCodes has different URI format (not matching regexUri)
            this.message = obfuscatePassword ? m.replaceFirst("$1********$2") : message;

            // set the stack trace
            StringWriter result = new StringWriter();
            PrintWriter trace = new PrintWriter(result);
            e.printStackTrace(trace);

            this.details = result.toString();
        }
	}

	public XMLATestResult(XMLATestCode status, String message, Exception e) {
		this(status,message,null,e);
	}

	public XMLATestResult(XMLATestCode status) {
		this(status,"",null,null);
	}

    public String buildJson() throws Exception {
        Map<String, String> response = new HashMap<String, String>();
        response.put("status", status.name());
        response.put("message", message);
        if (details != null) {
            response.put("details", details);
        }

        ObjectMapper mapper = new ObjectMapper();
        StringWriter result = new StringWriter();
        mapper.writeValue(result, response);
        return result.toString();
    }

	public XMLATestCode getStatus() {
		return status;
	}
	public void setStatus(XMLATestCode status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String[] getOptions() {
		return options;
	}
	public void setOptions(String[] options) {
		this.options = options;
	}

}
