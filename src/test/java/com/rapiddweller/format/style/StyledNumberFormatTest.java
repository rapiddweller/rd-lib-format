package com.rapiddweller.format.style;

import com.rapiddweller.common.format.Alignment;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * The type Styled number format test.
 */
public class StyledNumberFormatTest {
  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    assertEquals(Alignment.RIGHT, (new StyledNumberFormat("Pattern", true)).getAlignment());
    assertEquals(Alignment.RIGHT, (new StyledNumberFormat("Pattern", true)).getAlignment());
  }

  /**
   * Test get foreground color.
   */
  @Test
  public void testGetForegroundColor() {
    assertNull((new StyledNumberFormat("Pattern", true)).getForegroundColor("value", true));
    assertNull((new StyledNumberFormat("Pattern", false)).getForegroundColor("value", true));
    assertNull((new StyledNumberFormat("Pattern", true)).getForegroundColor(42, true));
    assertNull((new StyledNumberFormat("Pattern", true)).getForegroundColor("value", true));
    assertNull((new StyledNumberFormat("Pattern", false)).getForegroundColor("value", true));
    assertNull((new StyledNumberFormat("Pattern", true)).getForegroundColor(42, true));
  }

  /**
   * Test get foreground color 2.
   */
  @Test
  public void testGetForegroundColor2() {
    Color actualForegroundColor = (new StyledNumberFormat("Pattern", true)).getForegroundColor(-1, true);
    assertSame(Color.red, actualForegroundColor);
  }

  /**
   * Test get foreground color 3.
   */
  @Test
  public void testGetForegroundColor3() {
    Color actualForegroundColor = (new StyledNumberFormat("Pattern", true)).getForegroundColor(-1, true);
    assertSame(Color.red, actualForegroundColor);
  }
}

