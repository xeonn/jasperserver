package com.jaspersoft.jasperserver.dto.adhoc.filters;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientWhere;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientVariable;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientInteger;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.comparison.ClientGreaterOrEqual;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.comparison.ClientLessOrEqual;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;


/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @date 1/27/16 3:45PM
 */
public class IntegerComparisonELDeserializationFromXMLTest extends FilterTest {

    @Test
    public void ensureGreaterOrEqualInWhere() throws Exception {
        String xml = "<where>\n" +
                "    <greaterOrEqual>\n" +
                "        <operands>\n" +
                "            <variable name=\"sales\"/>\n" +
                "            <integer>\n" +
                "                <value>5</value>\n" +
                "            </integer>\n" +
                "        </operands>\n" +
                "    </greaterOrEqual>\n" +
                "</where>";

        ClientWhere where = dto(xml);

        assertThat(where.getFilterExpressionObject(), is(instanceOf(ClientGreaterOrEqual.class)));
        assertThat(((ClientGreaterOrEqual) where.getFilterExpressionObject()).getOperator(), is(ClientGreaterOrEqual.OPERATOR_ID));
        assertThat((((ClientGreaterOrEqual) where.getFilterExpressionObject()).getOperands().get(0)), is(instanceOf(ClientVariable.class)));
        assertThat((((ClientGreaterOrEqual) where.getFilterExpressionObject()).getOperands().get(1)), is(instanceOf(ClientInteger.class)));

    }

    @Test
    public void ensureLessOrEqualInWhere() throws Exception {
        String xml = "<where>\n" +
                "    <lessOrEqual>\n" +
                "        <operands>\n" +
                "            <variable name=\"sales\"/>\n" +
                "            <integer>\n" +
                "                <value>5</value>\n" +
                "            </integer>\n" +
                "        </operands>\n" +
                "    </lessOrEqual>\n" +
                "</where>";

        ClientWhere where = dto(xml);

        assertThat(where.getFilterExpressionObject(), is(instanceOf(ClientLessOrEqual.class)));
        assertThat(((ClientLessOrEqual) where.getFilterExpressionObject()).getOperator(), is(ClientLessOrEqual.OPERATOR_ID));
        assertThat((((ClientLessOrEqual) where.getFilterExpressionObject()).getOperands().get(0)), is(instanceOf(ClientVariable.class)));
        assertThat((((ClientLessOrEqual) where.getFilterExpressionObject()).getOperands().get(1)), is(instanceOf(ClientInteger.class)));
    }
}