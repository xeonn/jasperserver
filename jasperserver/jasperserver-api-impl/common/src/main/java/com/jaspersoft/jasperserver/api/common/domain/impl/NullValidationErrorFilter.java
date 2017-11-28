package com.jaspersoft.jasperserver.api.common.domain.impl;

import com.jaspersoft.jasperserver.api.common.domain.ValidationError;
import com.jaspersoft.jasperserver.api.common.domain.ValidationErrorFilter;

/**
 * @author Paul Lysak
 *         Date: 05.10.12
 *         Time: 16:30
 */
public class NullValidationErrorFilter implements ValidationErrorFilter {
    private static final NullValidationErrorFilter instance = new NullValidationErrorFilter();

    public static final ValidationErrorFilter getInstance() {
        return instance;
    }

    public static boolean isNullFilter(ValidationErrorFilter filter) {
        return filter == null || filter instanceof NullValidationErrorFilter;
    }

    @Override
    public boolean matchError(ValidationError error) {
        return true;
    }

    @Override
    public boolean matchErrorCode(String errorCode) {
        return true;
    }

    @Override
    public boolean matchErrorField(String errorField) {
        return true;
    }
}
