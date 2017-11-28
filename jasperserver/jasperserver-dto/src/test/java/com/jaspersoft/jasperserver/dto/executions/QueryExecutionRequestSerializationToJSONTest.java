package com.jaspersoft.jasperserver.dto.executions;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientMultiLevelQuery;
import com.jaspersoft.jasperserver.dto.adhoc.query.ClientQuery;
import com.jaspersoft.jasperserver.dto.adhoc.query.ClientWhere;
import com.jaspersoft.jasperserver.dto.adhoc.query.MultiLevelQueryBuilder;
import com.jaspersoft.jasperserver.dto.adhoc.query.QueryExecutionRequestTest;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpression;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientRelativeDateRange;
import com.jaspersoft.jasperserver.dto.adhoc.query.field.ClientQueryField;
import com.jaspersoft.jasperserver.dto.adhoc.query.field.ClientQueryGroup;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @date 1/27/16 3:47PM
 * @version $Id: QueryExecutionRequestSerializationToJSONTest.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
public class QueryExecutionRequestSerializationToJSONTest extends QueryExecutionRequestTest {

    @Test
    public void ensureSelectZeroField() throws Exception {
        ClientMultiLevelQuery cq = MultiLevelQueryBuilder
                .select()
                .build();


        assertThat(json(request(cq)), is("{\n" +
                "  \"query\" : {\n" +
                "    \"select\" : { }\n" +
                "  },\n" +
                "  \"dataSourceUri\" : \"/public/Samples/Ad_Hoc_Views/04__Product_Results_by_Store_Type\"\n" +
                "}"));

    }

    @Test
    public void ensureSelectOneField() throws Exception {
        ClientMultiLevelQuery cq = MultiLevelQueryBuilder
                .select(new ClientQueryField().setFieldName("sales").setId("fieldName"))
                .build();


        assertThat(json(request(cq)), is("{\n" +
                "  \"query\" : {\n" +
                "    \"select\" : {\n" +
                "      \"fields\" : [ {\n" +
                "        \"id\" : \"fieldName\",\n" +
                "        \"field\" : \"sales\"\n" +
                "      } ]\n" +
                "    }\n" +
                "  },\n" +
                "  \"dataSourceUri\" : \"/public/Samples/Ad_Hoc_Views/04__Product_Results_by_Store_Type\"\n" +
                "}"));

    }


    @Test
    public void ensureSelectTwoFields() throws Exception {
        ClientMultiLevelQuery cq = MultiLevelQueryBuilder
                .select(new ClientQueryField().setFieldName("sales").setId("fieldName"),
                        new ClientQueryField().setFieldName("city").setId("fieldName2"))
                .build();


        assertThat(json(request(cq)), is("{\n" +
                "  \"query\" : {\n" +
                "    \"select\" : {\n" +
                "      \"fields\" : [ {\n" +
                "        \"id\" : \"fieldName\",\n" +
                "        \"field\" : \"sales\"\n" +
                "      }, {\n" +
                "        \"id\" : \"fieldName2\",\n" +
                "        \"field\" : \"city\"\n" +
                "      } ]\n" +
                "    }\n" +
                "  },\n" +
                "  \"dataSourceUri\" : \"/public/Samples/Ad_Hoc_Views/04__Product_Results_by_Store_Type\"\n" +
                "}"));
    }

    @Test
    public void ensureSelectOneField_groupByCity() throws Exception {
        ClientQuery cq = MultiLevelQueryBuilder
                .select(new ClientQueryField().setFieldName("sales").setId("fieldName"))
                .groupBy(new ClientQueryGroup().setFieldName("city").setId("g1"))
                .build();


        assertThat(json(request((ClientMultiLevelQuery) cq)), is("{\n" +
                "  \"query\" : {\n" +
                "    \"select\" : {\n" +
                "      \"fields\" : [ {\n" +
                "        \"id\" : \"fieldName\",\n" +
                "        \"field\" : \"sales\"\n" +
                "      } ]\n" +
                "    },\n" +
                "    \"groupBy\" : [ {\n" +
                "      \"group\" : {\n" +
                "        \"id\" : \"g1\",\n" +
                "        \"field\" : \"city\"\n" +
                "      }\n" +
                "    } ]\n" +
                "  },\n" +
                "  \"dataSourceUri\" : \"/public/Samples/Ad_Hoc_Views/04__Product_Results_by_Store_Type\"\n" +
                "}"));

    }

    @Test
    public void ensureWhereParameterIsRelativeDateRange() throws Exception {
        ClientQuery cq = MultiLevelQueryBuilder
                .select(new ClientQueryField().setFieldName("sales").setId("fieldName"))
                .build();

        ClientWhere where = new ClientWhere();
        Map<String, ClientExpression> params = new HashMap<String, ClientExpression>(1);
        params.put("DateP", new ClientRelativeDateRange().setValue("WEEK-1"));
        where.setParameters(params);
        cq.setWhere(where);

        String actualJson = json(request((ClientMultiLevelQuery) cq));
        assertThat(actualJson, is("{\n" +
                "  \"query\" : {\n" +
                "    \"select\" : {\n" +
                "      \"fields\" : [ {\n" +
                "        \"id\" : \"fieldName\",\n" +
                "        \"field\" : \"sales\"\n" +
                "      } ]\n" +
                "    },\n" +
                "    \"where\" : {\n" +
                "      \"parameters\" : [ {\n" +
                "        \"name\" : \"DateP\",\n" +
                "        \"expression\" : {\n" +
                "          \"relativeDateRange\" : {\n" +
                "            \"value\" : \"WEEK-1\"\n" +
                "          }\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  },\n" +
                "  \"dataSourceUri\" : \"/public/Samples/Ad_Hoc_Views/04__Product_Results_by_Store_Type\"\n" +
                "}"));
    }
}