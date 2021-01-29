package com.rapiddweller.format.html.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SpanTest {
    @Test
    public void testConstructor() {
        Span actualSpan = new Span();
        assertEquals("<span/>", actualSpan.toString());
        assertTrue(actualSpan.attributes.isEmpty());
        assertTrue(actualSpan.isInline());
        assertEquals("span", actualSpan.getTagName());
    }
}

