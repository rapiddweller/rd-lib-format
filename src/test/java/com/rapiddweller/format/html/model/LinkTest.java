package com.rapiddweller.format.html.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LinkTest {
    @Test
    public void testConstructor() {
        Link actualLink = new Link();
        assertEquals("<link/>", actualLink.toString());
        assertTrue(actualLink.attributes.isEmpty());
        assertTrue(actualLink.isInline());
        assertEquals("link", actualLink.getTagName());
    }

    @Test
    public void testWithRel() {
        Link link = new Link();
        assertSame(link, link.withRel("Rel"));
    }

    @Test
    public void testWithType() {
        Link link = new Link();
        assertSame(link, link.withType("Type"));
    }

    @Test
    public void testWithHref() {
        Link link = new Link();
        assertSame(link, link.withHref("Href"));
    }
}

