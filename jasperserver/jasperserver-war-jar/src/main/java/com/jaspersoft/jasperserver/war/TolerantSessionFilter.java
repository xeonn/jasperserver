package com.jaspersoft.jasperserver.war;

import com.jaspersoft.jasperserver.api.SessionAttribMissingException;
import com.jaspersoft.jasperserver.core.util.TolerantRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 *
 * Pushes the Tolerant request down the filter chain *
 *
 * Created by nthapa on 7/3/13.
 */
public class TolerantSessionFilter implements Filter {
    protected final static Log log = LogFactory.getLog(TolerantSessionFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        /* Left blank */
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        TolerantRequest req=new TolerantRequest((HttpServletRequest)request);
        HttpSession session=((HttpServletRequest) req).getSession();

        chain.doFilter(req, response);

    }

    @Override
    public void destroy() {
        /* Left blank */
    }
}
