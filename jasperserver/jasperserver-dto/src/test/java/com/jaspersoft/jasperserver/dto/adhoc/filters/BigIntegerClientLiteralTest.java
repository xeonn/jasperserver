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
import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientInteger;
import org.junit.Test;

import java.math.BigInteger;

import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ELUtils.isNumericString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @version $Id: BigIntegerClientLiteralTest.java 64791 2016-10-12 15:08:37Z ykovalch $
 * @date 3/10/16 10:47 AM
 */
public class BigIntegerClientLiteralTest {

    @Test
    public void ensureValid() throws Exception {
        final String testString = "999999";
        final ClientInteger clientTest = new ClientInteger(Integer.valueOf(testString));

        assert(isNumericString(testString));
        assertThat(clientTest.getValue(), is((Object) new Integer(testString)));

    }

    @Test
    public void ensureValid_withPlus() throws Exception {
        final String testString = "+999999";
        final ClientInteger clientTest = ClientInteger.valueOf(testString);

        assert(isNumericString(testString));
        assertThat(clientTest.getValue(), is((Object) new Integer(testString)));
    }

    @Test
    public void ensureValid_withMinus() throws Exception {
        final String testString = "-999999";
        final ClientInteger clientTest = ClientInteger.valueOf(testString);

        assert(isNumericString(testString));
        assertThat(clientTest.getValue(), is((Object) new Integer(testString)));
    }

    @Test
    public void ensureValid_fullwidthDigits() throws Exception {
        final String testString = "１２８１３３０８００";
        final ClientInteger clientTest = new ClientInteger(Integer.valueOf(testString));

        assert(isNumericString(testString));
        assertThat(clientTest.getValue(), is((Object) Integer.valueOf("1281330800")));
    }


    @Test
    public void ensureValid_bigInteger_fullwidthDigits() throws Exception {
        final String testString = "１２８１３３０８００４";
        final ClientBigInteger clientTest = new ClientBigInteger(new BigInteger(testString));

        assert(isNumericString(testString));
        assertThat(clientTest.getValue(), is((Object) new BigInteger("12813308004")));
    }

    @Test
    public void ensureValid_mixed_fullwidthDigits_latinDigits() throws Exception {
        final String testString = "１2８１33０８０0";
        final ClientInteger clientTest = new ClientInteger(Integer.valueOf(testString));

        assert(isNumericString(testString));
        assertThat(clientTest.getValue(), is((Object) Integer.valueOf("1281330800")));
    }

    @Test
    public void ensureValid_bigInteger_mixed_fullwidthDigits_latinDigits() throws Exception {
        final String testString = "１2８１33０８００4";
        final ClientBigInteger clientTest = new ClientBigInteger(new BigInteger(testString));

        assert(isNumericString(testString));
        assertThat(clientTest.getValue(), is((Object) new BigInteger("12813308004")));
    }

    @Test
    public void ensureValid_arabicDigits() throws Exception {
        // 0123456789
        final String testString = "٠١٢٣٤٥٦٧٨٩";
        final ClientInteger clientTest = ClientInteger.valueOf(testString);
        assertThat(isNumericString(testString), is(true));
        assertThat(clientTest.getValue(), is((Object) new Integer("0123456789")));
    }

    @Test
    public void ensureValid_arabicDigits_withMinus() throws Exception {
        // -0123456789
        String numericTest = "-٠١٢٣٤٥٦٧٨٩";
        ClientInteger clientTest = ClientInteger.valueOf(numericTest);

        assert(isNumericString(numericTest));
        assertThat(clientTest.getValue(), is(instanceOf(Integer.class)));
        assertThat(clientTest.getValue(), is((Object) new Integer("-0123456789")));
    }

    @Test
    public void ensureInvalid_null() throws Exception {
        assert(!isNumericString(null));
    }

    @Test
    public void ensureInvalid_charInMiddle_withMinus() throws Exception {
        final String testString = "-999a999";

        assert(!isNumericString(testString));
    }

    @Test
    public void ensureInvalid_lastCharLetter() throws Exception {
        final String testString = "12314392a";

        assert(!isNumericString(testString));
    }

    @Test
    public void ensureInvalid_firstCharLetter() throws Exception {
        final String testString = "b12314392";

        assert(!isNumericString(testString));
    }

    @Test
    public void ensureInvalid_secondCharMinus() throws Exception {
        final String testString = "1-12314392";

        assert(!isNumericString(testString));
    }

    @Test
    public void ensureInvalid_secondCharPlus() throws Exception {
        final String testString = "1+12314392";

        assert(!isNumericString(testString));
    }
}
