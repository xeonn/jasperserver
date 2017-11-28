package com.jaspersoft.jasperserver.dto.adhoc.filters;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientWhere;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpression;
import org.junit.Test;

import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.function;
import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.nullLiteral;
import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions.variable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @version $Id:$
 */
public class NullELSerializationToXMLTest extends FilterTest {

    @Test
    public void ensureXML() throws Exception {
        ClientExpression aNull = nullLiteral();

        assertThat(xml(aNull), is("<NULL/>"));
    }

    @Test
    public void ensureComparisonXml() throws Exception {
        ClientExpression expression = variable("sales").notEq(nullLiteral());

        assertThat(xml(expression), is(
                "<notEqual>\n" +
                        "    <operands>\n" +
                        "        <variable name=\"sales\"/>\n" +
                        "        <NULL/>\n" +
                        "    </operands>\n" +
                        "</notEqual>"));
    }


    @Test
    public void ensureFilterComparisonXml() throws Exception {
        ClientExpression expression = variable("sales").notEq(nullLiteral());
        ClientWhere w = new ClientWhere(expression);

        assertThat(xml(w), is(
                "<where>\n" +
                        "    <notEqual>\n" +
                        "        <operands>\n" +
                        "            <variable name=\"sales\"/>\n" +
                        "            <NULL/>\n" +
                        "        </operands>\n" +
                        "    </notEqual>\n" +
                        "</where>"));
    }

    @Test
    public void ensureFilterFunctionXml() throws Exception {
        ClientExpression expression = function("has", variable("sales"), nullLiteral());
        ClientWhere w = new ClientWhere(expression);

        assertThat(xml(w), is(
                "<where>\n" +
                        "    <function>\n" +
                        "        <operands>\n" +
                        "            <variable name=\"sales\"/>\n" +
                        "            <NULL/>\n" +
                        "        </operands>\n" +
                        "        <functionName>has</functionName>\n" +
                        "    </function>\n" +
                        "</where>"));
    }


}