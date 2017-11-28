package com.jaspersoft.jasperserver.dto.executions;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientMultiLevelQuery;
import com.jaspersoft.jasperserver.dto.adhoc.query.QueryExecutionRequestTest;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientRelativeDateRange;
import com.jaspersoft.jasperserver.dto.adhoc.query.field.ClientQueryField;
import org.junit.Test;

import java.util.List;

import static com.jaspersoft.jasperserver.dto.matchers.IsClientQueryField.isClientQueryField;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;


/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @date 1/27/16 3:47PM
 * @version $Id: QueryExecutionDeserializationFromJSONTest.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
public class QueryExecutionDeserializationFromJSONTest extends QueryExecutionRequestTest {

    @Test
    public void ensureSelectZeroField() throws Exception {
        String jsonString = "{\n" +
                "  \"query\" : {\n" +
                "    \"select\" : {\n" +
                "      \"fields\" : [ ],\n" +
                "      \"aggregations\" : [ ]\n" +
                "    }\n" +
                "  }\n" +
                "}";

        ClientQueryExecution cq = dtoFromJSONString(jsonString, ClientMultiLevelQueryExecution.class);

        assertThat(cq.getQuery(), is(instanceOf(ClientMultiLevelQuery.class)));
        assertThat(cq.getQuery().getSelect().getFields().size(), is(0));
    }

    @Test
    public void ensureSelectOneField() throws Exception {
        String jsonString = "{\n" +
                "\"query\" : {\n" +
                "    \"select\" : {\n" +
                "      \"fields\" : [ {\n" +
                "        \"id\" : \"fieldName\",\n" +
                "        \"field\" : \"sales\"\n" +
                "      } ],\n" +
                "      \"aggregations\" : [ ]\n" +
                "    }\n" +
                "  }\n" +
                "}";

        ClientQueryExecution cq = dtoFromJSONString(jsonString, ClientMultiLevelQueryExecution.class);

        assertThat(cq.getQuery(), is(instanceOf(ClientMultiLevelQuery.class)));
        List<ClientQueryField> fields = cq.getQuery().getSelect().getFields();
        assertThat(fields.size(), is(1));

        assertThat(fields.get(0), isClientQueryField("fieldName","sales", "yyyy-MM-dd"));
    }

    @Test
    public void ensureSelectTwoFields() throws Exception {
        String jsonString = "{\n" +
                "  \"query\" : {\n" +
                "    \"select\" : {\n" +
                "      \"fields\" : [ {\n" +
                "        \"id\" : \"fieldName\",\n" +
                "        \"field\" : \"sales\"\n" +
                "      }, {\n" +
                "        \"id\" : \"fieldName2\",\n" +
                "        \"field\" : \"city\"\n" +
                "      } ],\n" +
                "      \"aggregations\" : [ ]\n" +
                "    }\n" +
                "}\n" +
                "}";

        ClientQueryExecution cq = dtoFromJSONString(jsonString, ClientMultiLevelQueryExecution.class);

        assertThat(cq.getQuery(), is(instanceOf(ClientMultiLevelQuery.class)));
        List<ClientQueryField> fields = cq.getQuery().getSelect().getFields();
        assertThat(fields.size(), is(2));

        assertThat(fields.get(0), isClientQueryField("fieldName","sales", "yyyy-MM-dd"));
        assertThat(fields.get(1), isClientQueryField("fieldName2","city"));
    }

    /**
     * Covers corrected case from <a href="http://jira.jaspersoft.com/browse/JRS-8192">JRS-8192</a>
     *
     * @throws Exception
     */
    @Test
    public void ensure_JRS8192() throws Exception {
        final String jsonString = "{\n" +
                "  \"dataSourceUri\":\"/organizations/organization_1/adhoc/topics/AdhocDemo\",\n" +
                "  \"query\":{\n" +
                "    \"select\":{\n" +
                "      \"fields\":[\n" +
                "        {\n" +
                "          \"field\":\"ShipCountry\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}";

        ClientQueryExecution qer = dtoFromJSONString(jsonString, ClientMultiLevelQueryExecution.class);

        assertThat(qer, is(notNullValue()));
        assertThat(qer.getDataSourceUri(), is("/organizations/organization_1/adhoc/topics/AdhocDemo"));
        assertThat(qer.getQuery(), is(instanceOf(ClientMultiLevelQuery.class)));
    }

    @Test
    public void ensure_whereRelativeDateRangeParameter() throws Exception {
        final String jsonString = "{\n" +
                "  \"dataSourceUri\": \"/organizations/organization_1/adhoc/topics/topicsRD/EqualsDate_2\",\n" +
                "  \"query\": {\n" +
                "    \"select\": {\n" +
                "      \"fields\": [\n" +
                "        {\n" +
                "          \"field\": \"column_string\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    \"where\": {\n" +
                "      \"parameters\": [{\n" +
                "        \"name\": \"DateP\",\n" +
                "        \"expression\": {\n" +
                "          \"relativeDateRange\": {\n" +
                "            \"value\": \"WEEK-1\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}";
        ClientQueryExecution qer = dtoFromJSONString(jsonString, ClientMultiLevelQueryExecution.class);
        assertThat(qer, is(notNullValue()));
        assertThat(qer.getQuery().getWhere().getParameters().get("DateP"), is(instanceOf(ClientRelativeDateRange.class)));
        assertThat(((ClientRelativeDateRange) qer.getQuery().getWhere().getParameters().get("DateP")).getValue(), is("WEEK-1"));
    }

}