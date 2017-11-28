package com.jaspersoft.jasperserver.remote.exception;

import com.jaspersoft.jasperserver.remote.exception.xml.ErrorDescriptor;

/**
 * @author Anton Fomin
 * @version $Id: AccessDeniedException.java 38348 2013-09-30 04:57:18Z carbiv $
 */
public class AccessDeniedException extends RemoteException {

    public static final String ERROR_CODE_ACCESS_DENIED = "access.denied";

    public AccessDeniedException(ErrorDescriptor errorDescriptor){
        super(errorDescriptor);
        this.getErrorDescriptor().setErrorCode(ERROR_CODE_ACCESS_DENIED);
    }

    public AccessDeniedException(String message) {
        super(message);
        this.getErrorDescriptor().setErrorCode(ERROR_CODE_ACCESS_DENIED);
    }

    public AccessDeniedException(String message, String... parameters) {
        super(message);
        setErrorDescriptor(new ErrorDescriptor.Builder().setMessage(message).setErrorCode(ERROR_CODE_ACCESS_DENIED).setParameters(parameters).getErrorDescriptor());
    }
}