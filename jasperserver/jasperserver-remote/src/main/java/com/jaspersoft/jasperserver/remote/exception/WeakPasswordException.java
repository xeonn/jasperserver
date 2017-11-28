package com.jaspersoft.jasperserver.remote.exception;

import com.jaspersoft.jasperserver.remote.exception.xml.ErrorDescriptor;

/**
 * @author Zakhar.Tomchenco
 * @version $Id$
 */
public class WeakPasswordException extends IllegalParameterValueException {
    public static final String  ERROR_CODE_WEAK_PASSWORD = "weak.password";

    public WeakPasswordException(String password) {
        this("Password is too weak", password);
        this.getErrorDescriptor().setErrorCode(ERROR_CODE_WEAK_PASSWORD);
    }

    public WeakPasswordException(String message, String... parameters) {
        super(message, parameters);
        this.getErrorDescriptor().setErrorCode(ERROR_CODE_WEAK_PASSWORD);
    }

    public WeakPasswordException(ErrorDescriptor errorDescriptor) {
        super(errorDescriptor);
        this.getErrorDescriptor().setErrorCode(ERROR_CODE_WEAK_PASSWORD);
    }
}
