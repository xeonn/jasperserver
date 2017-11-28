package com.jaspersoft.jasperserver.dto.adhoc.filters;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientWhere;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpression;
import org.junit.Test;

import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @version $Id:$
 */
public class NullELSerializationToJSONTest extends FilterTest {
    @Test
    public void ensureJson() throws Exception {
        ClientExpression aNull = nullLiteral();

        assertThat(json(aNull), is("{ }"));
    }

    @Test
    public void ensureComparisonJson() throws Exception {
        ClientExpression expression = variable("sales").notEq(nullLiteral());

        assertThat(json(expression), is(
                "{\n" +
                        "  \"operands\" : [ {\n" +
                        "    \"variable\" : {\n" +
                        "      \"name\" : \"sales\"\n" +
                        "    }\n" +
                        "  }, {\n" +
                        "    \"NULL\" : { }\n" +
                        "  } ]\n" +
                        "}"));
    }


    @Test
    public void ensureFilterComparisonJson() throws Exception {
        ClientExpression expression = variable("sales").notEq(nullLiteral());
        ClientWhere w = new ClientWhere(expression);

        assertThat(json(w), is(
                "{\n" +
                        "  \"filterExpressionObject\" : {\n" +
                        "    \"notEqual\" : {\n" +
                        "      \"operands\" : [ {\n" +
                        "        \"variable\" : {\n" +
                        "          \"name\" : \"sales\"\n" +
                        "        }\n" +
                        "      }, {\n" +
                        "        \"NULL\" : { }\n" +
                        "      } ]\n" +
                        "    }\n" +
                        "  }\n" +
                        "}"));
    }

    @Test
    public void ensureFilterFunctionJson() throws Exception {
        ClientExpression expression = function("has", variable("sales"), nullLiteral());
        ClientWhere w = new ClientWhere(expression);

        assertThat(json(w), is(
                "{\n" +
                        "  \"filterExpressionObject\" : {\n" +
                        "    \"function\" : {\n" +
                        "      \"operands\" : [ {\n" +
                        "        \"variable\" : {\n" +
                        "          \"name\" : \"sales\"\n" +
                        "        }\n" +
                        "      }, {\n" +
                        "        \"NULL\" : { }\n" +
                        "      } ],\n" +
                        "      \"functionName\" : \"has\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}"));
    }

}