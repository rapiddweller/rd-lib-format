package com.rapiddweller.format.text;

import static org.junit.Assert.assertEquals;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

public class NormalizeSpaceConverterTest {
    @Test
    public void testConvert() throws ConversionException {
        assertEquals("Source Value", (new NormalizeSpaceConverter()).convert("Source Value"));
    }
}

