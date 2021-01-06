package com.rapiddweller.format.script;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class ScriptExceptionTest {
    @Test
    public void testConstructor() {
        ScriptException actualScriptException = new ScriptException();
        assertEquals("com.rapiddweller.format.script.ScriptException", actualScriptException.toString());
        assertNull(actualScriptException.getLocalizedMessage());
        assertNull(actualScriptException.getCause());
        assertNull(actualScriptException.getMessage());
        assertEquals(0, actualScriptException.getSuppressed().length);
    }

    @Test
    public void testConstructor2() {
        ScriptException actualScriptException = new ScriptException("An error occurred");
        assertEquals("com.rapiddweller.format.script.ScriptException: An error occurred", actualScriptException.toString());
        assertEquals("An error occurred", actualScriptException.getLocalizedMessage());
        assertNull(actualScriptException.getCause());
        assertEquals("An error occurred", actualScriptException.getMessage());
        assertEquals(0, actualScriptException.getSuppressed().length);
    }

    @Test
    public void testConstructor3() {
        Throwable throwable = new Throwable();
        assertSame((new ScriptException("An error occurred", throwable)).getCause(), throwable);
    }

    @Test
    public void testConstructor4() {
        Throwable throwable = new Throwable();
        assertSame((new ScriptException(throwable)).getCause(), throwable);
    }
}

