package com.jaspersoft.jasperserver.dto.adhoc.filters;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientWhere;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.ClientFunction;
import org.junit.Test;

import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.literal;
import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.variable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @date 1/27/16 3:47PM
 */
public class StringFunctionELSerializationToJSONTest extends FilterTest {

    @Test
    public void ensureStartsWith() throws Exception {
        ClientFunction function = variable("sales").startsWith("Talking Heads");

        assertThat(json(function), is("{\n" +
                "  \"operands\" : [ {\n" +
                "    \"variable\" : {\n" +
                "      \"name\" : \"sales\"\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"string\" : {\n" +
                "      \"value\" : \"Talking Heads\"\n" +
                "    }\n" +
                "  } ],\n" +
                "  \"functionName\" : \"startsWith\"\n" +
                "}"));

    }

    @Test
    public void ensureEndsWith() throws Exception {
        ClientFunction function = variable("sales").endsWith("Talking Heads");

        assertThat(json(function), is("{\n" +
                "  \"operands\" : [ {\n" +
                "    \"variable\" : {\n" +
                "      \"name\" : \"sales\"\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"string\" : {\n" +
                "      \"value\" : \"Talking Heads\"\n" +
                "    }\n" +
                "  } ],\n" +
                "  \"functionName\" : \"endsWith\"\n" +
                "}"));

    }

    @Test
    public void ensureContains() throws Exception {
        ClientFunction function = variable("sales").contains("Talking Heads");

        assertThat(json(function), is("{\n" +
                "  \"operands\" : [ {\n" +
                "    \"variable\" : {\n" +
                "      \"name\" : \"sales\"\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"string\" : {\n" +
                "      \"value\" : \"Talking Heads\"\n" +
                "    }\n" +
                "  } ],\n" +
                "  \"functionName\" : \"contains\"\n" +
                "}"));

    }

    @Test
    public void ensureStartsWithInWhere() throws Exception {
        ClientFunction function = variable("sales").startsWith("Talking Heads");

        assertThat(json(new ClientWhere(function)), is("{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"function\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"sales\"\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"string\" : {\n" +
                "          \"value\" : \"Talking Heads\"\n" +
                "        }\n" +
                "      } ],\n" +
                "      \"functionName\" : \"startsWith\"\n" +
                "    }\n" +
                "  }\n" +
                "}"));

    }


}