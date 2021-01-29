package com.rapiddweller.format.html.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TextComponentTest {
    @Test
    public void testConstructor() {
        assertEquals("Text", (new TextComponent("Text")).toString());
        assertEquals("Text", (new TextComponent("Text", true, true)).toString());
        assertEquals("Text", (new TextComponent("Text", false, true)).toString());
        assertEquals("Text", (new TextComponent("Text", true, false)).toString());
    }
}

