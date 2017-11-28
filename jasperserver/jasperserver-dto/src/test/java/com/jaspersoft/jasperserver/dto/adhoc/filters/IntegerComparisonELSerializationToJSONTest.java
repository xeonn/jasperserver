package com.jaspersoft.jasperserver.dto.adhoc.filters;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientWhere;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpression;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientLiteral;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.ClientComparison;
import org.junit.Test;

import java.util.ArrayList;

import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.literal;
import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.variable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 *
 * @author Grant Bacon <gbacon@tibco.com>
 * @version $Id:$
 */
public class IntegerComparisonELSerializationToJSONTest extends FilterTest {
    @Test
    public void ensureGreaterOrEqual() throws Exception {
        ClientComparison comparison = variable("sales").gtOrEq(literal(5));

        assertThat(json(comparison), is("{\n" +
                "  \"operands\" : [ {\n" +
                "    \"variable\" : {\n" +
                "      \"name\" : \"sales\"\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"integer\" : {\n" +
                "      \"value\" : 5\n" +
                "    }\n" +
                "  } ]\n" +
                "}"));
    }

    @Test
    public void ensureGreaterOrEqualInWhere() throws Exception {
        ClientComparison comparison = variable("sales").gtOrEq(literal(5));
        ClientWhere w = new ClientWhere(comparison);

        assertThat(json(w), is("{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"greaterOrEqual\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"sales\"\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"integer\" : {\n" +
                "          \"value\" : 5\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  }\n" +
                "}"));
    }

    @Test
    public void ensureLessOrEqualInWhere() throws Exception {
        ClientComparison comparison = variable("sales").ltOrEq(literal(5));
        ClientWhere w = new ClientWhere(comparison);

        assertThat(json(w), is("{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"lessOrEqual\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"sales\"\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"integer\" : {\n" +
                "          \"value\" : 5\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  }\n" +
                "}"));
    }


    @Test
    public void ensureLessInWhere() throws Exception {
        ClientComparison comparison = variable("sales").lt(literal(5));
        ClientWhere w = new ClientWhere(comparison);

        assertThat(json(w), is("{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"less\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"sales\"\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"integer\" : {\n" +
                "          \"value\" : 5\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  }\n" +
                "}"));
    }

    @Test
    public void ensureGreaterInWhere() throws Exception {
        ClientComparison comparison = variable("sales").gt(literal(5));
        ClientWhere w = new ClientWhere(comparison);

        assertThat(json(w), is("{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"greater\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"sales\"\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"integer\" : {\n" +
                "          \"value\" : 5\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  }\n" +
                "}"));
    }

    @Test
    public void ensureGreaterOrEqualNullReference() throws Exception {
        ClientComparison comparison = variable("sales").gtOrEq((ClientLiteral)null);

        assertThat(json(comparison), is("{\n" +
                "  \"operands\" : [ {\n" +
                "    \"variable\" : {\n" +
                "      \"name\" : \"sales\"\n" +
                "    }\n" +
                "  }, null ]\n" +
                "}"));
    }


    @Test
    public void ensureGreaterOrEqualOfNullReferences() throws Exception {
        ClientComparison comparison = ClientComparison.gtOrEq(null, null);

        assertThat(json(comparison), is("{\n" +
                "  \"operands\" : [ null, null ]\n" +
                "}"));
    }

    @Test
    public void ensureGreaterOrEqualEmpty() throws Exception {
        ClientComparison comparison = ClientComparison.createComparison("greaterOrEqual", new ArrayList<ClientExpression>());

        assertThat(json(comparison), is("{ }"));
    }
}