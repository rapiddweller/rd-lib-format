package com.rapiddweller.format.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The type Data file util test.
 */
public class DataFileUtilTest {
  /**
   * Test is plain text document.
   */
  @Test
    public void testIsPlainTextDocument() {
        assertTrue(DataFileUtil.isPlainTextDocument("foo.txt"));
        assertFalse(DataFileUtil.isPlainTextDocument("File Name"));
    }

  /**
   * Test is db unit document.
   */
  @Test
    public void testIsDbUnitDocument() {
        assertFalse(DataFileUtil.isDbUnitDocument("foo.txt"));
        assertTrue(DataFileUtil.isDbUnitDocument(".dbunit.xml"));
    }

  /**
   * Test is xml document.
   */
  @Test
    public void testIsXmlDocument() {
        assertFalse(DataFileUtil.isXmlDocument("foo.txt"));
        assertTrue(DataFileUtil.isXmlDocument(".xml"));
    }

  /**
   * Test is excel or csv document.
   */
  @Test
    public void testIsExcelOrCsvDocument() {
        assertFalse(DataFileUtil.isExcelOrCsvDocument("foo.txt"));
        assertTrue(DataFileUtil.isExcelOrCsvDocument(".xls"));
        assertTrue(DataFileUtil.isExcelOrCsvDocument(".xlsx"));
        assertTrue(DataFileUtil.isExcelOrCsvDocument(".csv"));
    }

  /**
   * Test is excel document.
   */
  @Test
    public void testIsExcelDocument() {
        assertFalse(DataFileUtil.isExcelDocument("foo.txt"));
        assertTrue(DataFileUtil.isExcelDocument(".xls"));
        assertTrue(DataFileUtil.isExcelDocument(".xlsx"));
    }

  /**
   * Test is binary excel document.
   */
  @Test
    public void testIsBinaryExcelDocument() {
        assertFalse(DataFileUtil.isBinaryExcelDocument("foo.txt"));
        assertTrue(DataFileUtil.isBinaryExcelDocument(".xls"));
    }

  /**
   * Test is xml excel document.
   */
  @Test
    public void testIsXmlExcelDocument() {
        assertFalse(DataFileUtil.isXmlExcelDocument("foo.txt"));
        assertTrue(DataFileUtil.isXmlExcelDocument(".xlsx"));
    }

  /**
   * Test is csv document.
   */
  @Test
    public void testIsCsvDocument() {
        assertFalse(DataFileUtil.isCsvDocument("foo.txt"));
        assertTrue(DataFileUtil.isCsvDocument(".csv"));
    }

  /**
   * Test is fixed column width file.
   */
  @Test
    public void testIsFixedColumnWidthFile() {
        assertFalse(DataFileUtil.isFixedColumnWidthFile("foo.txt"));
        assertTrue(DataFileUtil.isFixedColumnWidthFile(".fcw"));
    }

  /**
   * Test has suffix ignore case.
   */
  @Test
    public void testHasSuffixIgnoreCase() {
        assertFalse(DataFileUtil.hasSuffixIgnoreCase("Suffix", "foo.txt"));
        assertTrue(DataFileUtil.hasSuffixIgnoreCase("", "foo.txt"));
    }
}

