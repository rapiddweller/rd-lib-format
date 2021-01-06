package com.rapiddweller.format.xls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import com.rapiddweller.common.converter.ToArrayConverter;

import java.io.IOException;

import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Test;

public class XLSUtilTest {
    @Test
    public void testResolveCellValue() throws IOException {
        SXSSFCell sxssfCell = new SXSSFCell(new SXSSFRow(new SXSSFSheet(new SXSSFWorkbook(), null), 3), 1);
        assertNull(XLSUtil.resolveCellValue(sxssfCell));
        assertNull(sxssfCell.toString());
    }

    @Test
    public void testResolveCellValue2() {
        assertNull(XLSUtil.resolveCellValue(null));
    }

    @Test
    public void testResolveCellValue3() throws IOException {
        SXSSFCell sxssfCell = new SXSSFCell(new SXSSFRow(new SXSSFSheet(new SXSSFWorkbook(), null), 3), 1);
        assertEquals(0,
                ((Object[]) XLSUtil.resolveCellValue(sxssfCell, "Empty Marker", "Null Marker", new ToArrayConverter())).length);
        assertNull(sxssfCell.toString());
    }

    @Test
    public void testResolveCellValue4() {
        assertNull(XLSUtil.resolveCellValue(null, "Empty Marker", "Null Marker", new ToArrayConverter()));
    }

    @Test
    public void testResolveCellValue5() throws IOException {
        SXSSFCell cell = new SXSSFCell(new SXSSFRow(new SXSSFSheet(new SXSSFWorkbook(), null), 3), 0);
        assertEquals(0L,
                ((Long) XLSUtil.resolveCellValue(cell, "Empty Marker", "Null Marker", new ToArrayConverter())).longValue());
    }

    @Test
    public void testResolveCellValue6() throws IOException {
        SXSSFCell cell = new SXSSFCell(new SXSSFRow(new SXSSFSheet(new SXSSFWorkbook(), null), 3), 4);
        assertFalse((Boolean) XLSUtil.resolveCellValue(cell, "Empty Marker", "Null Marker", new ToArrayConverter()));
    }

    @Test
    public void testResolveCellValueAsString() throws IOException {
        SXSSFCell sxssfCell = new SXSSFCell(new SXSSFRow(new SXSSFSheet(new SXSSFWorkbook(), null), 3), 1);
        assertNull(XLSUtil.resolveCellValueAsString(sxssfCell));
        assertNull(sxssfCell.toString());
    }

    @Test
    public void testResolveCellValueAsString2() {
        assertNull(XLSUtil.resolveCellValueAsString(null));
    }

    @Test
    public void testResolveCellValueAsString3() throws IOException {
        SXSSFCell sxssfCell = new SXSSFCell(new SXSSFRow(new SXSSFSheet(new SXSSFWorkbook(), null), 3), 1);
        assertEquals("",
                XLSUtil.resolveCellValueAsString(sxssfCell, "Empty Marker", "Null Marker", new ToArrayConverter()));
        assertNull(sxssfCell.toString());
    }

    @Test
    public void testResolveCellValueAsString4() {
        assertNull(XLSUtil.resolveCellValueAsString(null, "Empty Marker", "Null Marker", new ToArrayConverter()));
    }

    @Test
    public void testResolveCellValueAsString5() throws IOException {
        SXSSFCell cell = new SXSSFCell(new SXSSFRow(new SXSSFSheet(new SXSSFWorkbook(), null), 3), 0);
        assertEquals("0", XLSUtil.resolveCellValueAsString(cell, "Empty Marker", "Null Marker", new ToArrayConverter()));
    }
}

