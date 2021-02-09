package com.rapiddweller.format.demo;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The type Csv filter demo test.
 */
public class CSVFilterDemoTest {
  /**
   * Test row filter accept.
   */
  @Test
  public void testRowFilterAccept() {
    assertFalse((new CSVFilterDemo.RowFilter()).accept(new String[] {"foo", "foo", "foo"}));
    assertTrue((new CSVFilterDemo.RowFilter()).accept(new String[] {"foo", "3023293310905", "foo"}));
    assertFalse((new CSVFilterDemo.RowFilter()).accept(new String[] {"2020-03-01"}));
    assertFalse((new CSVFilterDemo.RowFilter()).accept(new String[] {"foo", "foo", "foo"}));
    assertTrue((new CSVFilterDemo.RowFilter()).accept(new String[] {"foo", "3023293310905", "foo"}));
    assertFalse((new CSVFilterDemo.RowFilter()).accept(new String[] {"2020-03-01"}));
  }
}

