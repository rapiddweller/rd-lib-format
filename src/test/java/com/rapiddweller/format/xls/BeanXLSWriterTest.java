package com.rapiddweller.format.xls;

import org.junit.Test;

import java.io.OutputStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The type Bean xls writer test.
 */
public class BeanXLSWriterTest {
  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    assertEquals("BeanXLSWriter",
        (new BeanXLSWriter<Object>(OutputStream.nullOutputStream(), "Sheet Name")).toString());
  }

  /**
   * Test to string.
   */
  @Test
  public void testToString() {
    assertEquals("BeanXLSWriter",
        (new BeanXLSWriter<Object>(OutputStream.nullOutputStream(), "Sheet Name")).toString());
  }

  /**
   * Test constructor 2.
   */
  @Test
  public void testConstructor2() {
    OutputStream out = OutputStream.nullOutputStream();
    ArrayList<PropFormat> propFormatList = new ArrayList<PropFormat>();
    new BeanXLSWriter<Object>(out, "Sheet Name", propFormatList);
    assertTrue(propFormatList.isEmpty());
  }
}

