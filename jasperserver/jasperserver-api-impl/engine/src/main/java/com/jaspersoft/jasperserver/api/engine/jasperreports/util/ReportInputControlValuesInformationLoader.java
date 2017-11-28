package com.jaspersoft.jasperserver.api.engine.jasperreports.util;

import com.jaspersoft.jasperserver.api.engine.common.service.ReportInputControlValuesInformation;
import com.jaspersoft.jasperserver.api.engine.jasperreports.service.impl.ReportInputControlValueInformationImpl;
import com.jaspersoft.jasperserver.api.engine.jasperreports.service.impl.ReportInputControlValuesInformationImpl;
import com.jaspersoft.jasperserver.api.metadata.common.domain.ListOfValues;
import com.jaspersoft.jasperserver.api.metadata.common.domain.ListOfValuesItem;
import org.springframework.context.MessageSource;

import java.util.ResourceBundle;

/**
 * Utility class for getting localized info for list of values
 *
 * @author Sergey Prilukin
 */
public class ReportInputControlValuesInformationLoader {

    public static ReportInputControlValuesInformation getReportInputControlValuesInformation(
            ListOfValues listOfValues, ResourceBundle resourceBundle, MessageSource messageSource) {

        ReportInputControlValuesInformationImpl valuesInformation = null;

        if (listOfValues != null){
            valuesInformation = new ReportInputControlValuesInformationImpl();

            ListOfValuesItem[] listOfValuesItem = listOfValues.getValues();
            for (ListOfValuesItem valuesItem : listOfValuesItem) {
                ReportInputControlValueInformationImpl valueInformation = new ReportInputControlValueInformationImpl();
                valueInformation.setPromptLabel(InputControlLabelResolver.resolve(valuesItem.getLabel(), resourceBundle, messageSource));
                valueInformation.setDefaultValue(valuesItem.getValue());
                valuesInformation.setInputControlValueInformation(valuesItem.getLabel(), valueInformation);
            }

        }

        return valuesInformation;
    }

}
