package com.jaspersoft.jasperserver.api.engine.jasperreports.service.impl;

import com.jaspersoft.jasperserver.api.engine.common.service.ReportInputControlValueInformation;

import java.io.Serializable;

/*
* @author inesterenko
*/
public class ReportInputControlValueInformationImpl implements ReportInputControlValueInformation, Serializable{

    private static final long serialVersionUID = 1L;

    private String promptLabel;
    private Object defaultValue;

    public String getPromptLabel() {
        return promptLabel;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setPromptLabel(String promptLabel){
        this.promptLabel = promptLabel;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }
}
