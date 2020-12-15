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
package com.rapiddweller.formats.xls;

import com.rapiddweller.commons.ArrayUtil;
import com.rapiddweller.commons.TimeUtil;
import com.rapiddweller.formats.DataContainer;
import com.rapiddweller.formats.util.DataIteratorTestCase;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the {@link XLSLineIterator}.
 * Created at 28.01.2009 09:10:26
 * @since 0.4.8
 * @author Volker Bergmann
 */

public class XLSLineIteratorTest extends DataIteratorTestCase {

	private static final String PERSON_FILENAME = "com/rapiddweller/formats/xls/person_lines.xls";
	private static final String VALUES_FILENAME = "com/rapiddweller/formats/xls/types_and_values.xls";
	private static final String ALTERNATIVE_EMPTY_FILENAME = "com/rapiddweller/formats/xls/alternative_empty.xls";
	private static final String NULL_AND_EMPTY_FILENAME = "com/rapiddweller/formats/xls/null_and_empty.xls";

    @Test
	public void testDefaultSheetWithFormula() throws Exception {
		// test default sheet
		XLSLineIterator iterator = new XLSLineIterator(PERSON_FILENAME);
		try {
			// check headers
			assertArrayEquals(new Object[] {"name", "age", "date"}, iterator.next(new DataContainer<Object[]>()).getData());
			// check normal row
			expectNext(iterator, "Alice", 23L, TimeUtil.date(2011, 0, 1));
			// test formula
			expectNext(iterator, "Bob", 34L, TimeUtil.date(2011, 0, 2));
			// check end of sheet
			expectUnavailable(iterator);
		} finally {
			iterator.close();
		}
	}
	
    @Test
	public void demoDefaultSheetWithFormula() throws Exception {
    	// print out default sheet content
		XLSLineIterator iterator = new XLSLineIterator(PERSON_FILENAME);
		try {
			DataContainer<Object[]> container = new DataContainer<Object[]>();
			while ((container = iterator.next(container)) != null) {
				Object[] row = container.getData();
				System.out.println(row[0] + ", " + row[1]);
			}
		} finally {
			iterator.close();
		}
	}
	
    @Test
	public void testSheet1() throws Exception {
		// test sheet 1
		XLSLineIterator iterator = new XLSLineIterator(PERSON_FILENAME, 1);
		try {
			assertArrayEquals(new Object[] {"name", "age"}, iterator.next(new DataContainer<Object[]>()).getData());
			expectNext(iterator, "Otto", 89L);
			expectUnavailable(iterator);
		} finally {
			iterator.close();
		}
	}
	
    @Test
	public void testWithoutHeader() throws Exception {
		// test sheet 1
		XLSLineIterator iterator = new XLSLineIterator(PERSON_FILENAME, 1);
		try {
			expectNext(iterator, "name", "age");
			expectNext(iterator, "Otto", 89L);
			expectUnavailable(iterator);
		} finally {
			iterator.close();
		}
	}
	
    @Test
	public void testTypesAndValues() throws Exception {
		// test default sheet
		XLSLineIterator iterator = new XLSLineIterator(VALUES_FILENAME);
		try {
			// check headers
			Object[] expectedHeaders = new Object[] {
					"text", "emptyText", "null", "numberAsText", "number", "date"};
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
			// check end of sheet
			expectUnavailable(iterator);
		} finally {
			iterator.close();
		}
	}
	
    @Test
	public void testAlternativeEmptyMarker() throws Exception {
		// test default sheet
		XLSLineIterator iterator = new XLSLineIterator(ALTERNATIVE_EMPTY_FILENAME);
		iterator.setEmptyMarker("\"\"");
		try {
			assertArrayEquals(new Object[] { "text", "empty" }, iterator.next(new DataContainer<Object[]>()).getData());
			assertArrayEquals(new Object[] { "X", "" }, iterator.next(new DataContainer<Object[]>()).getData());
			expectUnavailable(iterator);
		} finally {
			iterator.close();
		}
	}
	
    @Test
	public void testNullAndEmpty() throws Exception {
		// test default sheet
		XLSLineIterator iterator = new XLSLineIterator(NULL_AND_EMPTY_FILENAME);
		DataContainer<Object[]> wrapper = new DataContainer<Object[]>();
		try {
			assertArrayEquals(new Object[] { "text", "dummy" }, iterator.next(wrapper).getData());
			assertArrayEquals(new Object[] {  null , "x"     }, iterator.next(wrapper).getData());
			assertArrayEquals(new Object[] {  ""   , "y"     }, iterator.next(wrapper).getData());
			expectUnavailable(iterator);
		} finally {
			iterator.close();
		}
	}
	
	// private helpers ---------------------------------------------------------
	
	private static void expectNext(XLSLineIterator iterator, Object... expected) {
		Object[] actual = ArrayUtil.copyOfRange(iterator.next(new DataContainer<Object[]>()).getData(), 0, expected.length);
		assertArrayEquals(expected, actual);
	}
	
}
