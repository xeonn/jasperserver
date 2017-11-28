package com.jaspersoft.jasperserver.war.cascade.handlers.converters;

import com.jaspersoft.jasperserver.api.common.util.TimeZoneContextHolder;
import net.sf.jasperreports.types.date.DateRangeExpression;
import com.jaspersoft.jasperserver.api.common.util.rd.DateRangeFactory;
import com.jaspersoft.jasperserver.war.util.CalendarFormatProvider;
import net.sf.jasperreports.types.date.DateRange;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Anton Fomin
 * @version $Id: DateRangeDataConverter.java 25420 2012-10-20 16:36:09Z sergey.prilukin $
 */
@Service
public class DateRangeDataConverter implements DataConverter<DateRange> {

    @Resource(name = "messagesCalendarFormatProvider")
    protected CalendarFormatProvider calendarFormatProvider;

    @Override
    public DateRange stringToValue(String rawData) throws Exception {
        if (StringUtils.isEmpty(rawData)) {
            return null;
        }

        return DateRangeFactory.getInstance(rawData, Date.class,
                getStringDatePattern(calendarFormatProvider.getDateFormat()));
    }

    @Override
    public String valueToString(DateRange value) {
        if (value == null) {
            return "";
        } else if (value instanceof DateRangeExpression) {
            return ((DateRangeExpression) value).getExpression();
        } else {
            return calendarFormatProvider.getDateFormat().format(value.getStart());
        }
    }

    public static String getStringDatePattern(DateFormat dateFormat) {
        return ((SimpleDateFormat) dateFormat).toPattern();
    }

}
