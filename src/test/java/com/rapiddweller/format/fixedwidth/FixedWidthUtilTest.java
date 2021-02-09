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
package com.rapiddweller.format.fixedwidth;

import com.rapiddweller.common.format.Alignment;
import org.junit.Test;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.junit.Assert.assertArrayEquals;

/**
 * Tests the {@link FixedWidthUtil}.
 * Created at 29.04.2008 19:06:41
 *
 * @author Volker Bergmann
 * @since 0.4.2
 */
public class FixedWidthUtilTest {

  /**
   * Test left.
   *
   * @throws Exception the exception
   */
  @Test
	public void testLeft() throws Exception {
		FixedWidthColumnDescriptor d3l = new FixedWidthColumnDescriptor("name", 3, Alignment.LEFT, ' ');
		FixedWidthColumnDescriptor[] array = new FixedWidthColumnDescriptor[] { d3l };
		assertArrayEquals(array, parse("name[3]"));
		assertArrayEquals(array, parse("name[3l]"));
		assertArrayEquals(array, parse("name[3l ]"));
	}

  /**
   * Test right.
   *
   * @throws Exception the exception
   */
  @Test
	public void testRight() throws Exception {
		FixedWidthColumnDescriptor d3r = new FixedWidthColumnDescriptor("name", 3, Alignment.RIGHT, ' ');
		FixedWidthColumnDescriptor[] array = new FixedWidthColumnDescriptor[] { d3r };
		assertArrayEquals(array, parse("name[3r]"));
		assertArrayEquals(array, parse("name[3r ]"));
	}

  /**
   * Test center.
   *
   * @throws Exception the exception
   */
  @Test
	public void testCenter() throws Exception {
		FixedWidthColumnDescriptor d3c = new FixedWidthColumnDescriptor("name", 3, Alignment.CENTER, ' ');
		FixedWidthColumnDescriptor[] array = new FixedWidthColumnDescriptor[] { d3c };
		assertArrayEquals(array, parse("name[3c]"));
		assertArrayEquals(array, parse("name[3c ]"));
	}

  /**
   * Test multiple.
   *
   * @throws Exception the exception
   */
  @Test
	public void testMultiple() throws Exception {
		FixedWidthColumnDescriptor n3l = new FixedWidthColumnDescriptor("name", 3, Alignment.LEFT, ' ');
		FixedWidthColumnDescriptor a3r = new FixedWidthColumnDescriptor("age",  3, Alignment.RIGHT, '0');
		FixedWidthColumnDescriptor[] array = new FixedWidthColumnDescriptor[] { n3l, a3r };
		assertArrayEquals(array, parse("name[3l],age[3r0]"));
	}

  /**
   * Test parse pad char.
   *
   * @throws Exception the exception
   */
  @Test
	public void testParsePadChar() throws Exception {
		FixedWidthColumnDescriptor d3l = new FixedWidthColumnDescriptor("name", 3, Alignment.LEFT, '_');
		FixedWidthColumnDescriptor[] d3l_a = new FixedWidthColumnDescriptor[] { d3l };
		assertArrayEquals(d3l_a, parse("name[3l_]"));
	}

  /**
   * Test date format.
   *
   * @throws Exception the exception
   */
  @Test
	public void testDateFormat() throws Exception {
		FixedWidthColumnDescriptor d = new FixedWidthColumnDescriptor("date", new SimpleDateFormat("yyyyMMdd", DateFormatSymbols.getInstance(Locale.US)), "");
		FixedWidthColumnDescriptor[] array = new FixedWidthColumnDescriptor[] { d };
		assertArrayEquals(array, parse("date[DyyyyMMdd]"));
	}

  /**
   * Test number format.
   *
   * @throws Exception the exception
   */
  @Test
	public void testNumberFormat() throws Exception {
		FixedWidthColumnDescriptor n = new FixedWidthColumnDescriptor("num", new DecimalFormat("00.00", DecimalFormatSymbols.getInstance(Locale.US)), "");
		FixedWidthColumnDescriptor[] array = new FixedWidthColumnDescriptor[] { n };
		assertArrayEquals(array, parse("num[N00.00]"));
	}
	
	
	// private helper method -------------------------------------------------------------------------------------------
	
	private static FixedWidthColumnDescriptor[] parse(String pattern) throws ParseException {
		return FixedWidthUtil.parseBeanColumnsSpec(pattern, "test", "", Locale.US).getColumnDescriptors();
	}
	
}
