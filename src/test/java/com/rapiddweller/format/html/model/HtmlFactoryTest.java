package com.rapiddweller.format.html.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HtmlFactoryTest {
    @Test
    public void testBold() {
        Bold actualBoldResult = HtmlFactory.bold(new HtmlComponent());
        assertEquals("<b></b>", actualBoldResult.toString());
        assertTrue(actualBoldResult.attributes.isEmpty());
        assertTrue(actualBoldResult.isInline());
        assertEquals("b", actualBoldResult.getTagName());
    }

    @Test
    public void testBold2() {
        Bold actualBoldResult = HtmlFactory.bold("Not all who wander are lost");
        assertEquals("<b>Not all who wander are lost</b>", actualBoldResult.toString());
        assertTrue(actualBoldResult.attributes.isEmpty());
        assertTrue(actualBoldResult.isInline());
        assertEquals("b", actualBoldResult.getTagName());
    }

    @Test
    public void testFont() {
        Font actualFontResult = HtmlFactory.font(new HtmlComponent());
        assertEquals("<font></font>", actualFontResult.toString());
        assertTrue(actualFontResult.attributes.isEmpty());
        assertTrue(actualFontResult.isInline());
        assertEquals("font", actualFontResult.getTagName());
    }

    @Test
    public void testFont2() {
        Font actualFontResult = HtmlFactory.font("Not all who wander are lost");
        assertEquals("<font>Not all who wander are lost</font>", actualFontResult.toString());
        assertTrue(actualFontResult.attributes.isEmpty());
        assertTrue(actualFontResult.isInline());
        assertEquals("font", actualFontResult.getTagName());
    }

    @Test
    public void testUrlAnchor() {
        Anchor actualUrlAnchorResult = HtmlFactory.urlAnchor("https://example.org/example", "https://example.org/example");
        assertEquals("<a href=\"https://example.org/example\">https://example.org/example</a>",
                actualUrlAnchorResult.toString());
        assertEquals(1, actualUrlAnchorResult.attributes.size());
        assertTrue(actualUrlAnchorResult.isInline());
        assertEquals("a", actualUrlAnchorResult.getTagName());
    }

    @Test
    public void testBr() {
        HtmlElement<?> actualBrResult = HtmlFactory.br();
        assertEquals("<br/>", actualBrResult.toString());
        assertTrue(actualBrResult.attributes.isEmpty());
        assertTrue(actualBrResult.isInline());
        assertEquals("br", actualBrResult.getTagName());
    }
}

