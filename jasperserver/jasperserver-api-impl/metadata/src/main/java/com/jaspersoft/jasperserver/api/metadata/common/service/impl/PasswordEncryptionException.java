package com.jaspersoft.jasperserver.api.metadata.common.service.impl;

import org.springframework.dao.DataAccessException;

/**
 * Created by IntelliJ IDEA.
 * User: dlitvak
 * Date: 7/27/12
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class PasswordEncryptionException extends DataAccessException {
	/**
	 * Constructor for PasswordEncryptionException.
	 * @param msg the detail message
	 */
	public PasswordEncryptionException(String msg) {
		super(msg);
	}


	/**
	 * Constructor for PasswordEncryptionException.
	 * @param msg the detail message
	 * @param cause the root cause
	 */
	public PasswordEncryptionException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
