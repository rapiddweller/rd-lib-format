package com.rapiddweller.format.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class LFNormalizingStringBuilderTest {
    @Test
    public void testConstructor() {
        LFNormalizingStringBuilder actualLfNormalizingStringBuilder = new LFNormalizingStringBuilder("Line Separator");
        assertEquals("", actualLfNormalizingStringBuilder.toString());
        assertEquals("Line Separator", actualLfNormalizingStringBuilder.getLineSeparator());
    }

    @Test
    public void testAppend() {
        LFNormalizingStringBuilder lfNormalizingStringBuilder = new LFNormalizingStringBuilder("Line Separator");
        assertSame(lfNormalizingStringBuilder, lfNormalizingStringBuilder.append('A'));
    }

    @Test
    public void testAppend2() {
        LFNormalizingStringBuilder lfNormalizingStringBuilder = new LFNormalizingStringBuilder("Line Separator");
        assertSame(lfNormalizingStringBuilder, lfNormalizingStringBuilder.append('\r'));
    }

    @Test
    public void testAppend3() {
        LFNormalizingStringBuilder lfNormalizingStringBuilder = new LFNormalizingStringBuilder("Line Separator");
        assertSame(lfNormalizingStringBuilder, lfNormalizingStringBuilder.append('\n'));
    }

    @Test
    public void testAppend4() {
        LFNormalizingStringBuilder lfNormalizingStringBuilder = new LFNormalizingStringBuilder("Line Separator");
        assertSame(lfNormalizingStringBuilder, lfNormalizingStringBuilder.append("Text"));
    }

    @Test
    public void testToString() {
        assertEquals("", (new LFNormalizingStringBuilder("Line Separator")).toString());
    }
}

