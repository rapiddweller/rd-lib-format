package com.rapiddweller.format.text;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * The type Lf normalizing string builder test.
 */
public class LFNormalizingStringBuilderTest {
  /**
   * Test constructor.
   */
  @Test
    public void testConstructor() {
        LFNormalizingStringBuilder actualLfNormalizingStringBuilder = new LFNormalizingStringBuilder("Line Separator");
        assertEquals("", actualLfNormalizingStringBuilder.toString());
        assertEquals("Line Separator", actualLfNormalizingStringBuilder.getLineSeparator());
    }

  /**
   * Test append.
   */
  @Test
    public void testAppend() {
        LFNormalizingStringBuilder lfNormalizingStringBuilder = new LFNormalizingStringBuilder("Line Separator");
        assertSame(lfNormalizingStringBuilder, lfNormalizingStringBuilder.append('A'));
    }

  /**
   * Test append 2.
   */
  @Test
    public void testAppend2() {
        LFNormalizingStringBuilder lfNormalizingStringBuilder = new LFNormalizingStringBuilder("Line Separator");
        assertSame(lfNormalizingStringBuilder, lfNormalizingStringBuilder.append('\r'));
    }

  /**
   * Test append 3.
   */
  @Test
    public void testAppend3() {
        LFNormalizingStringBuilder lfNormalizingStringBuilder = new LFNormalizingStringBuilder("Line Separator");
        assertSame(lfNormalizingStringBuilder, lfNormalizingStringBuilder.append('\n'));
    }

  /**
   * Test append 4.
   */
  @Test
    public void testAppend4() {
        LFNormalizingStringBuilder lfNormalizingStringBuilder = new LFNormalizingStringBuilder("Line Separator");
        assertSame(lfNormalizingStringBuilder, lfNormalizingStringBuilder.append("Text"));
    }

  /**
   * Test to string.
   */
  @Test
    public void testToString() {
        assertEquals("", (new LFNormalizingStringBuilder("Line Separator")).toString());
    }
}

