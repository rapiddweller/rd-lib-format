package com.rapiddweller.format.html;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringAnchorTest {
    @Test
    public void testCreateAnchorForNewWindow() {
        StringAnchor actualCreateAnchorForNewWindowResult = StringAnchor.createAnchorForNewWindow("Href", "Label");
        assertEquals("Label", actualCreateAnchorForNewWindowResult.label);
        assertEquals("_blank", actualCreateAnchorForNewWindowResult.target);
        assertEquals("Href", actualCreateAnchorForNewWindowResult.href);
    }

    @Test
    public void testToString() {
        assertEquals("<a href='Href' target='_blank'>Label</a>",
                StringAnchor.createAnchorForNewWindow("Href", "Label").toString());
        assertEquals("<a href='Href'>Label</a>", (new StringAnchor("Href", "Label")).toString());
    }
}

