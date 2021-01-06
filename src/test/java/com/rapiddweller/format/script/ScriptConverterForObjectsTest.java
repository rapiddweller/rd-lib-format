package com.rapiddweller.format.script;

import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.context.DefaultContext;
import org.junit.Test;

public class ScriptConverterForObjectsTest {
    @Test
    public void testConstructor() {
        assertTrue((new ScriptConverterForObjects(new DefaultContext())).isParallelizable());
    }
}

