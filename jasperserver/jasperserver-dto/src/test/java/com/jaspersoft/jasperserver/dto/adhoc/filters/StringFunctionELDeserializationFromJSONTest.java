package com.jaspersoft.jasperserver.dto.adhoc.filters;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientWhere;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientVariable;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientString;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.ClientFunction;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;


/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @date 1/27/16 3:47PM
 */
public class StringFunctionELDeserializationFromJSONTest extends FilterTest {

    public static final String STARTS_WITH = "startsWith";
    public static final String ENDS_WITH = "endsWith";
    public static final String CONTAINS = "contains";

    @Test
    public void ensureStartsWithInWhere() throws Exception {
        String json = "{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"function\" : {\n" +
                "      \"functionName\" : \"startsWith\",\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"sales\"\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"string\" : {\n" +
                "          \"value\" : \"David Byrne\"\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  }\n" +
                "}";

        ClientWhere w = dtoFromJSONString(json, ClientWhere.class);
        ClientFunction function = (ClientFunction) w.getFilterExpressionObject();

        assertThat(function, is(instanceOf(ClientFunction.class)));
        assertThat(function.getFunctionName(), is(STARTS_WITH));
        assertThat(function.getOperator(), is(ClientFunction.FUNCTION_ID));
        assertThat(function.getOperands().size(), is(2));
        assertThat(function.getOperands().get(0), is(instanceOf(ClientVariable.class)));
        assertThat(function.getOperands().get(1), is(instanceOf(ClientString.class)));
    }

    @Test
    public void ensureEndsWithInWhere() throws Exception {
        String json = "{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"function\" : {\n" +
                "      \"functionName\" : \"endsWith\",\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"sales\"\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"string\" : {\n" +
                "          \"value\" : \"David Byrne\"\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  }\n" +
                "}";

        ClientWhere w = dtoFromJSONString(json, ClientWhere.class);
        ClientFunction function = (ClientFunction) w.getFilterExpressionObject();

        assertThat(function, is(instanceOf(ClientFunction.class)));
        assertThat(function.getFunctionName(), is(ENDS_WITH));
        assertThat(function.getOperator(), is(ClientFunction.FUNCTION_ID));
        assertThat(function.getOperands().size(), is(2));
        assertThat(function.getOperands().get(0), is(instanceOf(ClientVariable.class)));
        assertThat(function.getOperands().get(1), is(instanceOf(ClientString.class)));
    }

    @Test
    public void ensureContainsInWhere() throws Exception {
        String json = "{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"function\" : {\n" +
                "      \"functionName\" : \"contains\",\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"sales\"\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"string\" : {\n" +
                "          \"value\" : \"David Byrne\"\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  }\n" +
                "}";

        ClientWhere w = dtoFromJSONString(json, ClientWhere.class);
        ClientFunction function = (ClientFunction) w.getFilterExpressionObject();

        assertThat(function, is(instanceOf(ClientFunction.class)));
        assertThat(function.getFunctionName(), is(CONTAINS));
        assertThat(function.getOperator(), is(ClientFunction.FUNCTION_ID));
        assertThat(function.getOperands().size(), is(2));
        assertThat(function.getOperands().get(0), is(instanceOf(ClientVariable.class)));
        assertThat(function.getOperands().get(1), is(instanceOf(ClientString.class)));
    }

}