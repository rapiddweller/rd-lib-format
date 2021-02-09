package com.rapiddweller.format.html.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The type H 1 test.
 */
public class H1Test {
  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    H1 actualH1 = new H1("Text");
    assertEquals("<h1>Text</h1>", actualH1.toString());
    assertTrue(actualH1.attributes.isEmpty());
    assertTrue(actualH1.isInline());
    assertEquals("h1", actualH1.getTagName());
  }
}

