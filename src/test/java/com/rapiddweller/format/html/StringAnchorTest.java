package com.rapiddweller.format.html;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The type String anchor test.
 */
public class StringAnchorTest {
  /**
   * Test create anchor for new window.
   */
  @Test
  public void testCreateAnchorForNewWindow() {
    StringAnchor actualCreateAnchorForNewWindowResult = StringAnchor.createAnchorForNewWindow("Href", "Label");
    assertEquals("Label", actualCreateAnchorForNewWindowResult.label);
    assertEquals("_blank", actualCreateAnchorForNewWindowResult.target);
    assertEquals("Href", actualCreateAnchorForNewWindowResult.href);
  }

  /**
   * Test to string.
   */
  @Test
  public void testToString() {
    assertEquals("<a href='Href' target='_blank'>Label</a>",
        StringAnchor.createAnchorForNewWindow("Href", "Label").toString());
    assertEquals("<a href='Href'>Label</a>", (new StringAnchor("Href", "Label")).toString());
  }
}

