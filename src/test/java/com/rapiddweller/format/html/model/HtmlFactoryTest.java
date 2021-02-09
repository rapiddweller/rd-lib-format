package com.rapiddweller.format.html.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The type Html factory test.
 */
public class HtmlFactoryTest {
  /**
   * Test bold.
   */
  @Test
    public void testBold() {
        Bold actualBoldResult = HtmlFactory.bold(new HtmlComponent());
        assertEquals("<b></b>", actualBoldResult.toString());
        assertTrue(actualBoldResult.attributes.isEmpty());
        assertTrue(actualBoldResult.isInline());
        assertEquals("b", actualBoldResult.getTagName());
    }

  /**
   * Test bold 2.
   */
  @Test
    public void testBold2() {
        Bold actualBoldResult = HtmlFactory.bold("Not all who wander are lost");
        assertEquals("<b>Not all who wander are lost</b>", actualBoldResult.toString());
        assertTrue(actualBoldResult.attributes.isEmpty());
        assertTrue(actualBoldResult.isInline());
        assertEquals("b", actualBoldResult.getTagName());
    }

  /**
   * Test font.
   */
  @Test
    public void testFont() {
        Font actualFontResult = HtmlFactory.font(new HtmlComponent());
        assertEquals("<font></font>", actualFontResult.toString());
        assertTrue(actualFontResult.attributes.isEmpty());
        assertTrue(actualFontResult.isInline());
        assertEquals("font", actualFontResult.getTagName());
    }

  /**
   * Test font 2.
   */
  @Test
    public void testFont2() {
        Font actualFontResult = HtmlFactory.font("Not all who wander are lost");
        assertEquals("<font>Not all who wander are lost</font>", actualFontResult.toString());
        assertTrue(actualFontResult.attributes.isEmpty());
        assertTrue(actualFontResult.isInline());
        assertEquals("font", actualFontResult.getTagName());
    }

  /**
   * Test url anchor.
   */
  @Test
    public void testUrlAnchor() {
        Anchor actualUrlAnchorResult = HtmlFactory.urlAnchor("https://example.org/example", "https://example.org/example");
        assertEquals("<a href=\"https://example.org/example\">https://example.org/example</a>",
                actualUrlAnchorResult.toString());
        assertEquals(1, actualUrlAnchorResult.attributes.size());
        assertTrue(actualUrlAnchorResult.isInline());
        assertEquals("a", actualUrlAnchorResult.getTagName());
    }

  /**
   * Test br.
   */
  @Test
    public void testBr() {
        HtmlElement<?> actualBrResult = HtmlFactory.br();
        assertEquals("<br/>", actualBrResult.toString());
        assertTrue(actualBrResult.attributes.isEmpty());
        assertTrue(actualBrResult.isInline());
        assertEquals("br", actualBrResult.getTagName());
    }
}

