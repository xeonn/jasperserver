package com.jaspersoft.jasperserver.dto.adhoc.filters;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientWhere;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.logical.ClientNot;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.logical.ClientAnd;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.logical.ClientOr;
import org.junit.Test;

import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.literal;
import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.variable;
import static com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.ClientLogical.and;
import static com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.ClientLogical.or;
import static com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.logical.ClientNot.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


/**
 * Created by stas on 4/8/15.
 */
public class LogicalOperatorELSerializationToJSONTest extends FilterTest {
    @Test
    public void ensureNot() throws Exception {
        ClientNot not = not(variable("sales").gtOrEq(literal(5)));

        assertThat(json(not), is("{\n" +
                "  \"operands\" : [ {\n" +
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
                "  } ],\n" +
                "  \"paren\" : true\n" +
                "}"));
    }

    @Test
    public void ensureWhereNot() throws Exception {
        ClientNot not = not(variable("sales").gtOrEq(literal(5)));

        assertThat(json(new ClientWhere(not)), is("{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"not\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"greaterOrEqual\" : {\n" +
                "          \"operands\" : [ {\n" +
                "            \"variable\" : {\n" +
                "              \"name\" : \"sales\"\n" +
                "            }\n" +
                "          }, {\n" +
                "            \"integer\" : {\n" +
                "              \"value\" : 5\n" +
                "            }\n" +
                "          } ]\n" +
                "        }\n" +
                "      } ],\n" +
                "      \"paren\" : true\n" +
                "    }\n" +
                "  }\n" +
                "}"));
    }

    @Test
    public void ensureNotAndTrue() throws Exception {
        ClientAnd and = and(not(variable("sales").gtOrEq(literal(5))), literal(1));

        assertThat(json(and), is("{\n" +
                "  \"operands\" : [ {\n" +
                "    \"not\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"greaterOrEqual\" : {\n" +
                "          \"operands\" : [ {\n" +
                "            \"variable\" : {\n" +
                "              \"name\" : \"sales\"\n" +
                "            }\n" +
                "          }, {\n" +
                "            \"integer\" : {\n" +
                "              \"value\" : 5\n" +
                "            }\n" +
                "          } ]\n" +
                "        }\n" +
                "      } ],\n" +
                "      \"paren\" : true\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"integer\" : {\n" +
                "      \"value\" : 1\n" +
                "    }\n" +
                "  } ]\n" +
                "}"));
    }

    @Test
    public void ensureWhereAnd() throws Exception {

        ClientAnd and = and(variable("sales").gtOrEq(literal(1)), variable("sales").ltOrEq(literal(250)));

        assertThat(json(new ClientWhere(and)), is("{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"and\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"greaterOrEqual\" : {\n" +
                "          \"operands\" : [ {\n" +
                "            \"variable\" : {\n" +
                "              \"name\" : \"sales\"\n" +
                "            }\n" +
                "          }, {\n" +
                "            \"integer\" : {\n" +
                "              \"value\" : 1\n" +
                "            }\n" +
                "          } ]\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"lessOrEqual\" : {\n" +
                "          \"operands\" : [ {\n" +
                "            \"variable\" : {\n" +
                "              \"name\" : \"sales\"\n" +
                "            }\n" +
                "          }, {\n" +
                "            \"integer\" : {\n" +
                "              \"value\" : 250\n" +
                "            }\n" +
                "          } ]\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  }\n" +
                "}"));
    }

    @Test
    public void ensureWhereOr() throws Exception {

        ClientOr or = or(variable("sales").gtOrEq(literal(1)), variable("sales").ltOrEq(literal(250)));

        assertThat(json(new ClientWhere(or)), is("{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"or\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"greaterOrEqual\" : {\n" +
                "          \"operands\" : [ {\n" +
                "            \"variable\" : {\n" +
                "              \"name\" : \"sales\"\n" +
                "            }\n" +
                "          }, {\n" +
                "            \"integer\" : {\n" +
                "              \"value\" : 1\n" +
                "            }\n" +
                "          } ]\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"lessOrEqual\" : {\n" +
                "          \"operands\" : [ {\n" +
                "            \"variable\" : {\n" +
                "              \"name\" : \"sales\"\n" +
                "            }\n" +
                "          }, {\n" +
                "            \"integer\" : {\n" +
                "              \"value\" : 250\n" +
                "            }\n" +
                "          } ]\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  }\n" +
                "}"));
    }
}