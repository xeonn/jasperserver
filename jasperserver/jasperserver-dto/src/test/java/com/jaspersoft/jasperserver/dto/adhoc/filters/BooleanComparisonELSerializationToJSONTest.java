package com.jaspersoft.jasperserver.dto.adhoc.filters;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientWhere;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.ClientComparison;
import org.junit.Test;

import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.literal;
import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.variable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @date 1/27/16 3:47PM
 */
public class BooleanComparisonELSerializationToJSONTest extends FilterTest {

    @Test
    public void ensureEquals() throws Exception {
        ClientComparison comparison = variable("sales").eq(literal(true));

        assertThat(json(comparison), is("{\n" +
                "  \"operands\" : [ {\n" +
                "    \"variable\" : {\n" +
                "      \"name\" : \"sales\"\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"boolean\" : {\n" +
                "      \"value\" : true\n" +
                "    }\n" +
                "  } ]\n" +
                "}"));

    }

    @Test
    public void ensureNotEqual() throws Exception {
        ClientComparison comparison = variable("sales").notEq(literal(true));

        assertThat(json(comparison), is("{\n" +
                "  \"operands\" : [ {\n" +
                "    \"variable\" : {\n" +
                "      \"name\" : \"sales\"\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"boolean\" : {\n" +
                "      \"value\" : true\n" +
                "    }\n" +
                "  } ]\n" +
                "}"));

    }

    @Test
    public void ensureEqualsInWhere() throws Exception {
        ClientComparison comparison = variable("sales").eq(literal(true));

        assertThat(json(new ClientWhere(comparison)), is("{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"equals\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"sales\"\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"boolean\" : {\n" +
                "          \"value\" : true\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  }\n" +
                "}"));

    }

    @Test
    public void ensureNotEqualInWhere() throws Exception {
        ClientComparison comparison = variable("sales").notEq(literal(true));

        assertThat(json(new ClientWhere(comparison)), is("{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"notEqual\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"sales\"\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"boolean\" : {\n" +
                "          \"value\" : true\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  }\n" +
                "}"));

    }


}