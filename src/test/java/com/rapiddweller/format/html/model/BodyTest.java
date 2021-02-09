package com.rapiddweller.format.html.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The type Body test.
 */
public class BodyTest {
  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    HtmlComponent htmlComponent = new HtmlComponent();
    HtmlComponent htmlComponent1 = new HtmlComponent();
    Body actualBody = new Body(htmlComponent, htmlComponent1, new HtmlComponent());
    assertEquals("<body>\n\n</body>\n", actualBody.toString());
    assertTrue(actualBody.attributes.isEmpty());
    assertFalse(actualBody.isInline());
    assertEquals("body", actualBody.getTagName());
  }
}

