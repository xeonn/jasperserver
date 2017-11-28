package com.jaspersoft.jasperserver.war.cascade;

import com.jaspersoft.jasperserver.api.common.domain.ValidationErrors;

/**
 * @author Anton Fomin
 * @version $Id: InputControlsValidationException.java 38348 2013-09-30 04:57:18Z carbiv $
 */
public class InputControlsValidationException extends Exception {
    private ValidationErrors errors;

    public InputControlsValidationException(ValidationErrors errors) {
        super(errors.toString());
        this.errors = errors;
    }

    public ValidationErrors getErrors() {
        return errors;
    }

}
