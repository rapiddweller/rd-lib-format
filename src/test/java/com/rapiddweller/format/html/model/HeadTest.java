package com.rapiddweller.format.html.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HeadTest {
    @Test
    public void testConstructor() {
        Head actualHead = new Head();
        assertEquals("<head/>\n", actualHead.toString());
        assertTrue(actualHead.attributes.isEmpty());
        assertFalse(actualHead.isInline());
        assertEquals("head", actualHead.getTagName());
    }

    @Test
    public void testWithTitle() {
        Head head = new Head();
        assertSame(head, head.withTitle("Dr"));
    }

    @Test
    public void testWithCssStyleSheet() {
        Head head = new Head();
        assertSame(head, head.withCssStyleSheet("Css Path"));
    }



    @Test
    public void testWithInlineCssStyleSheet2() {
        Head head = new Head();
        assertSame(head, head.withInlineCssStyleSheet("string://"));
    }

    @Test
    public void testWithInlineCssStyleSheet3() {
        assertThrows(RuntimeException.class, () -> (new Head()).withInlineCssStyleSheet("://"));
    }
}

