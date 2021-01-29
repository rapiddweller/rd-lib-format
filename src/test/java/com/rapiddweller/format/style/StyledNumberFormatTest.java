package com.rapiddweller.format.style;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import com.rapiddweller.common.format.Alignment;

import java.awt.Color;

import org.junit.Test;

public class StyledNumberFormatTest {
    @Test
    public void testConstructor() {
        assertEquals(Alignment.RIGHT, (new StyledNumberFormat("Pattern", true)).getAlignment());
        assertEquals(Alignment.RIGHT, (new StyledNumberFormat("Pattern", true)).getAlignment());
    }

    @Test
    public void testGetForegroundColor() {
        assertNull((new StyledNumberFormat("Pattern", true)).getForegroundColor("value", true));
        assertNull((new StyledNumberFormat("Pattern", false)).getForegroundColor("value", true));
        assertNull((new StyledNumberFormat("Pattern", true)).getForegroundColor(42, true));
        assertNull((new StyledNumberFormat("Pattern", true)).getForegroundColor("value", true));
        assertNull((new StyledNumberFormat("Pattern", false)).getForegroundColor("value", true));
        assertNull((new StyledNumberFormat("Pattern", true)).getForegroundColor(42, true));
    }

    @Test
    public void testGetForegroundColor2() {
        Color actualForegroundColor = (new StyledNumberFormat("Pattern", true)).getForegroundColor(-1, true);
        assertSame(actualForegroundColor.red, actualForegroundColor);
    }

    @Test
    public void testGetForegroundColor3() {
        Color actualForegroundColor = (new StyledNumberFormat("Pattern", true)).getForegroundColor(-1, true);
        assertSame(actualForegroundColor.red, actualForegroundColor);
    }
}

