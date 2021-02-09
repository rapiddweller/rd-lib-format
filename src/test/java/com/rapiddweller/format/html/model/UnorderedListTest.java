package com.rapiddweller.format.html.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The type Unordered list test.
 */
public class UnorderedListTest {
  /**
   * Test constructor.
   */
  @Test
    public void testConstructor() {
        UnorderedList actualUnorderedList = new UnorderedList();
        assertEquals("<ul/>\n", actualUnorderedList.toString());
        assertTrue(actualUnorderedList.attributes.isEmpty());
        assertFalse(actualUnorderedList.isInline());
        assertEquals("ul", actualUnorderedList.getTagName());
    }
}

