package com.jaspersoft.jasperserver.war.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * <p>Puts sessionDecorator parameter into the session if it was found in the request parameters list.</p>
 *
 * @author Yuriy Plakosh
 * @version $Id: SessionDecoratorFilter.java 26539 2012-12-07 16:31:32Z sergey.prilukin $
 * @since 5.0.1
 */
public class SessionDecoratorFilter implements Filter {
    private static String SESSION_DECORATOR = "sessionDecorator";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Nothing.
    }

    @Override
    public void destroy() {
        // Nothing.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        String sessionDecorator = request.getParameter(SESSION_DECORATOR);

        if (!StringUtils.isEmpty(sessionDecorator)) {
            if (request instanceof HttpServletRequest) {
                HttpSession session = ((HttpServletRequest) request).getSession();

                session.setAttribute(SESSION_DECORATOR, sessionDecorator);
            }
        }

        chain.doFilter(request, response);
    }
}
