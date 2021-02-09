package com.rapiddweller.format.html.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The type Text component test.
 */
public class TextComponentTest {
  /**
   * Test constructor.
   */
  @Test
    public void testConstructor() {
        assertEquals("Text", (new TextComponent("Text")).toString());
        assertEquals("Text", (new TextComponent("Text", true, true)).toString());
        assertEquals("Text", (new TextComponent("Text", false, true)).toString());
        assertEquals("Text", (new TextComponent("Text", true, false)).toString());
    }
}

