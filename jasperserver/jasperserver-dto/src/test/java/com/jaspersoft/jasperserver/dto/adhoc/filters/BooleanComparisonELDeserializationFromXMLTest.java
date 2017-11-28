package com.jaspersoft.jasperserver.dto.adhoc.filters;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientWhere;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientVariable;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientInteger;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.ClientComparison;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.comparison.ClientEquals;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.comparison.ClientNotEqual;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;


/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @date 1/27/16 3:47PM
 */
public class BooleanComparisonELDeserializationFromXMLTest extends FilterTest {

    @Test
    public void ensureEqualsInWhere() throws Exception {
        String xml = "<where>\n" +
                "    <equals>\n" +
                "        <operands>\n" +
                "            <variable name=\"sales\"/>\n" +
                "            <integer>\n" +
                "                <value>5</value>\n" +
                "            </integer>\n" +
                "        </operands>\n" +
                "    </equals>\n" +
                "</where>";

        ClientWhere where = dto(xml);
        ClientComparison filters = (ClientComparison) where.getFilterExpressionObject();

        assertThat(where.getFilterExpressionObject(), is(instanceOf(ClientEquals.class)));
        assertThat(filters.getOperator(), is(ClientEquals.OPERATOR_ID));
        assertThat(filters.getOperands().get(0), is(instanceOf(ClientVariable.class)));
        assertThat(filters.getOperands().get(1), is(instanceOf(ClientInteger.class)));

    }

    @Test
    public void ensureNotEqualInWhere() throws Exception {
        String xml = "<where>\n" +
                "    <notEqual>\n" +
                "        <operands>\n" +
                "            <variable name=\"sales\"/>\n" +
                "            <integer>\n" +
                "                <value>5</value>\n" +
                "            </integer>\n" +
                "        </operands>\n" +
                "    </notEqual>\n" +
                "</where>";

        ClientWhere where = dto(xml);


        assertThat(where.getFilterExpressionObject(), is(instanceOf(ClientNotEqual.class)));
        ClientComparison filters = (ClientComparison) where.getFilterExpressionObject();

        assertThat(filters.getOperator(), is(ClientNotEqual.OPERATOR_ID));
        assertThat((filters.getOperands().get(0)), is(instanceOf(ClientVariable.class)));
        assertThat((filters.getOperands().get(1)), is(instanceOf(ClientInteger.class)));

    }

}