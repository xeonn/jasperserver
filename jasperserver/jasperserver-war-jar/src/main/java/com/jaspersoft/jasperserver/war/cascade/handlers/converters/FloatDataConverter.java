/*
* Copyright (C) 2005 - 2009 Jaspersoft Corporation. All rights  reserved.
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
* along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
*/
package com.jaspersoft.jasperserver.war.cascade.handlers.converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: FloatDataConverter.java 23652 2012-05-08 09:46:09Z ykovalchyk $
 */
@Service
public class FloatDataConverter implements DataConverter<Float> {
    @Override
    public Float stringToValue(String rawData) {
        return StringUtils.isNotEmpty(rawData) ? Float.parseFloat(rawData.replace(",", ".")) : null;
    }

    @Override
    public String valueToString(Float value) {
        if (value == null) return "";
        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(LocaleContextHolder.getLocale());
        df.setGroupingUsed(false);
        df.setMaximumFractionDigits(Integer.MAX_VALUE);
        return df.format(value);
    }
}
