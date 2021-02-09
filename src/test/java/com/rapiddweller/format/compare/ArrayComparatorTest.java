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
package com.rapiddweller.format.compare;

import com.rapiddweller.common.CollectionUtil;
import com.rapiddweller.common.converter.XMLNode2StringConverter;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link ArrayComparator}.
 * Created: 20.11.2013 19:08:28
 *
 * @author Volker Bergmann
 * @since 1.0.5
 */
public class ArrayComparatorTest {
	
	private final DiffFactory diffFactory = new DiffFactory(new XMLNode2StringConverter());

  /**
   * Test identical lists.
   */
  @Test
	public void testIdenticalLists() {
		String[] l1 = new String[] { "A", "B", "C" };
		String[] l2 = new String[] { "A", "B", "C" };
		ArrayComparisonResult result = ArrayComparator.compare(l1, l2, new StringComparisonModel(), "", "", diffFactory);
		assertTrue(result.identical());
	}

  /**
   * Test empty lists.
   */
  @Test
	public void testEmptyLists() {
		String[] l1 = new String[] { };
		String[] l2 = new String[] { };
		ArrayComparisonResult result = ArrayComparator.compare(l1, l2, new StringComparisonModel(), "", "", diffFactory);
		assertTrue(result.identical());
	}

  /**
   * Test removed last.
   */
  @Test
	public void testRemovedLast() {
		check(
			new String[] { "A", "B", "C" }, 
			new String[] { "A", "B" }, 
			diffFactory.missing("C", "list element", "[2]")
		);
	}

  /**
   * Test removed middle.
   */
  @Test
	public void testRemovedMiddle() {
		check(
			new String[] { "A", "B", "C" }, 
			new String[] { "A", "C" }, 
			diffFactory.missing("B", "list element", "[1]")
		);
	}

  /**
   * Test removed first.
   */
  @Test
	public void testRemovedFirst() {
		check(
			new String[] { "A", "B", "C" }, 
			new String[] { "B", "C" }, 
			diffFactory.missing("A", "list element", "[0]")
		);
	}

  /**
   * Test added end.
   */
  @Test
	public void testAddedEnd() {
		check(
			new String[] { "A", "B", "C" }, 
			new String[] { "A", "B", "C", "X" }, 
			diffFactory.unexpected("X", "list element", "[3]")
		);
	}

  /**
   * Test added in between.
   */
  @Test
	public void testAddedInBetween() {
		check(
			new String[] { "A", "B", "C" }, 
			new String[] { "A", "X", "B", "C" }, 
			diffFactory.unexpected("X", "list element", "[1]")
		);
	}

  /**
   * Test added beginning.
   */
  @Test
	public void testAddedBeginning() {
		check(
			new String[] { "A", "B", "C" }, 
			new String[] { "X", "A", "B", "C" }, 
			diffFactory.unexpected("X", "list element", "[0]")
		);
	}

  /**
   * Test swapped neighbours.
   */
  @Test
	public void testSwappedNeighbours() {
		check(
			new String[] { "A", "B", "C" }, 
			new String[] { "A", "C", "B" }, 
			diffFactory.moved("B", "list element", "[1]", "[2]")
		);
	}

  /**
   * Test swapped ends.
   */
  @Test
	public void testSwappedEnds() {
		String[] l1 = new String[] { "A", "B", "C" };
		String[] l2 = new String[] { "C", "B", "A" };
		ArrayComparisonResult result = ArrayComparator.compare(l1, l2, new StringComparisonModel(), "", "", diffFactory);
		assertFalse(result.identical());
		check(
			new String[] { "A", "B", "C" }, 
			new String[] { "C", "B", "A" }, 
			diffFactory.moved("A", "list element", "[0]", "[2]")
		);
	}

  /**
   * Test ring change.
   */
  @Test
	public void testRingChange() {
		String[] l1 = new String[] { "A", "B", "C" };
		String[] l2 = new String[] { "B", "C", "A" };
		ArrayComparisonResult result = ArrayComparator.compare(l1, l2, new StringComparisonModel(), "", "", diffFactory);
		assertFalse(result.identical());
		check(
			new String[] { "A", "B", "C" }, 
			new String[] { "C", "B", "A" }, 
			diffFactory.moved("A", "list element", "[0]", "[2]")
		);
	}

  /**
   * Test changed.
   */
  @Test
	public void testChanged() {
		check(
			new String[] { "A", "B",  "C" }, 
			new String[] { "A", "B2", "C" }, 
			diffFactory.different("B", "B2", "list element", "[1]", "[1]")
		);
	}

  /**
   * Test removed and added.
   */
  @Test
	public void testRemovedAndAdded() {
		check(
			new String[] { "A", "B",  "C" }, 
			new String[] { "A", "X", "C" }, 
			diffFactory.missing("B", "list element", "[1]"),
			diffFactory.unexpected("X", "list element", "[1]")
		);
	}

  /**
   * Test moved and changed.
   */
  @Test
	public void testMovedAndChanged() {
		check(
			new String[] { "A", "B",  "C" }, 
			new String[] { "A", "C", "B2" }, 
			diffFactory.moved("B", "string", "[1]", "[2]"),
			diffFactory.different("B", "B2", "string", "[1]", "[2]")
		);
	}

  /**
   * Test all change types.
   */
  @Test
	public void testAllChangeTypes() {
		check(
				new String[] { "A", "B", "C", "D",  "E" }, 
				new String[] { "A", "X", "B", "D2", "C" }, 
				diffFactory.unexpected("X", "list element", "[1]"),
				diffFactory.moved("C", "list element", "[2]", "[4]"),
				diffFactory.different("D", "D2", "list element", "[3]", "[3]"),
				diffFactory.missing("E", "list element", "[4]")
			);
	}
	
	
	// private helpers -------------------------------------------------------------------------------------------------
	
	private void check(String[] list1, String[] list2, DiffDetail... expectedDiffs) {
		ArrayComparisonResult result = ArrayComparator.compare(list1, list2, new StringComparisonModel(), "", "", diffFactory);
		if (expectedDiffs.length > 0)
			assertFalse(result.identical());
		else
			assertTrue(result.identical());
		List<DiffDetail> actualDiffs = result.getDiffs();
		for (DiffDetail diff : actualDiffs)
			System.out.println(diff);
		assertArrayEquals(expectedDiffs, CollectionUtil.toArray(actualDiffs));
	}

  /**
   * The type String comparison model.
   */
  static class StringComparisonModel implements ComparisonModel {

		@Override
		public String classifierOf(Object object) {
			return "string";
		}

		@Override
		public void addKeyExpression(String locator, String keyExpression) {
			// not supported
		}
		
		@Override
		public List<KeyExpression> getKeyExpressions() {
			return null;
		}

		@Override
		public boolean equal(Object o1, Object o2) {
			return o1.equals(o2);
		}

		@Override
		public boolean correspond(Object o1, Object o2) {
			return (((String) o1).charAt(0) == ((String) o2).charAt(0));
		}

		@Override
		public String subPath(Object[] array, int index) {
			return "[" + index + "]";
		}

	}
	
}
