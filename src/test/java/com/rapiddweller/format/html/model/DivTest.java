package com.rapiddweller.format.html.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DivTest {
    @Test
    public void testConstructor() {
        Div actualDiv = new Div();
        assertEquals("<div/>\n", actualDiv.toString());
        assertTrue(actualDiv.attributes.isEmpty());
        assertFalse(actualDiv.isInline());
        assertEquals("div", actualDiv.getTagName());
    }

    @Test
    public void testConstructor2() {
        Div actualDiv = new Div(true);
        assertEquals("<div/>", actualDiv.toString());
        assertTrue(actualDiv.attributes.isEmpty());
        assertTrue(actualDiv.isInline());
        assertEquals("div", actualDiv.getTagName());
    }

    @Test
    public void testWithTitle() {
        Div div = new Div();
        assertSame(div, div.withTitle("Dr"));
    }
}

