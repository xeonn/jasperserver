package com.jaspersoft.jasperserver.war;

import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Zakhar.Tomchenco
 */
public class ForbiddenEntryPoint implements AuthenticationEntryPoint {
    public final String SUPPRESS_BASIC_HEADER = "X-Suppress-Basic";
    public final String HTTP_WWW_AUTHENTICATE_HEADER = "WWW-Authenticate";

    public void commence(ServletRequest request, ServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (!"true".equalsIgnoreCase(httpRequest.getHeader(SUPPRESS_BASIC_HEADER))){
            httpResponse.setHeader(HTTP_WWW_AUTHENTICATE_HEADER, "Basic realm=\"Protected area\"");
        }

        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
