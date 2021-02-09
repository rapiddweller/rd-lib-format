package com.rapiddweller.format.html.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The type Span test.
 */
public class SpanTest {
  /**
   * Test constructor.
   */
  @Test
    public void testConstructor() {
        Span actualSpan = new Span();
        assertEquals("<span/>", actualSpan.toString());
        assertTrue(actualSpan.attributes.isEmpty());
        assertTrue(actualSpan.isInline());
        assertEquals("span", actualSpan.getTagName());
    }
}

