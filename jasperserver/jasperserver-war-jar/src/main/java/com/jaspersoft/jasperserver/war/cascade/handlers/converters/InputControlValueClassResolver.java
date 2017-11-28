package com.jaspersoft.jasperserver.war.cascade.handlers.converters;

import com.jaspersoft.jasperserver.api.engine.common.service.ReportInputControlInformation;
import com.jaspersoft.jasperserver.api.metadata.common.domain.DataType;
import com.jaspersoft.jasperserver.api.metadata.common.domain.util.DataTypeValueClassResolver;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

/**
 * @author Anton Fomin
 * @version $Id: InputControlValueClassResolver.java 39101 2013-10-22 20:53:50Z ztomchenco $
 */
public class InputControlValueClassResolver {

    public static Class<?> getValueClass(DataType dataType, ReportInputControlInformation info, boolean returnNestedType) {
        Class<?> valueClass = getInputControlValueClass(info, returnNestedType);
        if (valueClass == null && dataType != null) {
            valueClass = DataTypeValueClassResolver.getValueClass(dataType);
        }
        if (valueClass == null)
            valueClass = String.class;
        return valueClass;
    }

    public static Class<?> getValueClass(DataType dataType, ReportInputControlInformation info) {
        return getValueClass(dataType, info, true);
    }

    private static Class<?> getInputControlValueClass(ReportInputControlInformation info, boolean returnNestedType) {
        Class<?> parameterType = null;
        if (info != null && info.getValueType() != null) {
            if (Collection.class.isAssignableFrom(info.getValueType())) {
                if (!returnNestedType) {
                    parameterType = info.getValueType();
                } else if (info.getNestedType() != null) {
                    parameterType = info.getNestedType();
                }
            } else {
                parameterType = info.getValueType();
            }
        }
        return parameterType;
    }
}
