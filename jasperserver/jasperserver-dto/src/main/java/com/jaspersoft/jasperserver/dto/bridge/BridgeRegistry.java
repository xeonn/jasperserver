/*
 * Copyright © 2005 - 2016. TIBCO Software Inc. All Rights Reserved.
 */
package com.jaspersoft.jasperserver.dto.bridge;

import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id: BridgeRegistry.java 64415 2016-09-06 15:41:44Z ykovalch $
 */
public class BridgeRegistry {
    private static final Map<Class, Object> bridges = new HashMap<Class, Object>();
    public static <T> void registerBridge(Class<T> bridgeClass, T bridgeImpl){
        bridges.put(bridgeClass, bridgeImpl);
    }

    public static <T> T getBridge(Class<T> bridgeClass){
        return (T) bridges.get(bridgeClass);
    }
}
