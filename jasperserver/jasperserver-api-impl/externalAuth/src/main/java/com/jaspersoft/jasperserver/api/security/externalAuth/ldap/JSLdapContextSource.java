/*
 * Copyright (C) 2005 - 2013 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com.
 * Licensed under commercial Jaspersoft Subscription License Agreement
 */
package com.jaspersoft.jasperserver.api.security.externalAuth.ldap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import java.util.Hashtable;

/**
 * This class is a fix to spring-security 2.0.7 bug in ldap (JS bug 25501).
 *
 * User: dlitvak
 * Date: 2/1/13
 */
public class JSLdapContextSource extends DefaultSpringSecurityContextSource {
	private static final Log logger = LogFactory.getLog(JSLdapContextSource.class);

	/**
	 * Create and initialize an instance which will connect to the supplied LDAP URL.
	 *
	 * @param providerUrl an LDAP URL of the form <code>ldap://localhost:389/base_dn<code>
	 */
	public JSLdapContextSource(String providerUrl) {
		super(providerUrl);
	}

	public DirContext getReadWriteContext(String userDn, Object credentials) {
		Hashtable env = new Hashtable(getAnonymousEnv());

		env.put(Context.SECURITY_PRINCIPAL, userDn);
		env.put(Context.SECURITY_CREDENTIALS, credentials);
		env.remove(SUN_LDAP_POOLING_FLAG);

		if (logger.isDebugEnabled()) {
			logger.debug("Creating context with principal: '" + userDn + "'");
		}

		return createContext(env);
	}
}
