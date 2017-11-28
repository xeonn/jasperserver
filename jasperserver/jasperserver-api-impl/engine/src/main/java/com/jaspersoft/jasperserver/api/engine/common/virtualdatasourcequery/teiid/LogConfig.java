package com.jaspersoft.jasperserver.api.engine.common.virtualdatasourcequery.teiid;

import org.teiid.logging.MessageLevel;

/**
 * @author Ivan Chan (ichan@jaspersoft.com)
 * @version $Id: LogConfig.java 24498 2012-08-10 21:27:06Z ichan $
 */
public class LogConfig {

    String logContext;
    String logLevel = Labels.NONE;

    public String getLogContext() {
        return logContext;
    }

    public void setLogContext(String logContext) {
        this.logContext = logContext;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public int getMessageLevel() {
        for (int i = MessageLevel.getValidLowerMessageLevel(); i <= MessageLevel.getValidUpperMessageLevel(); i++) {
            if (MessageLevel.getLabelForLevel(i).equalsIgnoreCase(logLevel)) return i;
        }
        return MessageLevel.NONE;
    }

    /**
     * Constants that define the types of the messages that are to be recorded
     * by the LogManager.
     */
    public static class Labels {
        public static final String CRITICAL     = "CRITICAL";
        public static final String ERROR        = "ERROR";
        public static final String WARNING      = "WARNING";
	    public static final String INFO         = "INFO";
	    public static final String DETAIL       = "DETAIL";
        public static final String TRACE        = "TRACE";
        public static final String NONE         = "NONE";
    }
}
