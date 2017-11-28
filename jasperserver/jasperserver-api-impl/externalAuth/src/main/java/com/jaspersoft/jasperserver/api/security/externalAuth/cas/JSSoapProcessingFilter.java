package com.jaspersoft.jasperserver.api.security.externalAuth.cas;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.rememberme.NullRememberMeServices;
import org.springframework.util.Assert;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Chaim Arbiv
 * @version $id$
 */
public class JSSoapProcessingFilter extends JSCasProcessingFilter{
	//TODO: Do we need to expose these properties in config???  Should we make them constants?
	private String authorizationHeaderKeyName = "Authorization";
	private String encryptionKeyName = "Basic";
	private String credentialsDelimiter = ":";

    private String userName;
    private String password;
    private String ticket;
    private FilterChain chain;

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(getAuthenticationManager(), "authenticationManager must be specified");
        Assert.notNull(getExternalDataSynchronizer(), "externalDataSynchronizer cannot be null");

        if (getRememberMeServices() == null) {
            setRememberMeServices(new NullRememberMeServices());
        }
    }

    public void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.chain = chain;
        super.doFilterHttp(request, response,chain);
    }

	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI();
		int pathParamIndex = uri.indexOf(';');

		if (pathParamIndex > 0) {
			// strip everything after the first semi-colon
			uri = uri.substring(0, pathParamIndex);
		}

		if ("".equals(request.getContextPath())) {
			return uri.endsWith(getFilterProcessesUrl());
		}

		return uri.startsWith(request.getContextPath() + getFilterProcessesUrl());
	}

    // since we need to deal with the usaul superuser:superuser auth and ticket:<ticket> auth
    protected void onPreAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        String header = request.getHeader("Authorization");

		if (header != null && logger.isDebugEnabled()) {
			logger.debug("Authorization header: " + header);
		}

		if ((header != null) && header.startsWith(encryptionKeyName)) {
			String base64Token = header.substring(6);
            String token = new String(Base64.decodeBase64(base64Token.getBytes()));

            int delim = token.indexOf(":");
            String tempHeader; // to check if we got a normal user pass token or a it is a sso ticket
            if (delim != -1) {
                tempHeader = token.substring(0, delim);
                if (tempHeader.equalsIgnoreCase(encryptionKeyName))
                    ticket = token.substring(delim + 1);
                else {
                    userName = tempHeader;
                    password = token.substring(delim + 1);
                }
            }
        }
	}

    protected void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException{
        try {
            chain.doFilter(request, response);
        }

        catch (ServletException e) {
            logger.error( "ServletException: "+ e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    public String getAuthorizationHeaderKeyName() {
        return authorizationHeaderKeyName;
    }

    public void setAuthorizationHeaderKeyName(String authorizationHeaderKeyName) {
        this.authorizationHeaderKeyName = authorizationHeaderKeyName;
    }

    public String getEncryptionKeyName() {
        return encryptionKeyName;
    }

    protected String obtainTicket(HttpServletRequest request) {
        return ticket;
    }

    public void setEncryptionKeyName(String encryptionKeyName) {
        this.encryptionKeyName = encryptionKeyName;
    }

    public String getCredentialsDelimiter() {
        return credentialsDelimiter;
    }

    public void setCredentialsDelimiter(String credentialsDelimiter) {
        this.credentialsDelimiter = credentialsDelimiter;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public FilterChain getChain() {
        return chain;
    }

    public void setChain(FilterChain chain) {
        this.chain = chain;
    }
}
