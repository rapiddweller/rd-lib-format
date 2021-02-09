package com.rapiddweller.format.html.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The type Bold test.
 */
public class BoldTest {
  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    Bold actualBold = new Bold(new HtmlComponent());
    assertEquals("<b></b>", actualBold.toString());
    assertTrue(actualBold.attributes.isEmpty());
    assertTrue(actualBold.isInline());
    assertEquals("b", actualBold.getTagName());
  }

  /**
   * Test constructor 2.
   */
  @Test
  public void testConstructor2() {
    Bold actualBold = new Bold("Text");
    assertEquals("<b>Text</b>", actualBold.toString());
    assertTrue(actualBold.attributes.isEmpty());
    assertTrue(actualBold.isInline());
    assertEquals("b", actualBold.getTagName());
  }
}

