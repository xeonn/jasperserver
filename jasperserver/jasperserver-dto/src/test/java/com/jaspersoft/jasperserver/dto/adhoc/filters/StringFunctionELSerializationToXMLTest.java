package com.jaspersoft.jasperserver.dto.adhoc.filters;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientWhere;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.operator.ClientFunction;
import org.junit.Test;

import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.variable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @date 1/27/16 3:45PM
 * @version $Id: StringFunctionELSerializationToXMLTest.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
public class StringFunctionELSerializationToXMLTest extends FilterTest {

    @Test
    public void ensureStartsWithInWhere() throws Exception {
        ClientFunction function = variable("sales").startsWith("Talking Heads");
        ClientWhere w = new ClientWhere(function);

        assertThat(xml(w), is("<where>\n" +
                "    <function>\n" +
                "        <operands>\n" +
                "            <variable name=\"sales\"/>\n" +
                "            <string>\n" +
                "                <value>Talking Heads</value>\n" +
                "            </string>\n" +
                "        </operands>\n" +
                "        <functionName>startsWith</functionName>\n" +
                "    </function>\n" +
                "</where>"));
    }

    @Test
    public void ensureEndsWithInWhere() throws Exception {
        ClientFunction function = variable("sales").endsWith("Talking Heads");
        ClientWhere w = new ClientWhere(function);

        assertThat(xml(w), is("<where>\n" +
                "    <function>\n" +
                "        <operands>\n" +
                "            <variable name=\"sales\"/>\n" +
                "            <string>\n" +
                "                <value>Talking Heads</value>\n" +
                "            </string>\n" +
                "        </operands>\n" +
                "        <functionName>endsWith</functionName>\n" +
                "    </function>\n" +
                "</where>"));
    }


    @Test
    public void ensureContainsInWhere() throws Exception {
        ClientFunction function = variable("sales").contains("Talking Heads");
        ClientWhere w = new ClientWhere(function);

        assertThat(xml(w), is("<where>\n" +
                "    <function>\n" +
                "        <operands>\n" +
                "            <variable name=\"sales\"/>\n" +
                "            <string>\n" +
                "                <value>Talking Heads</value>\n" +
                "            </string>\n" +
                "        </operands>\n" +
                "        <functionName>contains</functionName>\n" +
                "    </function>\n" +
                "</where>"));
    }

}