package com.jaspersoft.jasperserver.war.cascade;

/**
 * @author Anton Fomin
 * @version $Id: InputControlValidationException.java 23989 2012-06-01 11:07:51Z afomin $
 */
public class InputControlValidationException extends Exception {

    InputControlValidationError validationError;

    public InputControlValidationException(String errorCode, Object[] arguments, String defaultMessage, String invalidValue) {
        super(errorCode);
        validationError = new InputControlValidationError(errorCode, arguments, defaultMessage, null, invalidValue);
    }

    public InputControlValidationError getValidationError() {
        return validationError;
    }

}
