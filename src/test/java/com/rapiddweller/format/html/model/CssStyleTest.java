package com.rapiddweller.format.html.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class CssStyleTest {
    @Test
    public void testConstructor() {
        CssStyle actualCssStyle = new CssStyle();
        assertEquals("<style type=\"text/css\"/>\n", actualCssStyle.toString());
        assertEquals(1, actualCssStyle.attributes.size());
        assertFalse(actualCssStyle.isInline());
        assertEquals("style", actualCssStyle.getTagName());
    }
}

