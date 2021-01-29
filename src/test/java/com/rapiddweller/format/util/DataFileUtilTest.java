package com.rapiddweller.format.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DataFileUtilTest {
    @Test
    public void testIsPlainTextDocument() {
        assertTrue(DataFileUtil.isPlainTextDocument("foo.txt"));
        assertFalse(DataFileUtil.isPlainTextDocument("File Name"));
    }

    @Test
    public void testIsDbUnitDocument() {
        assertFalse(DataFileUtil.isDbUnitDocument("foo.txt"));
        assertTrue(DataFileUtil.isDbUnitDocument(".dbunit.xml"));
    }

    @Test
    public void testIsXmlDocument() {
        assertFalse(DataFileUtil.isXmlDocument("foo.txt"));
        assertTrue(DataFileUtil.isXmlDocument(".xml"));
    }

    @Test
    public void testIsExcelOrCsvDocument() {
        assertFalse(DataFileUtil.isExcelOrCsvDocument("foo.txt"));
        assertTrue(DataFileUtil.isExcelOrCsvDocument(".xls"));
        assertTrue(DataFileUtil.isExcelOrCsvDocument(".xlsx"));
        assertTrue(DataFileUtil.isExcelOrCsvDocument(".csv"));
    }

    @Test
    public void testIsExcelDocument() {
        assertFalse(DataFileUtil.isExcelDocument("foo.txt"));
        assertTrue(DataFileUtil.isExcelDocument(".xls"));
        assertTrue(DataFileUtil.isExcelDocument(".xlsx"));
    }

    @Test
    public void testIsBinaryExcelDocument() {
        assertFalse(DataFileUtil.isBinaryExcelDocument("foo.txt"));
        assertTrue(DataFileUtil.isBinaryExcelDocument(".xls"));
    }

    @Test
    public void testIsXmlExcelDocument() {
        assertFalse(DataFileUtil.isXmlExcelDocument("foo.txt"));
        assertTrue(DataFileUtil.isXmlExcelDocument(".xlsx"));
    }

    @Test
    public void testIsCsvDocument() {
        assertFalse(DataFileUtil.isCsvDocument("foo.txt"));
        assertTrue(DataFileUtil.isCsvDocument(".csv"));
    }

    @Test
    public void testIsFixedColumnWidthFile() {
        assertFalse(DataFileUtil.isFixedColumnWidthFile("foo.txt"));
        assertTrue(DataFileUtil.isFixedColumnWidthFile(".fcw"));
    }

    @Test
    public void testHasSuffixIgnoreCase() {
        assertFalse(DataFileUtil.hasSuffixIgnoreCase("Suffix", "foo.txt"));
        assertTrue(DataFileUtil.hasSuffixIgnoreCase("", "foo.txt"));
    }
}

