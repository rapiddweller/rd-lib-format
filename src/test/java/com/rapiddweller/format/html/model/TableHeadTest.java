package com.rapiddweller.format.html.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * The type Table head test.
 */
public class TableHeadTest {
  /**
   * Test constructor.
   */
  @Test
    public void testConstructor() {
        TableHead actualTableHead = new TableHead();
        assertEquals("<thead/>\n", actualTableHead.toString());
        assertTrue(actualTableHead.attributes.isEmpty());
        assertFalse(actualTableHead.isInline());
        assertEquals("thead", actualTableHead.getTagName());
    }

  /**
   * Test new row.
   */
  @Test
    public void testNewRow() {
        TableHead tableHead = new TableHead();
        TableRow actualNewRowResult = tableHead.newRow();
        assertEquals("<tr/>\n", actualNewRowResult.toString());
        assertTrue(actualNewRowResult.attributes.isEmpty());
        assertFalse(actualNewRowResult.isInline());
        assertEquals("tr", actualNewRowResult.getTagName());
        assertEquals("<thead>\n<tr/>\n\n</thead>\n", tableHead.toString());
    }

  /**
   * Test add row.
   */
  @Test
    public void testAddRow() {
        TableHead tableHead = new TableHead();
        assertSame(tableHead, tableHead.addRow(new TableRow()));
    }
}

