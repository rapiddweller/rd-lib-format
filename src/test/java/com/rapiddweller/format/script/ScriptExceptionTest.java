package com.rapiddweller.format.script;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/** Tests {@link ScriptException}. */
public class ScriptExceptionTest {

  @Test
  public void testConstructor1() {
    ScriptException actualScriptException = new ScriptException("An error occurred");
    assertEquals("com.rapiddweller.format.script.ScriptException: An error occurred", actualScriptException.toString());
    assertEquals("An error occurred", actualScriptException.getLocalizedMessage());
    assertNull(actualScriptException.getCause());
    assertEquals("An error occurred", actualScriptException.getMessage());
    assertEquals(0, actualScriptException.getSuppressed().length);
  }

  @Test
  public void testConstructor2() {
    Throwable throwable = new Throwable();
    assertSame((new ScriptException("An error occurred", throwable)).getCause(), throwable);
  }

}

