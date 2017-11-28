package com.jaspersoft.jasperserver.api.security.externalAuth.cas;

import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.rememberme.NullRememberMeServices;
import org.springframework.security.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Chaim Arbiv
 * @version $id$
 * uses to process rest/login for cas.
 * NOTE: unlike other sso solution where the request login should match the service request (during the ticket validation)
 * due to the CAS spring implementation we need to issue a ticket for j_spring_security_check
 */
public class CasRestProcessingFilter extends JSCasProcessingFilter{

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasLength(getFilterProcessesUrl(), "filterProcessesUrl must be specified");
        Assert.isTrue(UrlUtils.isValidRedirectUrl(getFilterProcessesUrl()), getFilterProcessesUrl()+ " isn't a valid redirect URL");

        Assert.notNull(getAuthenticationManager(), "authenticationManager must be specified");
        Assert.notNull(getExternalDataSynchronizer(), "externalDataSynchronizer must not be null.");
        if (getRememberMeServices() == null) {
            setRememberMeServices(new NullRememberMeServices());
        }

        Assert.notNull(getExternalDataSynchronizer(), "externalDataSynchronizer cannot be null");
    }

    // On successful login it should let the filter chain end and handle the response (which should be 200)
    protected void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        response.setContentType("text/xml; charset=UTF-8");
        return;
    }

    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException failed) throws IOException {
        response.setContentType("text/xml; charset=UTF-8");
        return;
    }
}
