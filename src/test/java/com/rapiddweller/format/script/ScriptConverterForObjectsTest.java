package com.rapiddweller.format.script;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.context.DefaultContext;
import org.junit.Test;

public class ScriptConverterForObjectsTest {
    @Test
    public void testConstructor() {
        assertTrue((new ScriptConverterForObjects(new DefaultContext())).isParallelizable());
        assertTrue((new ScriptConverterForObjects(new DefaultContext())).isParallelizable());
    }

    @Test
    public void testConvert() throws ConversionException {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        (new ScriptConverterForObjects(new DefaultContext())).convert("sourceValue");
    }

    @Test
    public void testConvert2() throws ConversionException {
        assertEquals(42, ((Integer) (new ScriptConverterForObjects(new DefaultContext())).convert(42)).intValue());
    }
}

