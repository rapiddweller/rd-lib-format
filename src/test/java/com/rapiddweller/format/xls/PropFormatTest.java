package com.rapiddweller.format.xls;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * The type Prop format test.
 */
public class PropFormatTest {
  /**
   * Test with date format.
   */
  @Test
  public void testWithDateFormat() {
    PropFormat propFormat = new PropFormat("Name");
    PropFormat actualWithDateFormatResult = propFormat.withDateFormat();
    assertSame(propFormat, actualWithDateFormatResult);
    assertEquals("dd.MM.yyyy", actualWithDateFormatResult.getPattern());
  }

  /**
   * Test with int format.
   */
  @Test
  public void testWithIntFormat() {
    PropFormat propFormat = new PropFormat("Name");
    PropFormat actualWithIntFormatResult = propFormat.withIntFormat();
    assertSame(propFormat, actualWithIntFormatResult);
    assertEquals("#,##0", actualWithIntFormatResult.getPattern());
  }

  /**
   * Test with percentage format.
   */
  @Test
  public void testWithPercentageFormat() {
    PropFormat propFormat = new PropFormat("Name");
    PropFormat actualWithPercentageFormatResult = propFormat.withPercentageFormat();
    assertSame(propFormat, actualWithPercentageFormatResult);
    assertEquals("0%", actualWithPercentageFormatResult.getPattern());
  }

  /**
   * Test with decimal format.
   */
  @Test
  public void testWithDecimalFormat() {
    PropFormat propFormat = new PropFormat("Name");
    PropFormat actualWithDecimalFormatResult = propFormat.withDecimalFormat(1);
    assertSame(propFormat, actualWithDecimalFormatResult);
    assertEquals("#,##0.0", actualWithDecimalFormatResult.getPattern());
  }

  /**
   * Test with decimal format 2.
   */
  @Test
  public void testWithDecimalFormat2() {
    PropFormat propFormat = new PropFormat("Name");
    PropFormat actualWithDecimalFormatResult = propFormat.withDecimalFormat(0);
    assertSame(propFormat, actualWithDecimalFormatResult);
    assertEquals("#,##0", actualWithDecimalFormatResult.getPattern());
  }

  /**
   * Test with decimal format 3.
   */
  @Test
  public void testWithDecimalFormat3() {
    PropFormat propFormat = new PropFormat("Name");
    PropFormat actualWithDecimalFormatResult = propFormat.withDecimalFormat(2);
    assertSame(propFormat, actualWithDecimalFormatResult);
    assertEquals("#,##0.00", actualWithDecimalFormatResult.getPattern());
  }

  /**
   * Test with decimal format 4.
   */
  @Test
  public void testWithDecimalFormat4() {
    PropFormat propFormat = new PropFormat("Name");
    PropFormat actualWithDecimalFormatResult = propFormat.withDecimalFormat(3);
    assertSame(propFormat, actualWithDecimalFormatResult);
    assertEquals("#,##0.000", actualWithDecimalFormatResult.getPattern());
  }

  /**
   * Test with decimal format 5.
   */
  @Test
  public void testWithDecimalFormat5() {
    PropFormat propFormat = new PropFormat("Name");
    PropFormat actualWithDecimalFormatResult = propFormat.withDecimalFormat(-1);
    assertSame(propFormat, actualWithDecimalFormatResult);
    assertEquals("#,##0.", actualWithDecimalFormatResult.getPattern());
  }

  /**
   * Test with pattern.
   */
  @Test
  public void testWithPattern() {
    PropFormat propFormat = new PropFormat("Name");
    PropFormat actualWithPatternResult = propFormat.withPattern("Pattern");
    assertSame(propFormat, actualWithPatternResult);
    assertEquals("Pattern", actualWithPatternResult.getPattern());
  }

  /**
   * Test to string.
   */
  @Test
  public void testToString() {
    assertEquals("Name", (new PropFormat("Name")).toString());
  }

  /**
   * Test to string 2.
   */
  @Test
  public void testToString2() {
    PropFormat propFormat = new PropFormat("Name");
    propFormat.withPattern("");
    assertEquals("Name[]", propFormat.toString());
  }
}

