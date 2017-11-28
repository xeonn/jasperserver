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
 * Created by stas on 4/8/15.
 * @version $Id: NullELDeserializationFromJSONTest.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
public class NullELDeserializationFromJSONTest extends FilterTest {

    @Test
    public void ensureEmptyObject() throws Exception {
        String json = "{ }";

        ClientExpression expression = dtoFromJSONString(json, ClientNull.class);

        assertThat(expression, is(instanceOf(ClientNull.class)));
    }

    @Test
    public void ensureNullObject() throws Exception {
        String json = "{\n" +
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
                "}";
        ClientWhere w = dtoFromJSONString(json, ClientWhere.class);


        assertThat(w.getFilterExpressionObject(), is(instanceOf(ClientNotEqual.class)));
        final ClientExpression nullValue = ((ClientNotEqual) w.getFilterExpressionObject()).getOperands().get(1);
        assertThat(nullValue, is(instanceOf(ClientNull.class)));
    }

}