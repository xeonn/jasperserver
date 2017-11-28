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

import com.jaspersoft.jasperserver.dto.adhoc.DateFixtures;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientBigInteger;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientBoolean;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientDate;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientDecimal;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientInteger;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientRelativeDateRange;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientString;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientTime;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientTimestamp;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @date 1/21/16 12:45 PM
 * @version $Id: LiteralELSerializationToJSONTest.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
public class LiteralELSerializationToJSONTest extends FilterTest {

    @Test
    public void booleanAsLiteral() throws Exception {
        assertThat(json(new ClientBoolean(true)), is("{\n  \"value\" : true\n}"));
    }

    @Test
    public void dateRange() throws Exception {
        assertThat(json(new ClientRelativeDateRange("YEAR")), is("{\n  \"value\" : \"YEAR\"\n}"));
    }

    @Test
    public void integerAsLiteral() throws Exception {
        assertThat(json(new ClientInteger(555)), is(
                "{\n" +
                "  \"value\" : 555\n" +
                "}"));
    }

    @Test
    @Ignore("In progress: JRS-8433")
    public void bigIntegerAsLiteral_valueIsString() throws Exception {
        assertThat(json(new ClientBigInteger(new BigInteger("999999999999999999999999999999999999999999999999"))), is(
                "{\n" +
                        "  \"value\" : \"999999999999999999999999999999999999999999999999\"\n" +
                        "}"));
    }

    @Test
    @Ignore("In progress: JRS-8433")
    public void bigDecimalAsLiteral_valueIsString() throws Exception {
        assertThat(json(ClientDecimal.valueOf("9.99999999999999999999999999999999999999999999999")), is(
                "{\n" +
                        "  \"value\" : \"9.99999999999999999999999999999999999999999999999\"\n" +
                        "}"));
    }

    @Test
    public void timeAsLiteral() throws Exception {
        assertThat(json(new ClientTime(DateFixtures.TIME_15_00)), is(
                "{\n" +
                        "  \"value\" : \"15:00:00\"\n" +
                        "}"));
    }

    @Test
    public void timestampAsLiteral() throws Exception {
        assertThat(json(new ClientTimestamp(DateFixtures.DATE_2009_1_1T0_0_0)), is(
                "{\n" +
                        "  \"value\" : \"2009-01-01 00:00:00\"\n" +
                        "}"));
    }

    @Test
    public void dateAsLiteral() throws Exception {
        assertThat(json(new ClientDate(DateFixtures.DATE_1961_08_26)), is(
                "{\n" +
                        "  \"value\" : \"1961-08-26\"\n" +
                        "}"));
    }

    @Test
    public void decimalAsLiteral() throws Exception {
        assertThat(json(ClientDecimal.valueOf("5.0")), is(
                "{\n" +
                        "  \"value\" : 5.0\n" +
                        "}"));
    }

    @Test
    public void stringAsLiteral() throws Exception {
        assertThat(json(new ClientString("Talking Heads")), is(
                "{\n" +
                        "  \"value\" : \"Talking Heads\"\n" +
                        "}"));
    }


}
