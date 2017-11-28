package com.jaspersoft.jasperserver.api.engine.jasperreports.service.impl;

import com.jaspersoft.jasperserver.api.engine.common.service.ReportInputControlValueInformation;
import com.jaspersoft.jasperserver.api.engine.common.service.ReportInputControlValuesInformation;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/*
* @author inesterenko
*/

public class ReportInputControlValuesInformationImpl implements
        ReportInputControlValuesInformation, Serializable {
    
    private static final long serialVersionUID = 1L;

    private Map<String, ReportInputControlValueInformation> infos = new LinkedHashMap<String, ReportInputControlValueInformation>();

    public ReportInputControlValuesInformationImpl() {
    }

    public Set<String> getControlValuesNames() {
        return infos.keySet();
    }

    public ReportInputControlValueInformation getInputControlValueInformation(String name) {
        return infos.get(name);
    }
    
    public void setInputControlValueInformation(String name, ReportInputControlValueInformation info){
        infos.put(name, info);
    }
}
