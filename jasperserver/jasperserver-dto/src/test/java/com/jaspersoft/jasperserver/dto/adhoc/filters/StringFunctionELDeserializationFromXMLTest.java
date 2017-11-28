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
public class StringFunctionELDeserializationFromXMLTest extends FilterTest {

    public static final String STARTS_WITH = "startsWith";
    public static final String ENDS_WITH = "endsWith";

    @Test
    public void ensureStartsWithInWhere() throws Exception {
        String xml = "<where>\n" +
                "    <function>\n" +
                "       <functionName>startsWith</functionName>\n " +
                "        <operands>\n" +
                "            <variable name=\"sales\"/>\n" +
                "            <string>\n" +
                "                <value>David Byrne</value>\n" +
                "            </string>\n" +
                "        </operands>\n" +
                "    </function>\n" +
                "</where>";

        ClientWhere where = dto(xml);
        ClientFunction filters = (ClientFunction) where.getFilterExpressionObject();

        assertThat(where.getFilterExpressionObject(), is(instanceOf(ClientFunction.class)));
        assertThat(filters.getOperator(), is(ClientFunction.FUNCTION_ID));
        assertThat(filters.getFunctionName(), is(STARTS_WITH));
        assertThat(filters.getOperands().get(0), is(instanceOf(ClientVariable.class)));
        assertThat(filters.getOperands().get(1), is(instanceOf(ClientString.class)));
        assertThat(((ClientString) filters.getOperands().get(1)).getValue().toString(), is("David Byrne"));

    }

    @Test
    public void ensureEndsWithInWhere() throws Exception {
        String xml = "<where>\n" +
                "    <function>\n" +
                "        <functionName>endsWith</functionName>" +
                "        <operands>\n" +
                "            <variable name=\"sales\"/>\n" +
                "            <string>\n" +
                "                <value>David Byrne</value>\n" +
                "            </string>\n" +
                "        </operands>\n" +
                "    </function>\n" +
                "</where>";

        ClientWhere where = dto(xml);
        ClientFunction filters = (ClientFunction) where.getFilterExpressionObject();

        assertThat(filters.getOperator(), is(ClientFunction.FUNCTION_ID));
        assertThat(filters.getFunctionName(), is(ENDS_WITH));
        assertThat((filters.getOperands().get(0)), is(instanceOf(ClientVariable.class)));
        assertThat((filters.getOperands().get(1)), is(instanceOf(ClientString.class)));
        assertThat(((ClientString) filters.getOperands().get(1)).getValue().toString(), is("David Byrne"));
    }

}