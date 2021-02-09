package com.rapiddweller.format.text;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The type Split string converter test.
 */
public class SplitStringConverterTest {
  /**
   * Test convert.
   *
   * @throws ConversionException the conversion exception
   */
  @Test
  public void testConvert() throws ConversionException {
    assertEquals(1, (new SplitStringConverter('A')).convert("Source Value").length);
  }
}

