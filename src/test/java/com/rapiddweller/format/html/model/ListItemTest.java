package com.rapiddweller.format.html.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ListItemTest {
    @Test
    public void testConstructor() {
        ListItem actualListItem = new ListItem(new HtmlComponent());
        assertEquals("<li></li>", actualListItem.toString());
        assertTrue(actualListItem.attributes.isEmpty());
        assertTrue(actualListItem.isInline());
        assertEquals("li", actualListItem.getTagName());
    }
}

