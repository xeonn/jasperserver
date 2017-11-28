package com.jaspersoft.jasperserver.dto.adhoc.filters;

/*
 * Copyright (C) 2005 - 2016 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientBigInteger;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientBoolean;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientDate;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientDecimal;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientInteger;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientRelativeDateRange;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientString;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientTime;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientTimestamp;
import org.hamcrest.core.Is;
import org.junit.Test;

import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @date 1/21/16 12:45 PM
 * @version $Id: LiteralELDeserializationFromJSONTest.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
public class LiteralELDeserializationFromJSONTest extends FilterTest {

    @Test
    public void ensureBoolean() throws Exception {
        final String booleanLiteral = "{\"value\": true}";
        ClientBoolean cl = dtoFromJSONString(booleanLiteral, ClientBoolean.class);

        assertThat(cl, Is.is(instanceOf(ClientBoolean.class)));
        assertThat(cl.getValue(), Is.is(instanceOf(Boolean.class)));
    }

    @Test
    public void ensureDateRange() throws Exception {
        final String dateRangeLiteral = "{\"value\": \"YEAR\"}";
        ClientRelativeDateRange cl = dtoFromJSONString(dateRangeLiteral, ClientRelativeDateRange.class);

        assertThat(cl, Is.is(instanceOf(ClientRelativeDateRange.class)));
        assertThat(cl.getValue(), Is.is(instanceOf(String.class)));
    }

    @Test
    public void ensureInteger() throws Exception {
        final String literal = "{\n" +
                "  \"value\" : 555\n" +
                "}";
        ClientInteger cl = dtoFromJSONString(literal, ClientInteger.class);

        assertThat(cl, Is.is(instanceOf(ClientInteger.class)));
        assertThat(cl.getValue(), Is.is(instanceOf(Integer.class)));
    }

    @Test
    public void ensureIntegerFromString() throws Exception {
        final String literal = "{\n" +
                "  \"value\" : \"999\"\n" +
                "}";
        ClientInteger cl = dtoFromJSONString(literal, ClientInteger.class);

        assertThat(cl, Is.is(instanceOf(ClientInteger.class)));
        assertThat(cl.getValue(), Is.is(instanceOf(Integer.class)));
    }

    @Test
    public void ensureBigIntegerFromString() throws Exception {
        final String literal = "{\n" +
                "  \"value\" : \"999999999999\"\n" +
                "}";
        ClientBigInteger cl = dtoFromJSONString(literal, ClientBigInteger.class);

        assertThat(cl, Is.is(instanceOf(ClientBigInteger.class)));
        assertThat(cl.getValue(), Is.is(instanceOf(BigInteger.class)));
    }

    @Test
    public void ensureString() throws Exception {
        final String literal = "{\n" +
                "  \"value\" : \"Ukraine\"\n" +
                "}";
        ClientString cl = dtoFromJSONString(literal, ClientString.class);

        assertThat(cl, Is.is(instanceOf(ClientString.class)));
        assertThat(cl.getValue(), Is.is(instanceOf(String.class)));
    }

    @Test
    public void ensureDecimalFromString() throws Exception {
        final String literal = "{\n" +
                "  \"value\" : \"9.99234345542999123456523458574238592993011193433339\"\n" +
                "}";
        ClientDecimal cl = dtoFromJSONString(literal, ClientDecimal.class);

        assertThat(cl, Is.is(instanceOf(ClientDecimal.class)));
        assertThat(cl.getValue(), Is.is(instanceOf(Double.class)));
        assertThat(cl.getValue(), Is.is(Double.valueOf("9.99234345542999123456523458574238592993011193433339")));
    }

    @Test
    public void ensureBigDecimal() throws Exception {
        final String literal = "{\n" +
                "  \"value\" : \"1.7976931348623157E310\"\n" + // Larger than Double.MAX_VALUE by 2 orders of magnitude
                "}";
        ClientDecimal cl = dtoFromJSONString(literal, ClientDecimal.class);

        assertThat(cl, Is.is(instanceOf(ClientDecimal.class)));
        assertThat(cl.getValue(), Is.is(instanceOf(Double.class)));
        assertThat(cl.getValue(), Is.is(Double.valueOf("1.7976931348623157E310")));
    }

    @Test
    public void ensureDecimal() throws Exception {
        final String literal = "{\n" +
                "  \"value\" : 6.666\n" +
                "}";
        ClientDecimal cl = dtoFromJSONString(literal, ClientDecimal.class);

        assertThat(cl, Is.is(instanceOf(ClientDecimal.class)));
        assertThat(cl.getValue(), Is.is(instanceOf(Double.class)));
    }

    @Test
    public void ensureTime() throws Exception {
        final String literal = "{\n" +
                "  \"value\" : \"23:33:15\"\n" +
                "}";
        ClientTime cl = dtoFromJSONString(literal, ClientTime.class);

        assertThat(cl, Is.is(instanceOf(ClientTime.class)));
        assertThat(cl.getValue(), Is.is(instanceOf(Time.class)));
    }

    @Test
    public void ensureTimestamp() throws Exception {
        final String literal = " {\n" +
                "  \"value\" : \"2016-01-26 23:33:15\"\n" +
                "}";
        ClientTimestamp cl = dtoFromJSONString(literal, ClientTimestamp.class);

        assertThat(cl, Is.is(instanceOf(ClientTimestamp.class)));
        assertThat(cl.getValue(), Is.is(instanceOf(Timestamp.class)));
    }

    @Test
    public void ensureDate() throws Exception {

        final String literal = "{\n" +
                "  \"value\" : \"2016-01-26\"\n" +
                "}";

        ClientDate cl = dtoFromJSONString(literal, ClientDate.class);

        assertThat(cl, Is.is(instanceOf(ClientDate.class)));
        assertThat(cl.getValue(), Is.is(instanceOf(Date.class)));
    }

}
