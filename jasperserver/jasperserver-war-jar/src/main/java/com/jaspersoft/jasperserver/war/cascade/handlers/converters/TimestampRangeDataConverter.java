package com.jaspersoft.jasperserver.war.cascade.handlers.converters;

import com.jaspersoft.jasperserver.api.common.util.TimeZoneContextHolder;
import net.sf.jasperreports.types.date.DateRangeExpression;
import com.jaspersoft.jasperserver.api.common.util.rd.DateRangeFactory;
import net.sf.jasperreports.types.date.TimestampRange;
import com.jaspersoft.jasperserver.war.util.CalendarFormatProvider;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.DateFormat;

/**
 * @author Sergey Prilukin
 * @version $Id: TimestampRangeDataConverter.java 25420 2012-10-20 16:36:09Z sergey.prilukin $
 */
@Service
public class TimestampRangeDataConverter implements DataConverter<TimestampRange> {

    @Resource(name = "messagesCalendarFormatProvider")
    protected CalendarFormatProvider calendarFormatProvider;

    @Override
    public TimestampRange stringToValue(String rawData) throws Exception {
        if (StringUtils.isEmpty(rawData)) {
            return null;
        }

        return (TimestampRange) DateRangeFactory.getInstance(rawData, Timestamp.class,
                DateRangeDataConverter.getStringDatePattern(calendarFormatProvider.getDatetimeFormat()));
    }

    @Override
    public String valueToString(TimestampRange value) {
        if (value == null) {
            return "";
        } else if (value instanceof DateRangeExpression) {
            return ((DateRangeExpression) value).getExpression();
        } else {
            return TimestampDataConverter.getDateFormatWithTimeZone(calendarFormatProvider.getDatetimeFormat())
                    .format(value.getStart());
        }
    }
}
