/*
 * Copyright © 2005 - 2016. TIBCO Software Inc. All Rights Reserved.
 */
package com.jaspersoft.jasperserver.dto.bridge;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id: SettingsBridge.java 64446 2016-09-12 12:18:52Z ykovalch $
 */
public interface SettingsBridge {
    String GROUP_NAME_PROFILE_ATTRIBUTES = "profileAttributes";
    String SETTING_NAME_ATTRIBUTE_PLACEHOLDER_SIMPLE_PATTERN = "attributePlaceholderSimplePattern";
    <T> T getSetting(String group, String path);
}
