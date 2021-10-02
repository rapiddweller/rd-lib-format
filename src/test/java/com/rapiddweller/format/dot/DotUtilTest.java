package com.rapiddweller.format.dot;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link DotUtil}.
 * @author Volker Bergmann
 */
public class DotUtilTest {

  @Test
  public void testNormalizeId() {
    assertEquals("42", DotUtil.normalizeId("42"));
    assertEquals("\"", DotUtil.normalizeId("\""));
    assertEquals("\" \"", DotUtil.normalizeId(" "));
    assertEquals("\"-\"", DotUtil.normalizeId("-"));
  }

  @Test
  public void testNormalizeColor() {
    assertEquals("Color", DotUtil.normalizeColor("Color"));
    assertEquals("\"#\"", DotUtil.normalizeColor("#"));
    assertNull(DotUtil.normalizeColor(""));
  }

  @Test
  public void testSegmentsToLabel() {
    assertNull(DotUtil.segmentsToLabel(new ArrayList<>(), true));
  }

  @Test
  public void testFormatLines() {
    assertEquals("Lines\\l", DotUtil.formatLines("Lines"));
  }

  @Test
  public void testAddLine() {
    StringBuilder b = new StringBuilder();
    DotUtil.addLine("Line", b);
    assertEquals("Line\\l", b.toString());
  }

  @Test
  public void testAddLine2() {
    StringBuilder b = new StringBuilder();
    DotUtil.addLine("\\l", b);
    assertEquals("\\l\\l", b.toString());
  }

}

