package com.jaspersoft.jasperserver.dto.executions;

/**
 * @author Vasyl Spachynskyi
 * @version $Id: QueryExecutionsMediaType.java 64626 2016-09-26 13:25:24Z vzavadsk $
 * @since 16.01.2016
 */
public interface QueryExecutionsMediaType {
    String JSON = "+json";
    String XML = "+xml";
    String MEDIA_TYPE_PREFIX = "application/execution.";

    String PROVIDED_QUERY = "providedQuery";
    String MULTI_LEVEL_QUERY = "multiLevelQuery";
    String MULTI_AXES_QUERY = "multiAxesQuery";

    String EXECUTION_MULTI_LEVEL_QUERY_JSON = MEDIA_TYPE_PREFIX + MULTI_LEVEL_QUERY + JSON;
    String EXECUTION_MULTI_AXES_QUERY_JSON = MEDIA_TYPE_PREFIX + MULTI_AXES_QUERY + JSON;
    String EXECUTION_PROVIDED_QUERY_JSON = MEDIA_TYPE_PREFIX + PROVIDED_QUERY + JSON;

    String EXECUTION_MULTI_LEVEL_QUERY_XML = MEDIA_TYPE_PREFIX + MULTI_LEVEL_QUERY + XML;
    String EXECUTION_MULTI_AXES_QUERY_XML = MEDIA_TYPE_PREFIX + MULTI_AXES_QUERY + XML;
    String EXECUTION_PROVIDED_QUERY_XML = MEDIA_TYPE_PREFIX + PROVIDED_QUERY + XML;
}
