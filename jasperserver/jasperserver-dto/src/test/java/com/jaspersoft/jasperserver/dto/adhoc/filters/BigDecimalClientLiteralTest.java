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

import com.jaspersoft.jasperserver.dto.adhoc.query.el.literal.ClientDecimal;
import org.junit.Test;

import java.math.BigDecimal;

import static com.jaspersoft.jasperserver.dto.adhoc.query.el.ELUtils.isFloatingPointString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @version $Id: BigDecimalClientLiteralTest.java 64791 2016-10-12 15:08:37Z ykovalch $
 * @date 3/10/16 10:47 AM
 */
public class BigDecimalClientLiteralTest {

    @Test
    public void ensureValid() throws Exception {
        final String testString = "0.999999";
        final ClientDecimal clientTest = ClientDecimal.valueOf(testString);

        assert(isFloatingPointString(testString));
        assertThat(clientTest.getValue(), is((Object) Double.valueOf(testString)));

    }

    @Test
    public void ensureValid_noLeadingDigit() throws Exception {
        final String testString = ".999999";
        final ClientDecimal clientTest = ClientDecimal.valueOf(testString);

        assert(isFloatingPointString(testString));
        assertThat(clientTest.getValue(), is((Object) Double.valueOf(testString)));

    }

    @Test
    public void ensureValid_1enegative5() throws Exception {
        final String testString = "1e-5";
        final ClientDecimal clientTest = ClientDecimal.valueOf(testString);

        assert(isFloatingPointString(testString));
        assertThat(clientTest.getValue(), is((Object) Double.valueOf(testString)));
    }

    @Test
    public void ensureValid_negativeExponential() throws Exception {
        final String testString = "1.234e-54";
        final ClientDecimal clientTest = ClientDecimal.valueOf(testString);

        assert(isFloatingPointString(testString));
        assertThat(clientTest.getValue(), is((Object) Double.valueOf(testString)));
    }

    @Test
    public void ensureValid_positiveExponential() throws Exception {
        final String testString = "1.234e+54";
        final ClientDecimal clientTest = ClientDecimal.valueOf(testString);

        assert(isFloatingPointString(testString));
        assertThat(clientTest.getValue(), is((Object) Double.valueOf(testString)));
    }

    @Test
    public void ensureValid_1dot_e10() throws Exception {
        final String testString = "1.e10";
        final ClientDecimal clientTest = ClientDecimal.valueOf(testString);

        assert(isFloatingPointString(testString));
        assertThat(clientTest.getValue(), is((Object) Double.valueOf(testString)));
    }

    @Test
    public void ensureValid_withPlus() throws Exception {
        final String testString = "+123.999999";
        final ClientDecimal clientTest = ClientDecimal.valueOf(testString);

        assert(isFloatingPointString(testString));
        assertThat(clientTest.getValue(), is((Object) Double.valueOf(testString)));
    }

    @Test
    public void ensureValid_fullwidthDigits() throws Exception {
        final String testString = "１２８.１３３０８００４";
        final ClientDecimal clientTest = ClientDecimal.valueOf(testString);

        assert(isFloatingPointString(testString));
        assertThat(clientTest.getValue(), is((Object) new BigDecimal("128.13308004").doubleValue()));
    }

    @Test
    public void ensureValid_mixed_fullwidthDigits_latinDigits() throws Exception {
        final String testString = "+１.2８１33０８００4";
        final ClientDecimal clientTest = ClientDecimal.valueOf(testString);

        assert(isFloatingPointString(testString));
        assertThat(clientTest.getValue(), is((Object) new BigDecimal("1.2813308004").doubleValue()));
    }

    @Test
    public void ensureValid_arabicDigits() throws Exception {
        // 0123456789
        final String testString = ".٠١٢٣٤٥٦٧٨٩";
        final ClientDecimal clientTest = ClientDecimal.valueOf(testString);
        assertThat(isFloatingPointString(testString), is(true));
        assertThat(clientTest.getValue(), is((Object) new BigDecimal(".0123456789").doubleValue()));
    }

    @Test
    public void ensureValid_arabicDigits_withMinus() throws Exception {
        // -0123456789
        String testString = "-٠١.٢٣٤٥٦٧٨٩";
        ClientDecimal clientTest = ClientDecimal.valueOf(testString);

        assert(isFloatingPointString(testString));
        assertThat(clientTest.getValue(), is(instanceOf(Double.class)));
        assertThat(clientTest.getValue(), is((Object) new BigDecimal("-01.23456789").doubleValue()));
    }

    @Test
    public void ensureValid_withMinus() throws Exception {
        final String testString = "-42.000";
        final ClientDecimal clientTest = ClientDecimal.valueOf(testString);

        assert(isFloatingPointString(testString));
        assertThat(clientTest.getValue(), is((Object) Double.valueOf(testString)));
    }

    @Test
    public void ensureInvalid_null() throws Exception {
        assert(!isFloatingPointString(null));
    }

    @Test
    public void ensureInvalid_twoDecimalPoints() throws Exception {
        final String testString = "-99.99.9";

        assert(!isFloatingPointString(testString));
    }

    @Test
    public void ensureInvalid_justE() throws Exception {
        final String testString = "e";

        assert(!isFloatingPointString(testString));
    }

    @Test
    public void ensureInvalid_justE10() throws Exception {
        final String testString = "e10";

        assert(!isFloatingPointString(testString));
    }

    @Test
    public void ensureInvalid_twoDecimalPointsAtBeginning() throws Exception {
        final String testString = "..99999";

        assert(!isFloatingPointString(testString));
    }

    @Test
    public void ensureInvalid_charInMiddle_withMinus() throws Exception {
        final String testString = "-99.9a999";

        assert(!isFloatingPointString(testString));
    }

    @Test
    public void ensureInvalid_lastCharLetter() throws Exception {
        final String testString = "12314392a.";

        assert(!isFloatingPointString(testString));
    }

    @Test
    public void ensureInvalid_firstCharLetter() throws Exception {
        final String testString = "b12.314392";

        assert(!isFloatingPointString(testString));
    }

    @Test
    public void ensureInvalid_secondCharMinus() throws Exception {
        final String testString = "1.-12314392";

        assert(!isFloatingPointString(testString));
    }

    @Test
    public void ensureInvalid_secondCharPlus() throws Exception {
        final String testString = "1+123.14392";

        assert(!isFloatingPointString(testString));
    }
}
