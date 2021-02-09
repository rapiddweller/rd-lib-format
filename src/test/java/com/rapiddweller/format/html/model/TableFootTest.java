package com.rapiddweller.format.html.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * The type Table foot test.
 */
public class TableFootTest {
  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    TableFoot actualTableFoot = new TableFoot();
    assertEquals("<tfoot/>\n", actualTableFoot.toString());
    assertTrue(actualTableFoot.attributes.isEmpty());
    assertFalse(actualTableFoot.isInline());
    assertEquals("tfoot", actualTableFoot.getTagName());
  }

  /**
   * Test new row.
   */
  @Test
  public void testNewRow() {
    TableFoot tableFoot = new TableFoot();
    TableRow actualNewRowResult = tableFoot.newRow();
    assertEquals("<tr/>\n", actualNewRowResult.toString());
    assertTrue(actualNewRowResult.attributes.isEmpty());
    assertFalse(actualNewRowResult.isInline());
    assertEquals("tr", actualNewRowResult.getTagName());
    assertEquals("<tfoot>\n<tr/>\n\n</tfoot>\n", tableFoot.toString());
  }

  /**
   * Test add row.
   */
  @Test
  public void testAddRow() {
    TableFoot tableFoot = new TableFoot();
    assertSame(tableFoot, tableFoot.addRow(new TableRow()));
  }
}

