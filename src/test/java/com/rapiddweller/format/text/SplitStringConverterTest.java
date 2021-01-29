package com.rapiddweller.format.text;

import static org.junit.Assert.assertEquals;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

public class SplitStringConverterTest {
    @Test
    public void testConvert() throws ConversionException {
        assertEquals(1, (new SplitStringConverter('A')).convert("Source Value").length);
    }
}

