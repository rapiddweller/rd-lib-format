package com.rapiddweller.format.html.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TableCellTest {
    @Test
    public void testConstructor() {
        TableCell actualTableCell = new TableCell("Text");
        assertEquals("<td>\nText\n</td>\n", actualTableCell.toString());
        assertTrue(actualTableCell.attributes.isEmpty());
        assertFalse(actualTableCell.isInline());
        assertEquals("td", actualTableCell.getTagName());
    }

    @Test
    public void testConstructor2() {
        HtmlComponent htmlComponent = new HtmlComponent();
        HtmlComponent htmlComponent1 = new HtmlComponent();
        TableCell actualTableCell = new TableCell(htmlComponent, htmlComponent1, new HtmlComponent());
        assertEquals("<td>\n\n</td>\n", actualTableCell.toString());
        assertTrue(actualTableCell.attributes.isEmpty());
        assertFalse(actualTableCell.isInline());
        assertEquals("td", actualTableCell.getTagName());
    }

    @Test
    public void testWithColspan() {
        HtmlComponent htmlComponent = new HtmlComponent();
        HtmlComponent htmlComponent1 = new HtmlComponent();
        TableCell tableCell = new TableCell(htmlComponent, htmlComponent1, new HtmlComponent());
        assertSame(tableCell, tableCell.withColspan(1));
    }
}

