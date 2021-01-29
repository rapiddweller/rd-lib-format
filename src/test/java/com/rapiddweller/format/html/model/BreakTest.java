package com.rapiddweller.format.html.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BreakTest {
    @Test
    public void testConstructor() {
        Break actualResultBreak = new Break();
        assertEquals("<br/>\n", actualResultBreak.toString());
        assertTrue(actualResultBreak.attributes.isEmpty());
        assertFalse(actualResultBreak.isInline());
        assertEquals("br", actualResultBreak.getTagName());
    }
}

