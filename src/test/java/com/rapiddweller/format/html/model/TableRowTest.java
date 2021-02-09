package com.rapiddweller.format.html.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * The type Table row test.
 */
public class TableRowTest {
  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    TableRow actualTableRow = new TableRow();
    assertEquals("<tr/>\n", actualTableRow.toString());
    assertTrue(actualTableRow.attributes.isEmpty());
    assertFalse(actualTableRow.isInline());
    assertEquals("tr", actualTableRow.getTagName());
  }

  /**
   * Test add cell.
   */
  @Test
  public void testAddCell() {
    TableRow tableRow = new TableRow();
    HtmlComponent htmlComponent = new HtmlComponent();
    HtmlComponent htmlComponent1 = new HtmlComponent();
    assertSame(tableRow, tableRow.addCell(new TableCell(htmlComponent, htmlComponent1, new HtmlComponent())));
  }

  /**
   * Test new cell.
   */
  @Test
  public void testNewCell() {
    TableRow tableRow = new TableRow();
    TableCell actualNewCellResult = tableRow.newCell("Text");
    assertEquals("<td>\nText\n</td>\n", actualNewCellResult.toString());
    assertTrue(actualNewCellResult.attributes.isEmpty());
    assertFalse(actualNewCellResult.isInline());
    assertEquals("td", actualNewCellResult.getTagName());
    assertEquals("<tr>\n<td>\nText\n</td>\n\n</tr>\n", tableRow.toString());
  }

  /**
   * Test new cell 2.
   */
  @Test
  public void testNewCell2() {
    TableRow tableRow = new TableRow();
    HtmlComponent htmlComponent = new HtmlComponent();
    HtmlComponent htmlComponent1 = new HtmlComponent();
    TableCell actualNewCellResult = tableRow.newCell(htmlComponent, htmlComponent1, new HtmlComponent());
    assertEquals("<td>\n\n</td>\n", actualNewCellResult.toString());
    assertTrue(actualNewCellResult.attributes.isEmpty());
    assertFalse(actualNewCellResult.isInline());
    assertEquals("td", actualNewCellResult.getTagName());
    assertEquals("<tr>\n<td>\n\n</td>\n\n</tr>\n", tableRow.toString());
  }
}

