package com.jaspersoft.jasperserver.war;

import com.jaspersoft.jasperserver.war.common.JavascriptOptimizationSettings;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet forwards requests from a path like
 * http://server:port/jrs/runtime/38479AD4/[scripts or optimized-scripts]/path/resource
 * to
 * http://server:port/jrs/[scripts or optimized-scripts]/path/resource
 * The "38479AD4" part is dynamically generated and changes on server start.
 *
 * The purpose for it is to help a proper cache management on client side.
 * We want resources to be cached by a browser for as long as we can, to reduce traffic between
 * the browser and the server related to static files (like javascript, etc.)
 * But we also want these resources to be updated when a client upgrades the server or makes modifications.
 *
 * @author asokolnikov
 */
public class JavascriptForwardingServlet extends HttpServlet {

    private static final String URL_PREFIX = "/runtime/";
    //Forwarded parameter was added 'cos WebSphere erase parameters from request on forward which results in bug JRS-10031
    //Do not remove this to avoid possible issues with WebSphere
    public static final String FORWARDED_PARAMETERS = "forwardedParameters";

    private JavascriptOptimizationSettings javascriptOptimizationSettings;
    private String urlPrefix;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String resourcePath = req.getPathInfo() == null ? path : path.concat(req.getPathInfo());
        String newResourcePath = resourcePath.replace(urlPrefix + javascriptOptimizationSettings.getRuntimeHash(), "");
        if (req.getParameterMap() != null) {
            req.setAttribute(FORWARDED_PARAMETERS, req.getParameterMap());
        }

        req.getRequestDispatcher(newResourcePath).forward(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        urlPrefix = config.getInitParameter("urlPrefix");
        urlPrefix = urlPrefix == null ? URL_PREFIX : urlPrefix;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        javascriptOptimizationSettings = (JavascriptOptimizationSettings) ctx.getBean("javascriptOptimizationSettings");
    }

}
