/*
 * Copyright (C) 2011-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rapiddweller.format.text;

import java.util.Locale;
import java.text.ParseException;
import java.text.FieldPosition;

import com.rapiddweller.format.text.AbbreviatedNumberFormat;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the {@link AbbreviatedNumberFormat}.
 * Created: 16.05.2005 22:04:10
 * @since 0.1
 * @author Volker Bergmann
 */
public class AbbreviatedNumberFormatTest {
    private static final double DELTA = 0.0001;

    @Test
    public void testParseEnglish() throws Exception {
        checkParse("1", 1, Locale.US);
        checkParse("1 Tsd", 1000, Locale.US);
        checkParse("1. Tsd", 1000, Locale.US);
        checkParse("1.234 Tsd", 1234, Locale.US);
        checkParse("1,234.56 Mio", 1234560000, Locale.US);
    }

    @Test
    public void testParseGerman() throws Exception {
        checkParse("1", 1, Locale.GERMANY);
        checkParse("1 Tsd", 1000, Locale.GERMANY);
        checkParse("1, Tsd", 1000, Locale.GERMANY);
        checkParse("1,234 Tsd", 1234, Locale.GERMANY);
        checkParse("1,234 Tsd", 1234, Locale.GERMANY);
        checkParse("1.234,56 Mio", 1234560000, Locale.GERMANY);
    }

    @Test
    public void testFormat() throws Exception {
        checkFormat(1, "1.00", Locale.US);
        checkFormat(1000, "1.00 Tsd", Locale.US);
        checkFormat(1234, "1.23 Tsd", Locale.US);
        checkFormat(1, "1,00", Locale.GERMANY);
        checkFormat(1000, "1,00 Tsd", Locale.GERMANY);
        checkFormat(1234, "1,23 Tsd", Locale.GERMANY);
    }

    @Test
    public void testFormatFixed() throws Exception {
        checkFormatFixed(1, "1.00", 1, Locale.US);
        checkFormatFixed(123, "0.12 Tsd", 1000, Locale.US);
        checkFormatFixed(1000, "1.00 Tsd", 1000, Locale.US);
        checkFormatFixed(1234, "1.23 Tsd", 1000, Locale.US);
        checkFormatFixed(1234560000, "1,234.56 M", 1000000, Locale.US);
        checkFormatFixed(1, "1,00", 1, Locale.GERMANY);
        checkFormatFixed(123, "0,12 Tsd", 1000, Locale.GERMANY);
        checkFormatFixed(1000, "1,00 Tsd", 1000, Locale.GERMANY);
        checkFormatFixed(1234, "1,23 Tsd", 1000, Locale.GERMANY);
        checkFormatFixed(1234560000, "1.234,56 Mio", 1000000, Locale.GERMANY);
    }

    private static void checkParse(String source, double target, Locale locale) throws ParseException {
        AbbreviatedNumberFormat format = new AbbreviatedNumberFormat(locale);
        assertEquals(target, format.parse(source).doubleValue(), DELTA);
    }

    private static void checkFormat(double source, String target, Locale locale) {
        AbbreviatedNumberFormat format = new AbbreviatedNumberFormat(locale);
        String result = format.format(source);
        assertEquals(target, result);
    }

    private static void checkFormatFixed(double source, String target, double defaultScale, Locale locale) {
        AbbreviatedNumberFormat format = new AbbreviatedNumberFormat(defaultScale, locale);
        String result = format.formatFixed(source, new StringBuffer(), new FieldPosition(0)).toString();
        assertEquals(target, result);
    }

}
