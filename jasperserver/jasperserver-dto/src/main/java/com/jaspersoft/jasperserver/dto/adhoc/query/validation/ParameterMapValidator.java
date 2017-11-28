/**
 * @author fpang
 * @since May 6, 2016
 * @version $Id: ParameterMapValidator.java 65088 2016-11-03 23:22:01Z gbacon $
 *
 */

package com.jaspersoft.jasperserver.dto.adhoc.query.validation;

import java.util.Map;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpression;

/**
 * @author fpang, schubar
 *
 */
public class ParameterMapValidator implements ConstraintValidator<ParameterMap, Map<String, ClientExpression>> {
    public static Pattern NOT_W_PATTERN = Pattern.compile("[^\\p{L}\\p{N}_]");
    public static Pattern NOT_A_TO_Z_OR_UNDERSCORE_PATTERN = Pattern.compile("[^\\p{L}_]");

    @Override
    public void initialize(ParameterMap constraintAnnotation) {
    }

    @Override
    public boolean isValid(Map<String, ClientExpression> targetParameterMap, ConstraintValidatorContext context) {
        if (targetParameterMap != null) {
            for (String paramEl : targetParameterMap.keySet()) {
                if (paramEl == null
                    || paramEl.contains(".") // This check is explicitly defined because <dot> has special meaning in Data Engine and impl of ExpressionEvaluator
                    || NOT_A_TO_Z_OR_UNDERSCORE_PATTERN.matcher(paramEl.substring(0, 1)).matches()
                    || NOT_W_PATTERN.matcher(paramEl.substring(1)).matches()) {
                    return false;
                }
            }
        }
        return true;
    }
}
