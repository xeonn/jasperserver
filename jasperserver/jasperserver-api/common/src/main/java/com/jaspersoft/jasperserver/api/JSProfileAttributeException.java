package com.jaspersoft.jasperserver.api;

import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;

/**
 * <p></p>
 *
 * @author Vlad Zavadskii
 * @version $Id: JSProfileAttributeException.java 63760 2016-07-05 18:59:28Z agodovan $
 */
public class JSProfileAttributeException extends JSException {
    public static final String PROFILE_ATTRIBUTE_EXCEPTION_SUBSTITUTION_BASE =
            "profile.attribute.exception.substitution.base";
    public static final String PROFILE_ATTRIBUTE_EXCEPTION_SUBSTITUTION_NOT_FOUND =
            "profile.attribute.exception.substitution.not.found";
    public static final String PROFILE_ATTRIBUTE_EXCEPTION_SUBSTITUTION_CATEGORY_INVALID =
            "profile.attribute.exception.substitution.category.invalid";

    private ErrorDescriptor errorDescriptor;

    //We keep localizedMessage, because it is used for legacy popup error dialogs.
    // In ErrorDescriptor we use default message in English locale
    public JSProfileAttributeException(String localizedMessage, ErrorDescriptor errorDescriptor) {
        super(localizedMessage);

        this.errorDescriptor = errorDescriptor;
    }

    public JSProfileAttributeException(ErrorDescriptor errorDescriptor) {
        super(errorDescriptor.getMessage() != null ? errorDescriptor.getMessage() :
                "Profile attribute substitutions error: " + errorDescriptor.toString());

        this.errorDescriptor = errorDescriptor;
    }

    public ErrorDescriptor getErrorDescriptor() {
        return errorDescriptor;
    }
}
