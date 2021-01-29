package com.rapiddweller.format.html.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class H1Test {
    @Test
    public void testConstructor() {
        H1 actualH1 = new H1("Text");
        assertEquals("<h1>Text</h1>", actualH1.toString());
        assertTrue(actualH1.attributes.isEmpty());
        assertTrue(actualH1.isInline());
        assertEquals("h1", actualH1.getTagName());
    }
}

