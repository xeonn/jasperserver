package com.jaspersoft.jasperserver.api.security;

import org.apache.log4j.Logger;
import org.owasp.csrfguard.CsrfGuard;
import org.owasp.csrfguard.log.ILogger;
import org.owasp.csrfguard.log.LogLevel;

/**
 * Wrap log4j for CsrfGuard
 *
 * @author Anton Fomin
 * @version $Id: JSCsrfLogger.java 21192 2011-10-11 01:15:43Z afomin $
 */
public class JSCsrfLogger implements ILogger {

    private Logger log = Logger.getLogger(CsrfGuard.class);

    public void log(String msg) {
        log.info(msg);
    }

    public void log(LogLevel level, String msg) {
        if (LogLevel.Error.equals(level)) {
            log.error(msg);
        } else if (LogLevel.Fatal.equals(level)) {
            log.fatal(msg);
        } else if (LogLevel.Debug.equals(level)) {
            log.debug(msg);
        } else if (LogLevel.Trace.equals(level)) {
            log.trace(msg);
        } else if (LogLevel.Warning.equals(level)) {
            log.warn(msg);
        } else if (LogLevel.Info.equals(level)) {
            log.info(msg);
        } else {
            log.info(msg);
        }
    }

    public void log(Exception exception) {
        log.error(String.valueOf(exception));
    }

    public void log(LogLevel level, Exception exception) {
        log(level, String.valueOf(exception));
    }
}
