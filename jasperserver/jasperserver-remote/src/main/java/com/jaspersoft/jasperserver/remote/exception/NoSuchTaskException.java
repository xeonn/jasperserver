package com.jaspersoft.jasperserver.remote.exception;

import com.jaspersoft.jasperserver.remote.exception.xml.ErrorDescriptor;

/**
 * @author: Zakhar.Tomchenco
 */
public class NoSuchTaskException extends ResourceNotFoundException {
    public static final String ERROR_CODE_NO_SUCH_EXPORT_PROCESS = "no.such.export.process";

    public NoSuchTaskException(String taskId) {
        super(new ErrorDescriptor.Builder()
                .setErrorCode(ERROR_CODE_NO_SUCH_EXPORT_PROCESS)
                .setMessage("No export task with id " + taskId)
                .setParameters(taskId)
                .getErrorDescriptor());
    }
}

