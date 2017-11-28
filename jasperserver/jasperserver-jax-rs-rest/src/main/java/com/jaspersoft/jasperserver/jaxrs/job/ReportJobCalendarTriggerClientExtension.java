/*
* Copyright (C) 2005 - 2013 Jaspersoft Corporation. All rights  reserved.
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
package com.jaspersoft.jasperserver.jaxrs.job;

import com.jaspersoft.jasperserver.api.engine.scheduling.domain.ReportJobCalendarTrigger;
import com.jaspersoft.jasperserver.api.engine.scheduling.domain.jaxb.NoTimezoneDateToStringXmlAdapter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: ReportJobCalendarTriggerClientExtension.java 38348 2013-09-30 04:57:18Z carbiv $
 */
@XmlRootElement(name = "calendarTrigger")
public class ReportJobCalendarTriggerClientExtension extends ReportJobCalendarTrigger {

    public ReportJobCalendarTriggerClientExtension(){}
    public ReportJobCalendarTriggerClientExtension(ReportJobCalendarTrigger source){
        super(source);
    }

    @XmlJavaTypeAdapter(NoTimezoneDateToStringXmlAdapter.class)
    @Override
    public Date getStartDate() {
        return super.getStartDate();
    }

    @XmlJavaTypeAdapter(NoTimezoneDateToStringXmlAdapter.class)
    @Override
    public Date getEndDate() {
        return super.getEndDate();
    }
}
