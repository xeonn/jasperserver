package com.jaspersoft.jasperserver.api.metadata.common.service.impl.hibernate.util;

import java.util.Date;

/**
 * <p>Indicates if update of creationDate and updatedDate is required.</p>
 *
 * @author Yuriy Plakosh
 * @version $Id: UpdateDatesIndicator.java 24192 2012-06-19 23:29:47Z yuriy.plakosh $
 * @since 4.7.0
 */
public class UpdateDatesIndicator {
    private static final ThreadLocal<Date> operationalDate = new ThreadLocal<Date>();
    private static final ThreadLocal<Boolean> useOperationalForUpdateDate = new ThreadLocal<Boolean>();

    /**
     * Initialize the indicator with date to be used.
     *
     * @param operationalDate the operational date.
     * @param useOperationalForUpdateDate indicates if we should use operational date for updateDate.
     */
    public static void required(Date operationalDate, boolean useOperationalForUpdateDate) {
        UpdateDatesIndicator.operationalDate.set(operationalDate);
        UpdateDatesIndicator.useOperationalForUpdateDate.set(useOperationalForUpdateDate);
    }

    /**
     * Cleans the indicator.
     */
    public static void clean() {
        operationalDate.set(null);
        useOperationalForUpdateDate.set(null);
    }

    /**
     * Returns <code>true</code> if creationDate and/or updateDate should be updated, <code>false</code> otherwise.
     *
     * @return <code>true</code> if creationDate and/or updateDate should be updated, <code>false</code> otherwise.
     */
    public static boolean shouldUpdate() {
        return operationalDate.get() != null;
    }

    /**
     * Returns operational date.
     *
     * @return operational date.
     */
    public static Date getOperationalDate() {
        return operationalDate.get();
    }

    /**
     * Returns <code>true</code> if we should use operational date for updateDate, <code>false</code> otherwise.
     *
     * @return <code>true</code> if we should use operational date for updateDate, <code>false</code> otherwise.
     */
    public static boolean useOperationalForUpdateDate() {
        return useOperationalForUpdateDate.get();
    }
}
