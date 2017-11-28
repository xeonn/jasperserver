/*
 * Copyright © 2005 - 2016. TIBCO Software Inc. All Rights Reserved.
 */
package com.jaspersoft.jasperserver.dto.resources.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id: ValidEnumValueOrProfileAttribute.java 64446 2016-09-12 12:18:52Z ykovalch $
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValidEnumValueOrProfileAttributeValidator.class })
@ReportAsSingleViolation
public @interface ValidEnumValueOrProfileAttribute {
    Class<?> enumClass();
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

