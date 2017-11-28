package com.jaspersoft.jasperserver.dto.adhoc.filters;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientWhere;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.membership.ClientIn;
import org.junit.Test;

import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.function;
import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.literal;
import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.range;
import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.variable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @date 1/27/16 3:45PM
 * @version $Id: InOperatorELSerializationToXMLTest.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
public class InOperatorELSerializationToXMLTest extends FilterTest {

    @Test
    public void ensureInList_inWhere() throws Exception {
        ClientIn in = variable("sales").in(literal(1), literal(2), literal(3));
        ClientWhere w = new ClientWhere(in);

        String xmlString = "<where>\n" +
                "    <in>\n" +
                "        <operands>\n" +
                "            <variable name=\"sales\"/>\n" +
                "            <list>\n" +
                "                <items>\n" +
                "                    <integer>\n" +
                "                        <value>1</value>\n" +
                "                    </integer>\n" +
                "                    <integer>\n" +
                "                        <value>2</value>\n" +
                "                    </integer>\n" +
                "                    <integer>\n" +
                "                        <value>3</value>\n" +
                "                    </integer>\n" +
                "                </items>\n" +
                "            </list>\n" +
                "        </operands>\n" +
                "    </in>\n" +
                "</where>";

        assertThat(xml(w), is(xmlString));
    }

    @Test
    public void ensureInRange_inWhere() throws Exception {
        ClientIn in = variable("sales").in(range(1, 20));
        ClientWhere w = new ClientWhere(in);

        String xmlString = "<where>\n" +
                "    <in>\n" +
                "        <operands>\n" +
                "            <variable name=\"sales\"/>\n" +
                "            <range>\n" +
                "                <start>\n" +
                "                    <integer>\n" +
                "                        <value>1</value>\n" +
                "                    </integer>\n" +
                "                </start>\n" +
                "                <end>\n" +
                "                    <integer>\n" +
                "                        <value>20</value>\n" +
                "                    </integer>\n" +
                "                </end>\n" +
                "            </range>\n" +
                "        </operands>\n" +
                "    </in>\n" +
                "</where>";

        assertThat(xml(w), is(xmlString));
    }

    @Test
    public void ensureInRange_inWhere_variable() throws Exception {
        ClientIn in = variable("sales").in(range(variable("a"), variable("b")));
        ClientWhere w = new ClientWhere(in);

        String xmlString = "<where>\n" +
                "    <in>\n" +
                "        <operands>\n" +
                "            <variable name=\"sales\"/>\n" +
                "            <range>\n" +
                "                <start>\n" +
                "                    <variable name=\"a\"/>\n" +
                "                </start>\n" +
                "                <end>\n" +
                "                    <variable name=\"b\"/>\n" +
                "                </end>\n" +
                "            </range>\n" +
                "        </operands>\n" +
                "    </in>\n" +
                "</where>";

        assertThat(xml(w), is(xmlString));
    }

    @Test
    public void ensureInRange_inWhere_function() throws Exception {
        ClientIn in = variable("sales").in(range(function("attribute", literal("birth_year")), function("attribute", literal("start_year"))));
        ClientWhere w = new ClientWhere(in);

        String xmlString = "<where>\n" +
                "    <in>\n" +
                "        <operands>\n" +
                "            <variable name=\"sales\"/>\n" +
                "            <range>\n" +
                "                <start>\n" +
                "                    <function>\n" +
                "                        <operands>\n" +
                "                            <string>\n" +
                "                                <value>birth_year</value>\n" +
                "                            </string>\n" +
                "                        </operands>\n" +
                "                        <functionName>attribute</functionName>\n" +
                "                    </function>\n" +
                "                </start>\n" +
                "                <end>\n" +
                "                    <function>\n" +
                "                        <operands>\n" +
                "                            <string>\n" +
                "                                <value>start_year</value>\n" +
                "                            </string>\n" +
                "                        </operands>\n" +
                "                        <functionName>attribute</functionName>\n" +
                "                    </function>\n" +
                "                </end>\n" +
                "            </range>\n" +
                "        </operands>\n" +
                "    </in>\n" +
                "</where>";

        assertThat(xml(w), is(xmlString));
    }



    @Test
    public void ensureInRange_inWhere_string() throws Exception {
        ClientIn in = variable("city").in(range("a", "m"));
        ClientWhere w = new ClientWhere(in);

        String xmlString = "<where>\n" +
                "    <in>\n" +
                "        <operands>\n" +
                "            <variable name=\"city\"/>\n" +
                "            <range>\n" +
                "                <start>\n" +
                "                    <string>\n" +
                "                        <value>a</value>\n" +
                "                    </string>\n" +
                "                </start>\n" +
                "                <end>\n" +
                "                    <string>\n" +
                "                        <value>m</value>\n" +
                "                    </string>\n" +
                "                </end>\n" +
                "            </range>\n" +
                "        </operands>\n" +
                "    </in>\n" +
                "</where>";

        assertThat(xml(w), is(xmlString));
    }

    @Test
    public void ensureInRange_inWhere_integer() throws Exception {
        ClientIn in = variable("city").in(range(1, 20));
        ClientWhere w = new ClientWhere(in);

        String xmlString = "<where>\n" +
                "    <in>\n" +
                "        <operands>\n" +
                "            <variable name=\"city\"/>\n" +
                "            <range>\n" +
                "                <start>\n" +
                "                    <integer>\n" +
                "                        <value>1</value>\n" +
                "                    </integer>\n" +
                "                </start>\n" +
                "                <end>\n" +
                "                    <integer>\n" +
                "                        <value>20</value>\n" +
                "                    </integer>\n" +
                "                </end>\n" +
                "            </range>\n" +
                "        </operands>\n" +
                "    </in>\n" +
                "</where>";

        assertThat(xml(w), is(xmlString));
    }


}