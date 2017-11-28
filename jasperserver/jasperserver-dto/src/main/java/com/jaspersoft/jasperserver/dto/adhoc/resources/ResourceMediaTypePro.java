/*
 * Copyright (C) 2005 - 2013 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com.
 * Licensed under commercial Jaspersoft Subscription License Agreement
 */
package com.jaspersoft.jasperserver.dto.adhoc.resources;

import com.jaspersoft.jasperserver.dto.resources.ResourceMediaType;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id$
 */
public interface ResourceMediaTypePro extends ResourceMediaType {
    // Dashboard
    public static final String DASHBOARD_CLIENT_TYPE = "dashboard";
    public static final String DASHBOARD_JSON = RESOURCE_MEDIA_TYPE_PREFIX + DASHBOARD_CLIENT_TYPE + RESOURCE_JSON_TYPE;
    public static final String DASHBOARD_XML = RESOURCE_MEDIA_TYPE_PREFIX + DASHBOARD_CLIENT_TYPE + RESOURCE_XML_TYPE;
    // reportOptions
    public static final String REPORT_OPTIONS_CLIENT_TYPE = "reportOptions";
    public static final String REPORT_OPTIONS_JSON = RESOURCE_MEDIA_TYPE_PREFIX + REPORT_OPTIONS_CLIENT_TYPE + RESOURCE_JSON_TYPE;
    public static final String REPORT_OPTIONS_XML = RESOURCE_MEDIA_TYPE_PREFIX + REPORT_OPTIONS_CLIENT_TYPE + RESOURCE_XML_TYPE;
    // domainTopic
    public static final String DOMAIN_TOPIC_CLIENT_TYPE = "domainTopic";
    public static final String DOMAIN_TOPIC_CLIENT_TYPE_JSON = RESOURCE_MEDIA_TYPE_PREFIX + DOMAIN_TOPIC_CLIENT_TYPE + RESOURCE_JSON_TYPE;
    public static final String DOMAIN_TOPIC_CLIENT_TYPE_XML = RESOURCE_MEDIA_TYPE_PREFIX + DOMAIN_TOPIC_CLIENT_TYPE + RESOURCE_XML_TYPE;

}
