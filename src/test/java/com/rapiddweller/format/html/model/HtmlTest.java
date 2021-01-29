package com.rapiddweller.format.html.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HtmlTest {
    @Test
    public void testConstructor() {
        HtmlComponent htmlComponent = new HtmlComponent();
        HtmlComponent htmlComponent1 = new HtmlComponent();
        Html actualHtml = new Html(htmlComponent, htmlComponent1, new HtmlComponent());
        assertEquals("<html>\n\n</html>\n", actualHtml.toString());
        assertTrue(actualHtml.attributes.isEmpty());
        assertFalse(actualHtml.isInline());
        assertEquals("html", actualHtml.getTagName());
    }

    @Test
    public void testCreateHead() {
        HtmlComponent htmlComponent = new HtmlComponent();
        HtmlComponent htmlComponent1 = new HtmlComponent();
        Html html = new Html(htmlComponent, htmlComponent1, new HtmlComponent());
        Head actualCreateHeadResult = html.createHead();
        assertEquals("<head/>\n", actualCreateHeadResult.toString());
        assertTrue(actualCreateHeadResult.attributes.isEmpty());
        assertFalse(actualCreateHeadResult.isInline());
        assertEquals("head", actualCreateHeadResult.getTagName());
        assertEquals("<html>\n<head/>\n\n</html>\n", html.toString());
    }

    @Test
    public void testCreateBody() {
        HtmlComponent htmlComponent = new HtmlComponent();
        HtmlComponent htmlComponent1 = new HtmlComponent();
        Html html = new Html(htmlComponent, htmlComponent1, new HtmlComponent());
        Body actualCreateBodyResult = html.createBody();
        assertEquals("<body/>\n", actualCreateBodyResult.toString());
        assertTrue(actualCreateBodyResult.attributes.isEmpty());
        assertFalse(actualCreateBodyResult.isInline());
        assertEquals("body", actualCreateBodyResult.getTagName());
        assertEquals("<html>\n<body/>\n\n</html>\n", html.toString());
    }
}

