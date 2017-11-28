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

import com.jaspersoft.jasperserver.war.util.CalendarFormatProvider;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: DateDataConverter.java 23649 2012-05-08 09:21:27Z ykovalchyk $
 */
@Service
public class DateDataConverter implements DataConverter<Date>{
    @Resource(name = "messagesCalendarFormatProvider")
    protected CalendarFormatProvider calendarFormatProvider;
    @Override
    public Date stringToValue(String rawData) throws ParseException {
        return StringUtils.isNotEmpty(rawData) ? calendarFormatProvider.getDateFormat().parse(rawData) : null;
    }

    @Override
    public String valueToString(Date value) {
        return value != null ? calendarFormatProvider.getDateFormat().format(value) : "";
    }
}