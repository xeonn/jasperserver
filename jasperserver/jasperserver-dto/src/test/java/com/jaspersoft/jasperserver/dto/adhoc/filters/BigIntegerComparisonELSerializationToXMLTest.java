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
 * @author Grant Bacon <gbacon@tibco.com>
 * @date 1/27/16 3:45PM
 * @version $Id: BigIntegerComparisonELSerializationToXMLTest.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
public class BigIntegerComparisonELSerializationToXMLTest extends FilterTest {

    @Test
    public void ensureGreaterOrEqualInWhere() throws Exception {
        ClientComparison comparison = variable("sales").gtOrEq(literal(new BigInteger("55555555555")));
        ClientWhere w = new ClientWhere(comparison);

        assertThat(xml(w), is("<where>\n" +
                "    <greaterOrEqual>\n" +
                "        <operands>\n" +
                "            <variable name=\"sales\"/>\n" +
                "            <bigInteger>\n" +
                "                <value>55555555555</value>\n" +
                "            </bigInteger>\n" +
                "        </operands>\n" +
                "    </greaterOrEqual>\n" +
                "</where>"));
    }

    @Test
    public void ensureLessInWhere() throws Exception {
        ClientComparison comparison = variable("sales").lt(literal(new BigInteger("55555555555")));
        ClientWhere w = new ClientWhere(comparison);

        assertThat(xml(w), is("<where>\n" +
                "    <less>\n" +
                "        <operands>\n" +
                "            <variable name=\"sales\"/>\n" +
                "            <bigInteger>\n" +
                "                <value>55555555555</value>\n" +
                "            </bigInteger>\n" +
                "        </operands>\n" +
                "    </less>\n" +
                "</where>"));
    }

}