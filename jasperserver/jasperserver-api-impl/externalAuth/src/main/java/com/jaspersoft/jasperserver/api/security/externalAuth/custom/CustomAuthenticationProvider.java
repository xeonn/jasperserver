package com.jaspersoft.jasperserver.api.security.externalAuth.custom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.providers.AuthenticationProvider;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;

/**
 * Sample authenticationProvider to demonstrate use of arbitrary authentication. in this sample authenticates
 * requests that are authenticated on an even minute.
 *
 * This is only a sample class.
 *
 * @author Chaim Arbiv
 * @version $id$
 *
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {
    protected final Log logger = LogFactory.getLog(this.getClass());


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        logger.trace("user " + ((UserDetails) authentication.getPrincipal()).getUsername() + " has an even user name length");
        return createSuccessAuthentication(authentication, (UserDetails) authentication.getPrincipal());
    }

    /**
     * Creates a successful {@link Authentication} object.<p>Protected so subclasses can override.</p>
     *
     * @param authentication that was presented to the provider for validation
     * @param userDetails    that were parsed from SSO server response to ticket validation request.
     * @return the successful authentication token
     */
    protected Authentication createSuccessAuthentication(Authentication authentication, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), userDetails.getAuthorities());
        authToken.setDetails(userDetails);

        return authToken;
    }


    @Override
    public boolean supports(Class authentication) {
        final boolean supportsCustomToken = CustomAuthenticationToken.class.isAssignableFrom(authentication);
        logger.debug("Provider " + (supportsCustomToken ? "supports" : "does not support") + " authentication with " + authentication.getName());

        if (supportsCustomToken) {
            return true;
        } else {
            return false;
        }
    }
}
