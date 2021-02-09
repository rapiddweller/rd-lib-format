package com.rapiddweller.format.text;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The type Normalize space converter test.
 */
public class NormalizeSpaceConverterTest {
  /**
   * Test convert.
   *
   * @throws ConversionException the conversion exception
   */
  @Test
    public void testConvert() throws ConversionException {
        assertEquals("Source Value", (new NormalizeSpaceConverter()).convert("Source Value"));
    }
}

