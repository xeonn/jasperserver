package com.jaspersoft.jasperserver.api.metadata.common.service.impl.hibernate.util;

/**
 * <p>Indicates if repository cache is on. Used for some special logic when we want to switch cache off.</p>
 *
 * @author Yuriy Plakosh
 * @version $Id: RepositoryCacheIndicator.java 25010 2012-09-26 16:56:35Z sergey.prilukin $
 */
public class RepositoryCacheIndicator {
    private static final ThreadLocal<Boolean> threadMonitor = new ThreadLocal<Boolean>();

    public static void off() {
        threadMonitor.set(Boolean.FALSE);
    }

    public static void on() {
        threadMonitor.set(Boolean.TRUE);
    }

    public static boolean isOn() {
        return threadMonitor.get() == null || threadMonitor.get();
    }
}
