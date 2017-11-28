package com.jaspersoft.jasperserver.dto.adhoc.filters;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientWhere;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientOperator;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientVariable;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientDecimal;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientInteger;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientString;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.ClientFunction;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.membership.ClientIn;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.range.ClientRange;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;


/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @date 1/27/16 3:47PM
 * @version $Id: InOperatorELDeserializationFromJSONTest.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
public class InOperatorELDeserializationFromJSONTest extends FilterTest {

    @Test
    public void ensureWhereVariableInIntegerRange() throws Exception {
        String jsonString = "{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"in\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"sales\"\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"range\" : {\n" +
                "          \"start\" : {\n" +
                "            \"boundary\" : {\n" +
                "              \"integer\" : {\n" +
                "                \"value\" : 1\n" +
                "              }\n" +
                "            }\n" +
                "          },\n" +
                "          \"end\" : {\n" +
                "            \"boundary\" : {\n" +
                "              \"integer\" : {\n" +
                "                \"value\" : 20\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  }\n" +
                "}";


        ClientWhere w = dtoFromJSONString(jsonString, ClientWhere.class);
        ClientIn in = (ClientIn) w.getFilterExpressionObject();

        assertThat(in, is(instanceOf(ClientOperator.class)));
        assertThat(in.getOperator(), is(ClientIn.OPERATOR_ID));
        assertThat(in.getOperands().get(1), is(instanceOf(ClientRange.class)));
        assertThat(in.getOperands().get(0), is(instanceOf(ClientVariable.class)));
        assertThat(((ClientInteger) ((ClientRange) in.getOperands().get(1)).getStart().getBoundary()).getValue(), is(1));
        assertThat(((ClientInteger) ((ClientRange) in.getOperands().get(1)).getEnd().getBoundary()).getValue(), is(20));
    }

    @Test
    public void ensureWhereVariableInStringRange() throws Exception {
        String jsonString = "{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"in\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"user_name\"\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"range\" : {\n" +
                "          \"start\" : {\n" +
                "            \"boundary\" : {\n" +
                "              \"string\" : {\n" +
                "                \"value\" : \"a\"\n" +
                "              }\n" +
                "            }\n" +
                "          },\n" +
                "          \"end\" : {\n" +
                "            \"boundary\" : {\n" +
                "              \"string\" : {\n" +
                "                \"value\" : \"z\"\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  }\n" +
                "}";

        ClientWhere w = dtoFromJSONString(jsonString, ClientWhere.class);
        ClientIn in = (ClientIn) w.getFilterExpressionObject();

        assertThat(in, is(instanceOf(ClientOperator.class)));
        assertThat(in.getOperator(), is(ClientIn.OPERATOR_ID));
        assertThat(in.getOperands().get(1), is(instanceOf(ClientRange.class)));
        assertThat(in.getOperands().get(0), is(instanceOf(ClientVariable.class)));
        assertThat(((ClientString) ((ClientRange) in.getOperands().get(1)).getStart().getBoundary()).getValue(), is("a"));
        assertThat(((ClientString) ((ClientRange) in.getOperands().get(1)).getEnd().getBoundary()).getValue(), is("z"));
    }

    @Test
    public void ensureWhereVariableInDecimalRange() throws Exception {
        String jsonString = "{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"in\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"pH\"\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"range\" : {\n" +
                "          \"start\" : {\n" +
                "            \"boundary\" : {\n" +
                "              \"decimal\" : {\n" +
                "                \"value\" : 0.0\n" +
                "              }\n" +
                "            }\n" +
                "          },\n" +
                "          \"end\" : {\n" +
                "            \"boundary\" : {\n" +
                "              \"decimal\" : {\n" +
                "                \"value\" : 14.0\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  }\n" +
                "}";

        ClientWhere w = dtoFromJSONString(jsonString, ClientWhere.class);
        ClientIn in = (ClientIn) w.getFilterExpressionObject();

        assertThat(in, is(instanceOf(ClientOperator.class)));
        assertThat(in.getOperator(), is(ClientIn.OPERATOR_ID));
        assertThat(in.getOperands().get(1), is(instanceOf(ClientRange.class)));
        assertThat(in.getOperands().get(0), is(instanceOf(ClientVariable.class)));
        assertThat(((ClientDecimal) ((ClientRange) in.getOperands().get(1)).getStart().getBoundary()).getValue(), is(Double.valueOf("0.0")));
        assertThat(((ClientDecimal) ((ClientRange) in.getOperands().get(1)).getEnd().getBoundary()).getValue(), is(Double.valueOf("14.0")));
    }

    @Test
    public void ensureWhereVariableInVariableRange() throws Exception {
        String jsonString = "{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"in\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"pH\"\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"range\" : {\n" +
                "          \"start\" : {\n" +
                "            \"boundary\" : {\n" +
                "              \"variable\" : {\n" +
                "                \"name\" : \"a\"\n" +
                "              }\n" +
                "            }\n" +
                "          },\n" +
                "          \"end\" : {\n" +
                "            \"boundary\" : {\n" +
                "              \"variable\" : {\n" +
                "                \"name\" : \"b\"\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  }\n" +
                "}";

        ClientWhere w = dtoFromJSONString(jsonString, ClientWhere.class);
        ClientIn in = (ClientIn) w.getFilterExpressionObject();

        assertThat(in, is(instanceOf(ClientOperator.class)));
        assertThat(in.getOperator(), is(ClientIn.OPERATOR_ID));
        assertThat(in.getOperands().get(1), is(instanceOf(ClientRange.class)));
        assertThat(in.getOperands().get(0), is(instanceOf(ClientVariable.class)));
        assertThat(((ClientRange)in.getOperands().get(1)).getEnd().getBoundary(), is(instanceOf(ClientVariable.class)));
        assertThat(((ClientVariable)((ClientRange)in.getOperands().get(1)).getEnd().getBoundary()).getName(), is("b"));

    }

    @Test
    public void ensureWhereVariableInFunctionRange() throws Exception {
        String jsonString = "{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"in\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"sales\"\n" +
                "        }\n" +
                "      }, {\n" +
                "        \"range\" : {\n" +
                "          \"start\" : {\n" +
                "            \"boundary\" : {\n" +
                "              \"function\" : {\n" +
                "                \"operands\" : [ {\n" +
                "                  \"string\" : {\n" +
                "                    \"value\" : \"birth_year\"\n" +
                "                  }\n" +
                "                } ],\n" +
                "                \"functionName\" : \"attribute\"\n" +
                "              }\n" +
                "            }\n" +
                "          },\n" +
                "          \"end\" : {\n" +
                "            \"boundary\" : {\n" +
                "              \"function\" : {\n" +
                "                \"operands\" : [ {\n" +
                "                  \"string\" : {\n" +
                "                    \"value\" : \"start_year\"\n" +
                "                  }\n" +
                "                } ],\n" +
                "                \"functionName\" : \"attribute\"\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      } ]\n" +
                "    }\n" +
                "  }\n" +
                "}";


        ClientWhere w = dtoFromJSONString(jsonString, ClientWhere.class);
        ClientIn in = (ClientIn) w.getFilterExpressionObject();

        assertThat(in, is(instanceOf(ClientOperator.class)));
        assertThat(in.getOperator(), is(ClientIn.OPERATOR_ID));
        assertThat(in.getOperands().get(1), is(instanceOf(ClientRange.class)));
        assertThat(in.getOperands().get(0), is(instanceOf(ClientVariable.class)));
        assertThat(((ClientRange)in.getOperands().get(1)).getEnd().getBoundary(), is(instanceOf(ClientFunction.class)));
        assertThat(((ClientFunction)((ClientRange)in.getOperands().get(1)).getEnd().getBoundary()).getFunctionName(), is("attribute"));
    }

    @Test
    public void ensureWhereVariableInList_ofIntegers() throws Exception {

        String json = "{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"in\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"sales\"\n" +
                "        }\n" +
                "      },\n" +
                "       \n" +
                "          { \"integer\" : { \"value\" : 1 } },          { \"integer\" : { \"value\" : 2 } },          { \"integer\" : { \"value\" : 3 } }    ]    }\n" +
                "    \n" +
                "  }\n" +
                "}";

        ClientWhere w = dtoFromJSONString(json, ClientWhere.class);
        ClientIn in = (ClientIn) w.getFilterExpressionObject();

        assertThat(in, is(instanceOf(ClientOperator.class)));
        assertThat(in.getOperator(), is(ClientIn.OPERATOR_ID));
        assertThat(in.getOperands().size(), is(4));
        assertThat(in.getOperands().get(1), is(instanceOf(ClientInteger.class)));
    }

    @Test
    public void ensureWhereVariableInList_ofStrings() throws Exception {

        String json = "{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"in\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"sales\"\n" +
                "        }\n" +
                "      },\n" +
                "       \n" +
                "          { \"string\" : { \"value\" : \"ok\" } },          { \"string\" : { \"value\" : \"not-ok\" } },          { \"string\" : { \"value\" : \"don't care\" } }    ]    }\n" +
                "    \n" +
                "  }\n" +
                "}";

        ClientWhere w = dtoFromJSONString(json, ClientWhere.class);
        ClientIn in = (ClientIn) w.getFilterExpressionObject();

        assertThat(in, is(instanceOf(ClientOperator.class)));
        assertThat(in.getOperator(), is(ClientIn.OPERATOR_ID));
        assertThat(in.getOperands().size(), is(4));
        assertThat(in.getOperands().get(1), is(instanceOf(ClientString.class)));
    }

    @Test
    public void ensureWhereVariableInList_ofDecimals() throws Exception {

        String json = "{\n" +
                "  \"filterExpressionObject\" : {\n" +
                "    \"in\" : {\n" +
                "      \"operands\" : [ {\n" +
                "        \"variable\" : {\n" +
                "          \"name\" : \"sales\"\n" +
                "        }\n" +
                "      },\n" +
                "       \n" +
                "          { \"decimal\" : { \"value\" : 5.55 } },          { \"decimal\" : { \"value\" : 6.66 } },          { \"decimal\" : { \"value\" : 7.77 } }    ]    }\n" +
                "    \n" +
                "  }\n" +
                "}";

        ClientWhere w = dtoFromJSONString(json, ClientWhere.class);
        ClientIn in = (ClientIn) w.getFilterExpressionObject();

        assertThat(in, is(instanceOf(ClientOperator.class)));
        assertThat(in.getOperator(), is(ClientIn.OPERATOR_ID));
        assertThat(in.getOperands().size(), is(4));
        assertThat(in.getOperands().get(1), is(instanceOf(ClientDecimal.class)));
    }


}