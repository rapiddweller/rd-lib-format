package com.rapiddweller.format.html.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The type List item test.
 */
public class ListItemTest {
  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    ListItem actualListItem = new ListItem(new HtmlComponent());
    assertEquals("<li></li>", actualListItem.toString());
    assertTrue(actualListItem.attributes.isEmpty());
    assertTrue(actualListItem.isInline());
    assertEquals("li", actualListItem.getTagName());
  }
}

