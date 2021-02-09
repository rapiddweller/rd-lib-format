package com.rapiddweller.format.html.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The type Break test.
 */
public class BreakTest {
  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    Break actualResultBreak = new Break();
    assertEquals("<br/>\n", actualResultBreak.toString());
    assertTrue(actualResultBreak.attributes.isEmpty());
    assertFalse(actualResultBreak.isInline());
    assertEquals("br", actualResultBreak.getTagName());
  }
}

