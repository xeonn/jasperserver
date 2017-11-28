package com.jaspersoft.jasperserver.api.common.util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author Anton Fomin
 * @author StasChubar
 * @version $Id: TimeZoneContextHolder.java 22842 2012-03-28 09:39:29Z afomin $
 */
public abstract class TimeZoneContextHolder {
    public static final ThreadLocal<TimeZone> threadLocalContext = new ThreadLocal<TimeZone>();
    public static final ThreadLocal<TimeZone> inheritableThreadLocalContext = new InheritableThreadLocal<TimeZone>();

    public static void setTimeZone(TimeZone timeZone) {
        threadLocalContext.set(timeZone);
        inheritableThreadLocalContext.set(timeZone);
    }

    public static void resetTimeZone() {
        threadLocalContext.remove();
        inheritableThreadLocalContext.remove();
    }

    public static TimeZone getTimeZone() {
        TimeZone tz = threadLocalContext.get();
        if (tz == null) {
            tz = inheritableThreadLocalContext.get();
        }
        return (tz != null) ? tz : Calendar.getInstance().getTimeZone();
    }
}