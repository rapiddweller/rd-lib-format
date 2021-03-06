package com.rapiddweller.format.style;

import com.rapiddweller.common.format.Alignment;
import com.rapiddweller.common.format.ConcurrentDateFormat;
import org.junit.Test;

import java.util.Locale;
import java.util.MissingResourceException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

/**
 * The type Styled date format test.
 */
public class StyledDateFormatTest {
  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    assertEquals(Alignment.RIGHT, (new StyledDateFormat()).getAlignment());
    assertEquals(Alignment.RIGHT, (new StyledDateFormat()).getAlignment());
  }

  /**
   * Test constructor 2.
   *
   * @throws MissingResourceException the missing resource exception
   */
  @Test
  public void testConstructor2() throws MissingResourceException {
    Locale locale = new Locale("en");
    assertEquals("en", locale.getLanguage());
    assertEquals("English", locale.getDisplayLanguage());
    assertEquals("eng", locale.getISO3Language());
    assertEquals("", locale.getScript());
    assertEquals("", locale.getVariant());
    assertEquals("", locale.getDisplayScript());
    assertFalse(locale.hasExtensions());
    assertEquals("", locale.getCountry());
    assertEquals("", locale.getDisplayVariant());
    assertEquals("", locale.getDisplayCountry());
    assertEquals("English", locale.getDisplayName());
    assertEquals("", locale.getISO3Country());
    assertEquals("en", locale.toString());
  }

  /**
   * Test constructor 3.
   *
   * @throws MissingResourceException the missing resource exception
   */
  @Test
  public void testConstructor3() throws MissingResourceException {
    Locale locale = new Locale("en");
    assertEquals(Alignment.RIGHT, (new StyledDateFormat("", locale)).getAlignment());
    assertEquals("en", locale.getLanguage());
    assertEquals("English", locale.getDisplayLanguage());
    assertEquals("eng", locale.getISO3Language());
    assertEquals("", locale.getScript());
    assertEquals("", locale.getVariant());
    assertEquals("", locale.getDisplayScript());
    assertFalse(locale.hasExtensions());
    assertEquals("", locale.getCountry());
    assertEquals("", locale.getDisplayVariant());
    assertEquals("", locale.getDisplayCountry());
    assertEquals("English", locale.getDisplayName());
    assertEquals("", locale.getISO3Country());
    assertEquals("en", locale.toString());
  }


  /**
   * Test constructor 5.
   *
   * @throws MissingResourceException the missing resource exception
   */
  @Test
  public void testConstructor5() throws MissingResourceException {
    Locale locale = new Locale("en");
    assertEquals(Alignment.RIGHT, (new StyledDateFormat("", locale)).getAlignment());
    assertEquals("en", locale.getLanguage());
    assertEquals("English", locale.getDisplayLanguage());
    assertEquals("eng", locale.getISO3Language());
    assertEquals("", locale.getScript());
    assertEquals("", locale.getVariant());
    assertEquals("", locale.getDisplayScript());
    assertFalse(locale.hasExtensions());
    assertEquals("", locale.getCountry());
    assertEquals("", locale.getDisplayVariant());
    assertEquals("", locale.getDisplayCountry());
    assertEquals("English", locale.getDisplayName());
    assertEquals("", locale.getISO3Country());
    assertEquals("en", locale.toString());
  }

  /**
   * Test constructor 6.
   */
  @Test
  public void testConstructor6() {
    ConcurrentDateFormat concurrentDateFormat = new ConcurrentDateFormat("Pattern");
    assertEquals(Alignment.RIGHT, (new StyledDateFormat(concurrentDateFormat)).getAlignment());
    assertNull(concurrentDateFormat.getCalendar());
    assertNull(concurrentDateFormat.getNumberFormat());
  }

}

