/**
 * @author fpang
 * @since May 6, 2016
 * @version $Id: ParameterMap.java 63036 2016-05-06 20:57:09Z fpang $
 *
 */

package com.jaspersoft.jasperserver.dto.adhoc.query.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

/**
 * @author fpang
 *
 */

@Documented
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ParameterMapValidator.class })
@ReportAsSingleViolation
public @interface ParameterMap {
    String errorCode = "parameter.name.not.valid";

    String message() default errorCode;

    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
