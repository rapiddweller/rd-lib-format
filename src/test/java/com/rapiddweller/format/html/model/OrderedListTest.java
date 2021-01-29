package com.rapiddweller.format.html.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OrderedListTest {
    @Test
    public void testConstructor() {
        OrderedList actualOrderedList = new OrderedList();
        assertEquals("<ol/>\n", actualOrderedList.toString());
        assertTrue(actualOrderedList.attributes.isEmpty());
        assertFalse(actualOrderedList.isInline());
        assertEquals("ol", actualOrderedList.getTagName());
    }
}

