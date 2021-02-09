package com.rapiddweller.format.html.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * The type Anchor test.
 */
public class AnchorTest {
  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    Anchor actualAnchor = new Anchor(new HtmlComponent());
    assertEquals("<a></a>", actualAnchor.toString());
    assertTrue(actualAnchor.attributes.isEmpty());
    assertTrue(actualAnchor.isInline());
    assertEquals("a", actualAnchor.getTagName());
  }

  /**
   * Test constructor 2.
   */
  @Test
  public void testConstructor2() {
    Anchor actualAnchor = new Anchor("Label");
    assertEquals("<a>Label</a>", actualAnchor.toString());
    assertTrue(actualAnchor.attributes.isEmpty());
    assertTrue(actualAnchor.isInline());
    assertEquals("a", actualAnchor.getTagName());
  }

  /**
   * Test with href.
   */
  @Test
  public void testWithHref() {
    Anchor anchor = new Anchor(new HtmlComponent());
    assertSame(anchor, anchor.withHref("https://example.org/example"));
  }
}

