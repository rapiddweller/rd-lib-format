/*
 * Copyright (C) 2011-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rapiddweller.format.text;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link ToHexConverter}.
 * Created: 29.10.2009 10:26:04
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class ToHexConverterTest {

  /**
   * Test long.
   */
  @Test
  public void testLong() {
    check("0", 0L);
    check("1", 1L);
    check("7fffffffffffffff", Long.MAX_VALUE);
    check("8000000000000000", Long.MIN_VALUE);
    check("ffffffffffffffff", -1L);
  }

  /**
   * Test int.
   */
  @Test
  public void testInt() {
    check("0", 0);
    check("1", 1);
    check("7fffffff", Integer.MAX_VALUE);
    check("80000000", Integer.MIN_VALUE);
    check("ffffffff", -1);
  }

  /**
   * Test short.
   */
  @Test
  public void testShort() {
    check("0", 0);
    check("1", 1);
    check("7fff", Short.MAX_VALUE);
    check("8000", Short.MIN_VALUE);
    check("ffff", (short) -1);
  }

  /**
   * Test byte.
   */
  @Test
  public void testByte() {
    check("0", 0);
    check("1", 1);
    check("7f", Byte.MAX_VALUE);
    check("80", Byte.MIN_VALUE);
    check("ff", (byte) -1);
  }

  /**
   * Test char.
   */
  @Test
  public void testChar() {
    check("30", '0');
    check("41", 'A');
    check("0a", '\n');
  }

  /**
   * Test string.
   */
  @Test
  public void testString() {
    check("41300a", "A0\n");
  }

  /**
   * Test length.
   */
  @Test
  public void testLength() {
    checkLength("ff", 0xff, 2);
    checkLength("00ff", 0xff, 4);
  }

  /**
   * Test pattern.
   */
  @Test
  public void testPattern() {
    checkPattern("ffh", 0xff, "{0}h");
    checkPattern("0xff", 0xff, "0x{0}");
  }

  /**
   * Test case.
   */
  @Test
  public void testCase() {
    checkUpperCase("FF", 0xff);
  }

  /**
   * Test combined options.
   */
  @Test
  public void testCombinedOptions() {
    assertEquals("0x00FF", new ToHexConverter(true, "0x{0}", 4).convert(0xff));
  }

  private static void check(String expected, Object in) {
    assertEquals(expected, new ToHexConverter().convert(in));
  }

  private static void checkLength(String expected, Object in, int length) {
    assertEquals(expected, new ToHexConverter(false, null, length).convert(in));
  }

  private static void checkUpperCase(String expected, Object in) {
    assertEquals(expected, new ToHexConverter(true).convert(in));
  }

  private static void checkPattern(String expected, Object in, String pattern) {
    assertEquals(expected, new ToHexConverter(false, pattern).convert(in));
  }

}
