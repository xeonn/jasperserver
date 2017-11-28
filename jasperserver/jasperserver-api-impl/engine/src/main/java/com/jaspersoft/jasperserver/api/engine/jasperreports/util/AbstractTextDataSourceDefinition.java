/*
 * Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.jaspersoft.jasperserver.api.engine.jasperreports.util;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.design.JRDesignField;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ichan
 * Date: 9/19/14
 * Time: 1:32 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTextDataSourceDefinition extends DataAdapterDefinition {

    protected static final Log log = LogFactory.getLog(DataAdapterDefinition.class);
    // searching column data type under the following order:
    protected Class typeList[] = {java.util.Date.class, Boolean.class, Number.class, String.class};
    // assigning numeric data type under the following order:
    ArrayList<String> numericTypes = new ArrayList<String>(Arrays.asList("java.lang.Integer", "java.lang.Long", "java.lang.Double"));

    // FOR METADATA DISCOVERY
    int rowCountForMetadataDiscovery = -1;      // number of rows to use for metadata discovery

    public int getRowCountForMetadataDiscovery() {
        return rowCountForMetadataDiscovery;
    }

    public void setRowCountForMetadataDiscovery(int rowCountForMetadataDiscovery) {
        this.rowCountForMetadataDiscovery = rowCountForMetadataDiscovery;
    }

    public static boolean containsValue(List list) {
        return list != null && (list.size() > 0);
    }

    /****  HELPER FUNCTIONS FOR FIELD NAMES DISCOVERY  ***/

    public static Map<String, String> getDefaultFieldMapping(List<String> fieldNames) {
        Map<String, String> fieldMapping = new HashMap<String, String>();
        for (int i = 0; i < fieldNames.size(); i++) {
            fieldMapping.put(fieldNames.get(i), ("FIELD " + (i+1)));
        }
        return fieldMapping;
    }

    public static Map<String, String> getFieldMapping(List<String> fieldNames) {
        Map<String, String> fieldMapping = new HashMap<String, String>();
        for (String fieldName: fieldNames) {
            fieldMapping.put(fieldName, fieldName);
        }
        return fieldMapping;
    }

    // find field type from the data in JRDataSource
    protected List<String> getFieldTypes(JRDataSource dataSource, List<JRField> jrFields) {
        String columnTypeArray[] = new String[jrFields.size()];
        //scan through number of rows to use for metadata discovery
        for (int i = 0; (rowCountForMetadataDiscovery < 0) || (i < rowCountForMetadataDiscovery); i++) {
            for (int j = 0; j < jrFields.size(); j++) {
                String type = findType(dataSource, (JRDesignField)jrFields.get(j));
                columnTypeArray[j] = getCompatibleDataType(columnTypeArray[j], type);
            }
            try {
                if (!dataSource.next()) break;
            } catch (Exception ex) {
                break;
            }
        }
        // default to String for all NULL columns
        for (int i = 0; i < columnTypeArray.length; i++) {
            if (columnTypeArray[i] == null) {
                columnTypeArray[i] = "java.lang.String";
            }
            log.debug("DETECTED COLUMN TYPE[" + i + "] = " + columnTypeArray[i]);
        }
        return Arrays.asList(columnTypeArray);
    }

    // get compatible data type
    // for example:  double, integer > double
    //               double, string  > string
    protected String getCompatibleDataType(String originalType, String newType) {
        if (originalType == null) return newType;
        if (newType == null) return originalType;
        if (originalType.equals(newType)) return originalType;
        int originalNumericTypeIndex = numericTypes.indexOf(originalType);
        int newNumericTypeIndex = numericTypes.indexOf(newType);
        if ((originalNumericTypeIndex >= 0) && (newNumericTypeIndex >= 0)) {
            return numericTypes.get(Math.max(originalNumericTypeIndex, newNumericTypeIndex));
        }
        return "java.lang.String";
    }

    protected String findType(JRDataSource csvDataSource, JRDesignField field) {
        for (Class type : typeList) {
            Class fieldType = getFieldType(type);
            field.setValueClassName(fieldType.getName());
            field.setValueClass(fieldType);
            Object value = null;
            try {
                value = csvDataSource.getFieldValue(field);
            } catch (Exception ex) {
                continue;
            };
            if ((fieldType != String.class) && (value == null)) continue;
            if ((value == null) || value.toString().trim().equals("")) {
                // value is null.  Can't detect type
                return null;
            } else if (type == Boolean.class) {
                // BOOLEAN TYPE
                if (getBooleanType(value.toString()) == null) continue;
            } else if (type == Number.class) {
                // NUMERIC TYPE
                Class numericType = getNumericType(value, field);
                if (numericType != null) type = numericType;
                else continue;

            }
            //    log.debug("FIELD = " + field.getName()  + " VALUE = " + value.toString() + ", TYPE = " + type.getName());
            return type.getName();
        }
        return "java.lang.String";
    }


    protected Class getFieldType(Class type) {
        if (type == Number.class || type == Boolean.class) return String.class;
        else return type;
    }

    protected Class getNumericType(Object obj, JRDesignField field) {
        Double value = null;
        if (obj instanceof Double) {
            value = (Double) obj;
        } else if (obj instanceof String) {
            String s = (String) obj;
            if (!s.matches("[-+]?\\d*\\.?\\d+")) {
                return null;
            }
            value = Double.parseDouble(s);
        }
        if ((value - Math.rint(value)) == 0) {
            if ((value > Integer.MAX_VALUE) || (value < Integer.MIN_VALUE)) return Long.class;
            else return Integer.class;
        } else return Double.class;
    }

    protected Class getBooleanType(String value) {
        if (value.toString().equals("true") || value.toString().equals("false")) return Boolean.class;
        else return null;
    }

}
