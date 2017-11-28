package com.jaspersoft.jasperserver.war.control.interceptor;

import com.jaspersoft.jasperserver.api.JSException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Handles exceptions which occurred during view rendering.
 *
 * @author Yuriy Plakosh
 */
public class RenderViewExceptionInterceptor extends HandlerInterceptorAdapter {
    protected final Log log = LogFactory.getLog(this.getClass());

    private MessageSource messageSource;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        if (ex != null) {
            String message;

            Locale locale = LocaleContextHolder.getLocale();
            if (ex instanceof JSException) {
                JSException jsException = (JSException)ex;
                message = messageSource.getMessage(ex.getMessage(), jsException.getArgs(), locale);
            } else {
                message = messageSource.getMessage("error.500.message", null, locale);
            }

            log.error(message, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
        }
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }
}
