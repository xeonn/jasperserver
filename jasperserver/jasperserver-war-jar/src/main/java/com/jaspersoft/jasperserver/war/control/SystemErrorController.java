package com.jaspersoft.jasperserver.war.control;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Handles system errors.
 *
 * @author Yuriy Plakosh
 */
public class SystemErrorController extends JRBaseMultiActionController {

    public static final String DELETE = "DELETE";

    public SystemErrorController(){
        final List<String> supportedMethodsList = new ArrayList<String>(Arrays.asList(getSupportedMethods()));
        supportedMethodsList.add("DELETE");
        supportedMethodsList.add("PUT");
        setSupportedMethods(StringUtils.toStringArray(supportedMethodsList));
    }
    
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * Handles 404 error (page not found error).
     *
     * @param req the request.
     * @param res the response.
     *
     * @return model and view.
     */
    public ModelAndView handle404(HttpServletRequest req, HttpServletResponse res) {
        return new ModelAndView("modules/system/404");
    }

    /**
     * Handles 500 error (page not found error).
     *
     * @param req the request.
     * @param res the response.
     *
     * @return model and view.
     */
    public ModelAndView handle500(HttpServletRequest req, HttpServletResponse res) {
        ModelAndView mav = new ModelAndView("modules/system/500");

        String systemErrorDetails = (String)req.getAttribute("javax.servlet.error.message");

        Object e = req.getAttribute("javax.servlet.error.exception");

        if (e != null && e instanceof Throwable) {
            logger.error("Internal server error", (Throwable)e);
			if (systemErrorDetails == null || systemErrorDetails.length() == 0)
				systemErrorDetails = ((Throwable) e).getMessage();
        }

        mav.addObject("systemErrorDetails", systemErrorDetails);
        return mav;
    }
}