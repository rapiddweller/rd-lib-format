package com.rapiddweller.format.html.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UnorderedListTest {
    @Test
    public void testConstructor() {
        UnorderedList actualUnorderedList = new UnorderedList();
        assertEquals("<ul/>\n", actualUnorderedList.toString());
        assertTrue(actualUnorderedList.attributes.isEmpty());
        assertFalse(actualUnorderedList.isInline());
        assertEquals("ul", actualUnorderedList.getTagName());
    }
}

