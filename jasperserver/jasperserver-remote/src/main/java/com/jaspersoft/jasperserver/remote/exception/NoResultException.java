package com.jaspersoft.jasperserver.remote.exception;

import com.jaspersoft.jasperserver.remote.exception.xml.ErrorDescriptor;

/**
 * @author: Zakhar.Tomchenco
 */
public class NoResultException extends AccessDeniedException {
    public final String ERROR_CODE_NO_RESULT = "no.result.available";

    public NoResultException(ErrorDescriptor errorDescriptor) {
        super(errorDescriptor);
        this.getErrorDescriptor().setErrorCode(ERROR_CODE_NO_RESULT);
    }

    public NoResultException(String message) {
        super(message);
        this.getErrorDescriptor().setErrorCode(ERROR_CODE_NO_RESULT);
    }
}
