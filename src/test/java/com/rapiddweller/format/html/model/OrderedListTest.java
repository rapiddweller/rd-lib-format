package com.rapiddweller.format.html.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The type Ordered list test.
 */
public class OrderedListTest {
  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    OrderedList actualOrderedList = new OrderedList();
    assertEquals("<ol/>\n", actualOrderedList.toString());
    assertTrue(actualOrderedList.attributes.isEmpty());
    assertFalse(actualOrderedList.isInline());
    assertEquals("ol", actualOrderedList.getTagName());
  }
}

