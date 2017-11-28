package com.jaspersoft.jasperserver.dto.adhoc.filters;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientWhere;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.ClientComparison;
import org.junit.Test;

import java.math.BigInteger;

import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.literal;
import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.variable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 *
 * @author Grant Bacon <gbacon@tibco.com>
 * @version $Id: BigIntegerComparisonELSerializationToJSONTest.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
public class BigIntegerComparisonELSerializationToJSONTest extends FilterTest {
    @Test
    public void ensureGreaterOrEqual() throws Exception {
        ClientComparison comparison = variable("sales").gtOrEq(literal(new BigInteger("55555555555")));

        assertThat(json(comparison), is("{\n" +
                "  \"operands\" : [ {\n" +
                "    \"variable\" : {\n" +
                "      \"name\" : \"sales\"\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"bigInteger\" : {\n" +
                "      \"value\" : 55555555555\n" +
                "    }\n" +
                "  } ]\n" +
                "}"));
    }

    @Test
    public void ensureGreaterOrEqualInWhere() throws Exception {
        ClientComparison comparison = variable("sales").gtOrEq(literal(new BigInteger("55555555555")));
        ClientWhere w = new ClientWhere(comparison);

        assertThat(json(w), is("{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"greaterOrEqual\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"sales\"\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"bigInteger\" : {\n" +
                "          \"value\" : 55555555555\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  }\n" +
                "}"));
    }

    @Test
    public void ensureLessOrEqualInWhere() throws Exception {
        ClientComparison comparison = variable("sales").ltOrEq(literal(new BigInteger("55555555555")));
        ClientWhere w = new ClientWhere(comparison);

        assertThat(json(w), is("{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"lessOrEqual\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"sales\"\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"bigInteger\" : {\n" +
                "          \"value\" : 55555555555\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  }\n" +
                "}"));
    }


    @Test
    public void ensureLessInWhere() throws Exception {
        ClientComparison comparison = variable("sales").lt(literal(new BigInteger("55555555555")));
        ClientWhere w = new ClientWhere(comparison);

        assertThat(json(w), is("{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"less\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"sales\"\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"bigInteger\" : {\n" +
                "          \"value\" : 55555555555\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  }\n" +
                "}"));
    }

    @Test
    public void ensureGreaterInWhere() throws Exception {
        ClientComparison comparison = variable("sales").gt(literal(new BigInteger("55555555555")));
        ClientWhere w = new ClientWhere(comparison);

        assertThat(json(w), is("{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"greater\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"sales\"\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"bigInteger\" : {\n" +
                "          \"value\" : 55555555555\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  }\n" +
                "}"));
    }
}