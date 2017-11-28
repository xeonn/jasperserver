package com.jaspersoft.jasperserver.dto.adhoc.filters;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientWhere;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpression;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientNull;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.comparison.ClientNotEqual;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;


/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @date 1/27/16 3:45PM
 * @version $Id: NullELDeserializationFromXMLTest.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
public class NullELDeserializationFromXMLTest extends FilterTest {

    @Test
    public void ensureNull() throws Exception {
        String xml = "<NULL/>";

        ClientExpression expression = dto(xml);

        assertThat(expression, is(instanceOf(ClientNull.class)));
    }

    @Test
    public void ensureGreaterOrEqualInWhere() throws Exception {
        String xml =
                "<where>\n" +
                "    <notEqual>\n" +
                "        <operands>\n" +
                "            <variable name=\"sales\"/>\n" +
                "            <NULL/>\n" +
                "        </operands>\n" +
                "    </notEqual>\n" +
                "</where>";

        ClientWhere where = dto(xml);

        assertThat(where.getFilterExpressionObject(), is(instanceOf(ClientNotEqual.class)));

        final ClientExpression nullValue = ((ClientNotEqual) where.getFilterExpressionObject()).getOperands().get(1);
        assertThat(nullValue, is(instanceOf(ClientNull.class)));

    }
}