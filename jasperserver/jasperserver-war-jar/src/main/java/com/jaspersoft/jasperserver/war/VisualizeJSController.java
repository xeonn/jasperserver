package com.jaspersoft.jasperserver.war;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * This controller intended to create initialized copy of Visualize.js
 *
 * @author Zakhar Tomchenko
 * @version $Id: VisualizeJSController.java 45722 2014-05-14 10:24:22Z sergey.prilukin $
 */

public class VisualizeJSController implements Controller {
    private final String LOG_ENABLED_PARAMETER = "logEnabled";
    private final String LOG_LEVEL_PARAMETER = "logLevel";
    private final String BASE_URL_PARAMETER = "baseUrl";
    private String defaultUrl;
    @Resource
    private Map<String, String> clientLogging;

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String baseUrl = defaultUrl != null && !defaultUrl.isEmpty()
                ? defaultUrl
                : request.getRequestURL().toString().replace("/client/visualize.js", "");
        final String baseUrlRequestParameter = request.getParameter(BASE_URL_PARAMETER);
        if(baseUrlRequestParameter != null && !baseUrlRequestParameter.isEmpty()){
            baseUrl = baseUrlRequestParameter;
        }
        final String logEnabled = "false".equalsIgnoreCase(request.getParameter(LOG_ENABLED_PARAMETER))
                ? "false" : clientLogging.get("enabled");
        final String logLevelRequestParameter = request.getParameter(LOG_LEVEL_PARAMETER);
        final String logLevel = logLevelRequestParameter != null && !logLevelRequestParameter.isEmpty()
                ? logLevelRequestParameter : clientLogging.get("level");

        final ModelAndView result = new ModelAndView("modules/visualize");
        result.addObject("baseUrl", baseUrl);
        result.addObject("logEnabled", logEnabled);
        result.addObject("logLevel", logLevel);

        return result;
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }
}
