package com.jaspersoft.jasperserver.api.engine.jasperreports.service.impl;

import com.jaspersoft.jasperserver.api.common.domain.ExecutionContext;
import com.jaspersoft.jasperserver.api.common.service.impl.SimpleClassMappObjectFactory;
import com.jaspersoft.jasperserver.api.engine.common.service.ReportInputControlsInformation;
import com.jaspersoft.jasperserver.api.engine.jasperreports.service.InputControlsInfoExtractor;
import com.jaspersoft.jasperserver.api.metadata.common.domain.Resource;
import net.sf.jasperreports.engine.JasperReport;

import java.util.HashMap;
import java.util.Map;


public class InputControlsInfoRoutingExtractor implements InputControlsInfoExtractor {
    private SimpleClassMappObjectFactory extractorMappings = new SimpleClassMappObjectFactory();

    private Map<String, InputControlsInfoExtractor> extractorsByClass = new HashMap();

    public void setExtractorsByClass(Map<String, InputControlsInfoExtractor> extractorsByClass) {
        this.extractorsByClass = extractorsByClass;
    }

    public void addExecutor(String resourceClassName, InputControlsInfoExtractor extractor) {
        if(extractorMappings.getMappings() == null) {
            extractorMappings.setMappings(new HashMap());
        }
        extractorMappings.getMappings().put(resourceClassName, extractor);
    }

    public JasperReport getJasperReport(ExecutionContext context, Resource resource) {
        InputControlsInfoExtractor extractor = (InputControlsInfoExtractor) extractorMappings.getClassObject(resource.getClass().getName());
        if(extractor != null) {
            return extractor.getJasperReport(context, resource);
        } else {
            throw new RuntimeException("method not supported.");
        }
    }


    public ReportInputControlsInformation extractInputControlsInfo(ExecutionContext context, Resource resource, Map initialParameters) {
        if(resource == null) {
            return null;
        }
        InputControlsInfoExtractor extractor = (InputControlsInfoExtractor) extractorMappings.getClassObject(resource.getClass().getName());
        if(extractor != null) {
            return extractor.extractInputControlsInfo(context, resource, initialParameters);
        }
        return null;
    }
}
