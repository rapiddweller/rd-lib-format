package com.rapiddweller.format.demo;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The type Text filter demo test.
 */
public class TextFilterDemoTest {
  /**
   * Test line filter accept.
   */
  @Test
  public void testLineFilterAccept() {
    assertFalse((new TextFilterDemo.LineFilter()).accept("2020-03-01"));
    assertTrue((new TextFilterDemo.LineFilter()).accept("|3023293310905|"));
    assertFalse((new TextFilterDemo.LineFilter()).accept("2020-03-01"));
    assertTrue((new TextFilterDemo.LineFilter()).accept("|3023293310905|"));
  }
}

