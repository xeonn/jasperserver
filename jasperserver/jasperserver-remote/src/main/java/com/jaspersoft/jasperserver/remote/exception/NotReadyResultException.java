package com.jaspersoft.jasperserver.remote.exception;

import com.jaspersoft.jasperserver.remote.exception.xml.ErrorDescriptor;

/**
 * @author: Zakhar.Tomchenco
 */
public class NotReadyResultException extends AccessDeniedException {
    public final String ERROR_CODE_NOT_READY = "resource.not.ready";

    public NotReadyResultException(ErrorDescriptor errorDescriptor) {
        super(errorDescriptor);
        this.getErrorDescriptor().setErrorCode(ERROR_CODE_NOT_READY);
    }

    public NotReadyResultException(String message) {
        super(message);
        this.getErrorDescriptor().setErrorCode(ERROR_CODE_NOT_READY);
    }
}
