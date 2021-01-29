package com.rapiddweller.format.html.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TableTest {
    @Test
    public void testConstructor() {
        Table actualTable = new Table();
        assertEquals("<table/>\n", actualTable.toString());
        assertTrue(actualTable.attributes.isEmpty());
        assertFalse(actualTable.isInline());
        assertEquals("table", actualTable.getTagName());
    }

    @Test
    public void testWithCellspacing() {
        Table table = new Table();
        assertSame(table, table.withCellspacing("value"));
    }

    @Test
    public void testNewTableHead() {
        Table table = new Table();
        TableHead actualNewTableHeadResult = table.newTableHead();
        assertEquals("<thead/>\n", actualNewTableHeadResult.toString());
        assertTrue(actualNewTableHeadResult.attributes.isEmpty());
        assertFalse(actualNewTableHeadResult.isInline());
        assertEquals("thead", actualNewTableHeadResult.getTagName());
        assertEquals("<table>\n<thead/>\n\n</table>\n", table.toString());
    }

    @Test
    public void testNewTableBody() {
        Table table = new Table();
        TableBody actualNewTableBodyResult = table.newTableBody();
        assertEquals("<tbody/>\n", actualNewTableBodyResult.toString());
        assertTrue(actualNewTableBodyResult.attributes.isEmpty());
        assertFalse(actualNewTableBodyResult.isInline());
        assertEquals("tbody", actualNewTableBodyResult.getTagName());
        assertEquals("<table>\n<tbody/>\n\n</table>\n", table.toString());
    }

    @Test
    public void testNewTableFoot() {
        Table table = new Table();
        TableFoot actualNewTableFootResult = table.newTableFoot();
        assertEquals("<tfoot/>\n", actualNewTableFootResult.toString());
        assertTrue(actualNewTableFootResult.attributes.isEmpty());
        assertFalse(actualNewTableFootResult.isInline());
        assertEquals("tfoot", actualNewTableFootResult.getTagName());
        assertEquals("<table>\n<tfoot/>\n\n</table>\n", table.toString());
    }

    @Test
    public void testAddRow() {
        Table table = new Table();
        assertSame(table, table.addRow(new TableRow()));
    }

    @Test
    public void testNewRow() {
        Table table = new Table();
        TableRow actualNewRowResult = table.newRow();
        assertEquals("<tr/>\n", actualNewRowResult.toString());
        assertTrue(actualNewRowResult.attributes.isEmpty());
        assertFalse(actualNewRowResult.isInline());
        assertEquals("tr", actualNewRowResult.getTagName());
        assertEquals("<table>\n<tr/>\n\n</table>\n", table.toString());
    }
}

