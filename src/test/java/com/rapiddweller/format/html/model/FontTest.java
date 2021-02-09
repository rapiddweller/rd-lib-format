package com.rapiddweller.format.html.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The type Font test.
 */
public class FontTest {
  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    Font actualFont = new Font(new HtmlComponent());
    assertEquals("<font></font>", actualFont.toString());
    assertTrue(actualFont.attributes.isEmpty());
    assertTrue(actualFont.isInline());
    assertEquals("font", actualFont.getTagName());
  }

  /**
   * Test constructor 2.
   */
  @Test
  public void testConstructor2() {
    Font actualFont = new Font("Text");
    assertEquals("<font>Text</font>", actualFont.toString());
    assertTrue(actualFont.attributes.isEmpty());
    assertTrue(actualFont.isInline());
    assertEquals("font", actualFont.getTagName());
  }

  /**
   * Test with size.
   */
  @Test
  public void testWithSize() {
    assertEquals("<font size=\"Size\">Not all who wander are lost</font>",
        HtmlFactory.font("Not all who wander are lost").withSize("Size").toString());
  }
}

