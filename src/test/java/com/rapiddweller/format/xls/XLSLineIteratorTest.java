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

package com.rapiddweller.format.xls;

import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.common.TimeUtil;
import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.util.DataIteratorTestCase;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link XLSLineIterator}.
 * Created at 28.01.2009 09:10:26
 *
 * @author Volker Bergmann
 * @since 0.4.8
 */
public class XLSLineIteratorTest extends DataIteratorTestCase {

  private static final String PERSON_FILENAME = "com/rapiddweller/format/xls/person_lines.xls";
  private static final String VALUES_FILENAME = "com/rapiddweller/format/xls/types_and_values.xls";
  private static final String ALTERNATIVE_EMPTY_FILENAME = "com/rapiddweller/format/xls/alternative_empty.xls";
  private static final String NULL_AND_EMPTY_FILENAME = "com/rapiddweller/format/xls/null_and_empty.xls";
  private static final String POI_TESTDATA_XLS = "com/rapiddweller/format/xls/poiTestData.xls";
  private static final String POI_TESTDATA_XLSX = "com/rapiddweller/format/xls/poiTestData.xlsx";


  /**
   * Test default sheet with formula.
   *
   * @throws Exception the exception
   */
  @Test
  public void testXls() throws Exception {
    // test default sheet
    try (XLSLineIterator iterator = new XLSLineIterator(POI_TESTDATA_XLS, 0)) {
      // check headers
      assertArrayEquals(new Object[] {"A", "B", "C", "D"}, iterator.next(new DataContainer<Object[]>()).getData());
      // check normal row
      expectNext(iterator, 1L, "a", null, 100L);
      // check next row
      expectNext(iterator, 2L, "b", null, 200L);
      // check next row
      expectNext(iterator, 3L, "c", null, 300L);
      // check next row
      expectNext(iterator, 4L, "d", null, 400L);
      // check next row
      expectNext(iterator, 5L, "e", null, 500L);
      // check end of sheet
      expectUnavailable(iterator);
    }
  }

  /**
   * Test default sheet with formula.
   *
   * @throws Exception the exception
   */
  @Test
  public void testXlsx() throws Exception {
    // test default sheet
    try (XLSLineIterator iterator = new XLSLineIterator(POI_TESTDATA_XLSX, 0)) {
      // check headers
      assertArrayEquals(new Object[] {"A", "B", "C", "D"}, iterator.next(new DataContainer<Object[]>()).getData());
      // check normal row
      expectNext(iterator, 1L, "a", null, 100L);
      // check next row
      expectNext(iterator, 2L, "b", null, 200L);
      // check next row
      expectNext(iterator, 3L, "c", null, 300L);
      // check next row
      expectNext(iterator, 4L, "d", null, 400L);
      // check next row
      expectNext(iterator, 5L, "e", null, 500L);
      // check end of sheet
      expectUnavailable(iterator);
    }
  }

  /**
   * Test default sheet with formula.
   *
   * @throws Exception the exception
   */
  @Test
  public void testDefaultSheetWithFormula() throws Exception {
    // test default sheet
    try (XLSLineIterator iterator = new XLSLineIterator(PERSON_FILENAME)) {
      // check headers
      assertArrayEquals(new Object[] {"name", "age", "date"}, iterator.next(new DataContainer<Object[]>()).getData());
      // check normal row
      expectNext(iterator, "Alice", 23L, TimeUtil.date(2011, 0, 1));
      // test formula
      expectNext(iterator, "Bob", 34L, TimeUtil.date(2011, 0, 2));
      // check end of sheet
      expectUnavailable(iterator);
    }
  }

  /**
   * Demo default sheet with formula.
   *
   * @throws Exception the exception
   */
  @Test
  public void demoDefaultSheetWithFormula() throws Exception {
    // print out default sheet content
    try (XLSLineIterator iterator = new XLSLineIterator(PERSON_FILENAME)) {
      DataContainer<Object[]> container = new DataContainer<Object[]>();
      while ((container = iterator.next(container)) != null) {
        Object[] row = container.getData();
        System.out.println(row[0] + ", " + row[1]);
      }
    }
  }

  /**
   * Test sheet 1.
   *
   * @throws Exception the exception
   */
  @Test
  public void testSheet1() throws Exception {
    // test sheet 1
    try (XLSLineIterator iterator = new XLSLineIterator(PERSON_FILENAME, 1)) {
      assertArrayEquals(new Object[] {"name", "age"}, iterator.next(new DataContainer<Object[]>()).getData());
      expectNext(iterator, "Otto", 89L);
      expectUnavailable(iterator);
    }
  }

  /**
   * Test without header.
   *
   * @throws Exception the exception
   */
  @Test
  public void testWithoutHeader() throws Exception {
    // test sheet 1
    try (XLSLineIterator iterator = new XLSLineIterator(PERSON_FILENAME, 1)) {
      expectNext(iterator, "name", "age");
      expectNext(iterator, "Otto", 89L);
      expectUnavailable(iterator);
    }
  }

  /**
   * Test types and values.
   *
   * @throws Exception the exception
   */
  @Test
  public void testTypesAndValues() throws Exception {
    // test default sheet
    try (XLSLineIterator iterator = new XLSLineIterator(VALUES_FILENAME)) {
      // check headers
      Object[] expectedHeaders = new Object[] {
          "text", "emptyText", "null", "numberAsText", "number", "date", "formular", "formular2"};
      assertArrayEquals(expectedHeaders, iterator.next(new DataContainer<Object[]>()).getData());
      // check data
      Object[] data = iterator.next(new DataContainer<Object[]>()).getData();
      assertEquals(expectedHeaders.length, data.length);
      assertEquals("Simple Text", data[0]);
      assertEquals("", data[1]);
      assertEquals(null, data[2]);
      assertEquals("123", data[3]);
      assertEquals(42L, data[4]);
      assertEquals(TimeUtil.date(2011, 1, 1), data[5]);
      assertEquals(4200L, data[6]);
      assertEquals(null, data[7]);
      // check end of sheet
      expectUnavailable(iterator);
    }
  }

  /**
   * Test alternative empty marker.
   *
   * @throws Exception the exception
   */
  @Test
  public void testAlternativeEmptyMarker() throws Exception {
    // test default sheet
    XLSLineIterator iterator = new XLSLineIterator(ALTERNATIVE_EMPTY_FILENAME);
    try (iterator) {
      iterator.setEmptyMarker("\"\"");
      assertArrayEquals(new Object[] {"text", "empty"}, iterator.next(new DataContainer<Object[]>()).getData());
      assertArrayEquals(new Object[] {"X", ""}, iterator.next(new DataContainer<Object[]>()).getData());
      expectUnavailable(iterator);
    }
  }

  /**
   * Test null and empty.
   *
   * @throws Exception the exception
   */
  @Test
  public void testNullAndEmpty() throws Exception {
    // test default sheet
    try (XLSLineIterator iterator = new XLSLineIterator(NULL_AND_EMPTY_FILENAME)) {
      DataContainer<Object[]> wrapper = new DataContainer<Object[]>();
      assertArrayEquals(new Object[] {"text", "dummy"}, iterator.next(wrapper).getData());
      assertArrayEquals(new Object[] {null, "x"}, iterator.next(wrapper).getData());
      assertArrayEquals(new Object[] {"", "y"}, iterator.next(wrapper).getData());
      expectUnavailable(iterator);
    }
  }


  // private helpers ---------------------------------------------------------

  private static void expectNext(XLSLineIterator iterator, Object... expected) {
    Object[] actual = ArrayUtil.copyOfRange(iterator.next(new DataContainer<Object[]>()).getData(), 0, expected.length);
    assertArrayEquals(expected, actual);
  }

}
