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
 * @date 1/27/16 3:45PM
 * @version $Id: StringComparisonELSerializationToXMLTest.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
public class StringComparisonELSerializationToXMLTest extends FilterTest {

    @Test
    public void ensureEqualsInWhere() throws Exception {
        ClientComparison comparison = variable("sales").eq(literal("Talking Heads"));
        ClientWhere w = new ClientWhere(comparison);

        assertThat(xml(w), is("<where>\n" +
                "    <equals>\n" +
                "        <operands>\n" +
                "            <variable name=\"sales\"/>\n" +
                "            <string>\n" +
                "                <value>Talking Heads</value>\n" +
                "            </string>\n" +
                "        </operands>\n" +
                "    </equals>\n" +
                "</where>"));
    }

    @Test
    public void ensureNotEqualInWhere() throws Exception {
        ClientComparison comparison = variable("sales").lt(literal("Talking Heads"));
        ClientWhere w = new ClientWhere(comparison);

        assertThat(xml(w), is("<where>\n" +
                "    <less>\n" +
                "        <operands>\n" +
                "            <variable name=\"sales\"/>\n" +
                "            <string>\n" +
                "                <value>Talking Heads</value>\n" +
                "            </string>\n" +
                "        </operands>\n" +
                "    </less>\n" +
                "</where>"));
    }

}