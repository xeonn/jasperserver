/*
 * Copyright © 2005 - 2016. TIBCO Software Inc. All Rights Reserved.
 */
package com.jaspersoft.jasperserver.remote.connection;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id$
 */
public interface GenericTypeMetadataBuilder<T> extends ConnectionMetadataBuilder<T> {
    boolean isMetadataSupported(T connectionDescription);

}
