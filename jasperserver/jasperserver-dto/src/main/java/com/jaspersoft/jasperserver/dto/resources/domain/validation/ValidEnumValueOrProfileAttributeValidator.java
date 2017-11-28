/*
 * Copyright © 2005 - 2016. TIBCO Software Inc. All Rights Reserved.
 */
package com.jaspersoft.jasperserver.dto.resources.domain.validation;

import com.jaspersoft.jasperserver.dto.bridge.BridgeRegistry;
import com.jaspersoft.jasperserver.dto.bridge.SettingsBridge;
import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.dto.common.ValidationErrorDescriptorBuilder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id: ValidEnumValueOrProfileAttributeValidator.java 64446 2016-09-12 12:18:52Z ykovalch $
 */
public class ValidEnumValueOrProfileAttributeValidator implements
        ConstraintValidator<ValidEnumValueOrProfileAttribute, String>, ValidationErrorDescriptorBuilder {
    private Set<String> enumNames = new HashSet<String>();
    private String errorCode;
    @Override
    public void initialize(ValidEnumValueOrProfileAttribute constraintAnnotation) {
        Object[] constants = constraintAnnotation.enumClass().getEnumConstants();
        errorCode = constraintAnnotation.message();
        if(constants == null){
            throw new IllegalStateException("enumClass [" + constraintAnnotation.enumClass() + "]is not enum");
        }
        for (Object constant : constants) {
            enumNames.add(constant.toString());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        final Pattern pattern = getProfileAttributeFunctionPattern();
        return value == null || enumNames.contains(value) || pattern == null || pattern.matcher(value).matches();
    }

    protected Pattern getProfileAttributeFunctionPattern(){
        final SettingsBridge bridge = BridgeRegistry.getBridge(SettingsBridge.class);
        return bridge != null ? Pattern.compile((String) bridge.getSetting(SettingsBridge.GROUP_NAME_PROFILE_ATTRIBUTES,
                "$." + SettingsBridge.SETTING_NAME_ATTRIBUTE_PLACEHOLDER_SIMPLE_PATTERN)) : null;
    }

    @Override
    public ErrorDescriptor build(ConstraintViolation violation) {
        return new ErrorDescriptor().setErrorCode(errorCode).setMessage(violation.getPropertyPath()
                + " should be one of " + enumNames.toString() + " or profile attribute placeholder but is ["
                + violation.getInvalidValue() + "]")
                .setParameters(violation.getPropertyPath(), enumNames.toString(), violation.getInvalidValue());
    }
}

