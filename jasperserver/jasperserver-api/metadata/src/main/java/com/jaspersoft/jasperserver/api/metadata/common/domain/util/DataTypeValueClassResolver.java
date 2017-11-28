package com.jaspersoft.jasperserver.api.metadata.common.domain.util;

import com.jaspersoft.jasperserver.api.metadata.common.domain.DataType;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Zakhar.Tomchenco
 *
 */
public class DataTypeValueClassResolver {

    public static Class<?> getValueClass(DataType dataType) {
        Class<?> valueClass;
        switch (dataType.getType()) {
            case DataType.TYPE_TEXT:
                valueClass = String.class;
                break;
            case DataType.TYPE_NUMBER:
                valueClass = BigDecimal.class;
                break;
            case DataType.TYPE_DATE:
                valueClass = Date.class;
                break;
            case DataType.TYPE_DATE_TIME:
                valueClass = Timestamp.class;
                break;
            case DataType.TYPE_TIME:
                valueClass = Time.class;
                break;
            default:
                valueClass = String.class;
        }
        return valueClass;
    }
}
