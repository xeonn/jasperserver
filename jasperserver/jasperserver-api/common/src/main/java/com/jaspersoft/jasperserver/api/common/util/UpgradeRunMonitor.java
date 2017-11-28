package com.jaspersoft.jasperserver.api.common.util;

/**
 * <p>Monitors upgrade process.</p>
 *
 * @author Yuriy Plakosh
 * @version $Id: UpgradeRunMonitor.java 25010 2012-09-26 16:56:35Z sergey.prilukin $
 * @since 4.7.0
 */
public class UpgradeRunMonitor {
    private static final ThreadLocal<Boolean> threadMonitor = new ThreadLocal<Boolean>();

    public static void start() {
        threadMonitor.set(Boolean.TRUE);
    }

    public static void stop() {
        threadMonitor.set(Boolean.FALSE);
    }

    public static boolean isUpgradeRun() {
        return threadMonitor.get() != null && threadMonitor.get();
    }
}
