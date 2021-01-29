package com.rapiddweller.format.html.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TableBodyTest {
    @Test
    public void testConstructor() {
        TableBody actualTableBody = new TableBody();
        assertEquals("<tbody/>\n", actualTableBody.toString());
        assertTrue(actualTableBody.attributes.isEmpty());
        assertFalse(actualTableBody.isInline());
        assertEquals("tbody", actualTableBody.getTagName());
    }

    @Test
    public void testNewRow() {
        TableBody tableBody = new TableBody();
        TableRow actualNewRowResult = tableBody.newRow();
        assertEquals("<tr/>\n", actualNewRowResult.toString());
        assertTrue(actualNewRowResult.attributes.isEmpty());
        assertFalse(actualNewRowResult.isInline());
        assertEquals("tr", actualNewRowResult.getTagName());
        assertEquals("<tbody>\n<tr/>\n\n</tbody>\n", tableBody.toString());
    }

    @Test
    public void testAddRow() {
        TableBody tableBody = new TableBody();
        assertSame(tableBody, tableBody.addRow(new TableRow()));
    }
}

