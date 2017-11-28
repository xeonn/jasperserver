package com.jaspersoft.jasperserver.api.engine.jasperreports.service.impl;

import com.jaspersoft.jasperserver.api.engine.common.service.ReportInputControlsInformation;

/**
 * @author Pavel Lysak
 */
public class ReportUtils {
    /**
     * Merge IC Info from two sources. Second has priority (overwrites the first)
     *
     * @param first
     * @param second
     * @return
     */
    public static ReportInputControlsInformation mergeInputControlsInfo(ReportInputControlsInformation first, ReportInputControlsInformation second) {
        if(first == null) {
            return second;
        }
        if(second == null) {
            return first;
        }
        ReportInputControlsInformationImpl res = new ReportInputControlsInformationImpl();
        for(String name: first.getControlNames()) {
            res.setInputControlInformation(name, first.getInputControlInformation(name));
        }
        for(String name: second.getControlNames()) {
            res.setInputControlInformation(name, second.getInputControlInformation(name));
        }
        return res;
    }

}
