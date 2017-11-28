package com.jaspersoft.jasperserver.dto.adhoc.filters;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientWhere;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.ClientLogical;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.logical.ClientNot;
import org.junit.Test;

import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.literal;
import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.variable;
import static com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.ClientLogical.and;
import static com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.logical.ClientNot.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


/**
 * Created by stas on 4/8/15.
 * @version $Id: LogicalOperatorELSerializationToXMLTest.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
public class LogicalOperatorELSerializationToXMLTest extends FilterTest {
    @Test
    public void ensureNot() throws Exception {
        ClientNot not = not(variable("sales").gtOrEq(literal(5)));

        assertThat(xml(not), is("<not>\n" +
                "    <operands>\n" +
                "        <greaterOrEqual>\n" +
                "            <operands>\n" +
                "                <variable name=\"sales\"/>\n" +
                "                <integer>\n" +
                "                    <value>5</value>\n" +
                "                </integer>\n" +
                "            </operands>\n" +
                "        </greaterOrEqual>\n" +
                "    </operands>\n" +
                "    <paren>true</paren>\n" +
                "</not>"));
    }

    @Test
    public void ensureWhereNot() throws Exception {
        ClientNot not = not(variable("sales").gtOrEq(literal(5)));

        assertThat(xml(new ClientWhere(not)), is("<where>\n" +
                "    <not>\n" +
                "        <operands>\n" +
                "            <greaterOrEqual>\n" +
                "                <operands>\n" +
                "                    <variable name=\"sales\"/>\n" +
                "                    <integer>\n" +
                "                        <value>5</value>\n" +
                "                    </integer>\n" +
                "                </operands>\n" +
                "            </greaterOrEqual>\n" +
                "        </operands>\n" +
                "        <paren>true</paren>\n" +
                "    </not>\n" +
                "</where>"));
    }

    @Test
    public void ensureNotAndTrue() throws Exception {
        ClientLogical not = and(not(variable("sales").gtOrEq(literal(5))), literal(1));

        assertThat(xml(not), is("<and>\n" +
                "    <operands>\n" +
                "        <not>\n" +
                "            <operands>\n" +
                "                <greaterOrEqual>\n" +
                "                    <operands>\n" +
                "                        <variable name=\"sales\"/>\n" +
                "                        <integer>\n" +
                "                            <value>5</value>\n" +
                "                        </integer>\n" +
                "                    </operands>\n" +
                "                </greaterOrEqual>\n" +
                "            </operands>\n" +
                "            <paren>true</paren>\n" +
                "        </not>\n" +
                "        <integer>\n" +
                "            <value>1</value>\n" +
                "        </integer>\n" +
                "    </operands>\n" +
                "</and>"));
    }

}