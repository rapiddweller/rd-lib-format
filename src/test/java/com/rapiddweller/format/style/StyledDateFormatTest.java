package com.rapiddweller.format.style;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.format.Alignment;

import java.util.Locale;
import java.util.MissingResourceException;

import org.apache.poi.ss.usermodel.ExcelStyleDateFormatter;

import org.junit.Test;

public class StyledDateFormatTest {
    @Test
    public void testConstructor() {
        assertEquals(Alignment.RIGHT, (new StyledDateFormat()).getAlignment());
    }

    @Test
    public void testConstructor2() throws MissingResourceException {
        Locale locale = new Locale("en");
        assertEquals("en", locale.getLanguage());
        assertEquals("English", locale.getDisplayLanguage());
        assertEquals("eng", locale.getISO3Language());
        assertEquals("", locale.getScript());
        assertEquals("", locale.getVariant());
        assertEquals("", locale.getDisplayScript());
        assertFalse(locale.hasExtensions());
        assertEquals("", locale.getCountry());
        assertEquals("", locale.getDisplayVariant());
        assertEquals("", locale.getDisplayCountry());
        assertEquals("English", locale.getDisplayName());
        assertEquals("", locale.getISO3Country());
        assertEquals("en", locale.toString());
    }

    @Test
    public void testConstructor3() throws MissingResourceException {
        Locale locale = new Locale("en");
        assertEquals(Alignment.RIGHT, (new StyledDateFormat("", locale)).getAlignment());
        assertEquals("en", locale.getLanguage());
        assertEquals("English", locale.getDisplayLanguage());
        assertEquals("eng", locale.getISO3Language());
        assertEquals("", locale.getScript());
        assertEquals("", locale.getVariant());
        assertEquals("", locale.getDisplayScript());
        assertFalse(locale.hasExtensions());
        assertEquals("", locale.getCountry());
        assertEquals("", locale.getDisplayVariant());
        assertEquals("", locale.getDisplayCountry());
        assertEquals("English", locale.getDisplayName());
        assertEquals("", locale.getISO3Country());
        assertEquals("en", locale.toString());
    }

    @Test
    public void testConstructor4() {
        ExcelStyleDateFormatter excelStyleDateFormatter = new ExcelStyleDateFormatter();
        assertEquals(Alignment.RIGHT, (new StyledDateFormat(excelStyleDateFormatter)).getAlignment());
        assertTrue(excelStyleDateFormatter.getCalendar() instanceof java.util.GregorianCalendar);
        assertTrue(excelStyleDateFormatter.getNumberFormat() instanceof java.text.DecimalFormat);
        assertEquals("M/d/yy, h:mm a", excelStyleDateFormatter.toPattern());
        assertTrue(excelStyleDateFormatter.isLenient());
    }
}

