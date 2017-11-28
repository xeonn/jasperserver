/*
 * Copyright © 2005 - 2016. TIBCO Software Inc. All Rights Reserved.
 */
package com.jaspersoft.jasperserver.dto.resources.domain.validation;

import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.dto.common.ValidationErrorDescriptorBuilder;
import com.jaspersoft.jasperserver.dto.resources.domain.PresentationElement;
import com.jaspersoft.jasperserver.dto.resources.domain.PresentationGroupElement;
import com.jaspersoft.jasperserver.dto.resources.domain.PresentationSingleElement;
import com.jaspersoft.jasperserver.dto.resources.domain.ResourceElement;
import com.jaspersoft.jasperserver.dto.resources.domain.Schema;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;

import static com.jaspersoft.jasperserver.dto.resources.domain.DomainSchemaHelper.findResourceElement;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id$
 */
public class ValidReferencesValidator implements ConstraintValidator<ValidReferences, Schema>, ValidationErrorDescriptorBuilder {
    @Override
    public void initialize(ValidReferences constraintAnnotation) {
    }

    @Override
    public boolean isValid(Schema value, ConstraintValidatorContext context) {
        return findInvalidReferences(value).isEmpty();
    }

    private List<String> findInvalidReferences(Schema schema){
        final List<String> result = new ArrayList<String>();
        final List<PresentationGroupElement> presentation = schema.getPresentation();
        final List<ResourceElement> resources = schema.getResources();
        fillInvalidReferences((List)presentation, resources, result);
        return result;
    }

    private void fillInvalidReferences(List<PresentationElement> presentationElements, List<ResourceElement> resources,
            List<String> invalidReferences){
        if(presentationElements != null){
            for (PresentationElement presentationElement : presentationElements) {
                if(presentationElement instanceof PresentationSingleElement){
                    final PresentationSingleElement presentationSingleElement = (PresentationSingleElement) presentationElement;
                    final String resourcePath = presentationSingleElement.getResourcePath();
                    if(resourcePath != null && findResourceElement(resourcePath, resources) == null) {
                        invalidReferences.add(resourcePath);
                    }
                } else if(presentationElement instanceof PresentationGroupElement){
                    fillInvalidReferences(((PresentationGroupElement) presentationElement).getElements(), resources, invalidReferences);
                }
            }
        }

    }

    @Override
    public ErrorDescriptor build(ConstraintViolation violation) {
        final List<String> invalidReferences = findInvalidReferences((Schema) violation.getInvalidValue());
        return new ErrorDescriptor().setErrorCode(ValidReferences.errorCode)
                .setParameters(invalidReferences.toArray())
                .setMessage("Schema contains invalid references: " + invalidReferences.toString());
    }
}
