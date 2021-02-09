package com.rapiddweller.format.html.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * The type Link test.
 */
public class LinkTest {
  /**
   * Test constructor.
   */
  @Test
    public void testConstructor() {
        Link actualLink = new Link();
        assertEquals("<link/>", actualLink.toString());
        assertTrue(actualLink.attributes.isEmpty());
        assertTrue(actualLink.isInline());
        assertEquals("link", actualLink.getTagName());
    }

  /**
   * Test with rel.
   */
  @Test
    public void testWithRel() {
        Link link = new Link();
        assertSame(link, link.withRel("Rel"));
    }

  /**
   * Test with type.
   */
  @Test
    public void testWithType() {
        Link link = new Link();
        assertSame(link, link.withType("Type"));
    }

  /**
   * Test with href.
   */
  @Test
    public void testWithHref() {
        Link link = new Link();
        assertSame(link, link.withHref("Href"));
    }
}

