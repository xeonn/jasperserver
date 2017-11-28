package com.jaspersoft.jasperserver.dto.executions;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Vasyl Spachynskyi
 * @version $Id: QueryResultDataMediaType.java 63261 2016-05-20 09:00:55Z vspachyn $
 * @since 19.05.2016
 */
public class QueryResultDataMediaType {
    public final static String FLAT_DATA = "flatData";
    public final static String MULTI_LEVEL_DATA = "multiLevelData";
    public final static String MULTI_AXES_DATA = "multiAxesData";

    public final static String APPLICATION = "application";
    public final static String JSON = "+json";
    public final static String XML = "+xml";

    public final static String FLAT_DATA_JSON = APPLICATION + "/" + FLAT_DATA + JSON;
    public final static String MULTI_LEVEL_DATA_JSON = APPLICATION + "/" + MULTI_LEVEL_DATA + JSON;
    public final static String MULTI_AXES_DATA_JSON = APPLICATION + "/" + MULTI_AXES_DATA + JSON;

    public final static String FLAT_DATA_XML = APPLICATION + "/" + FLAT_DATA + XML;
    public final static String MULTI_LEVEL_DATA_XML = APPLICATION + "/" + MULTI_LEVEL_DATA + XML;
    public final static String MULTI_AXES_DATA_XML = APPLICATION + "/" + MULTI_AXES_DATA + XML;

    private static Map<String, Class<? extends ClientQueryResultData>> mediaTypeWithResultDataClassInfo =
            new HashMap<String, Class<? extends ClientQueryResultData>>(){{
                put(FLAT_DATA_JSON, ClientFlatQueryResultData.class);
                put(MULTI_LEVEL_DATA_JSON, ClientMultiLevelQueryResultData.class);
                put(MULTI_AXES_DATA_JSON, ClientMultiAxesQueryResultData.class);
            }};

    public static Class<? extends ClientQueryResultData> getResultDataType(String access) {
        for (Map.Entry<String, Class<? extends ClientQueryResultData>> entry : mediaTypeWithResultDataClassInfo.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(access)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
