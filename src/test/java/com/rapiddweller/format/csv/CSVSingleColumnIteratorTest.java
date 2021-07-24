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

package com.rapiddweller.format.csv;

import com.rapiddweller.common.Encodings;
import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.util.DataIteratorTestCase;
import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * Tests the {@link CSVSingleColumIterator}.
 * Created: 14.10.2009 12:06:42
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class CSVSingleColumnIteratorTest extends DataIteratorTestCase {

  private static final String FILENAME = "file://com/rapiddweller/format/csv/persons.csv";

  /**
   * Test valid columns.
   *
   * @throws Exception the exception
   */
  @Test
  public void testValidColumns() throws Exception {
    try (CSVSingleColumIterator iterator0 = new CSVSingleColumIterator(FILENAME, 0, ',', true, Encodings.UTF_8)) {
      expectNextElements(iterator0, "name", "Alice", "Bob").withNoNext();
    }
    try (CSVSingleColumIterator iterator1 = new CSVSingleColumIterator(FILENAME, 1, ',', true, Encodings.UTF_8)) {
      expectNextElements(iterator1, "age", "23", "34").withNoNext();
    }
  }

  /**
   * Test negative column.
   *
   * @throws Exception the exception
   */
  @SuppressWarnings("resource")
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeColumn() throws Exception {
    new CSVSingleColumIterator(FILENAME, -1, ',', true, Encodings.UTF_8);
  }

  /**
   * Test non existing column.
   *
   * @throws Exception the exception
   */
  @Test
  public void testNonExistingColumn() throws Exception {
    try (CSVSingleColumIterator iterator1 = new CSVSingleColumIterator(FILENAME, 2, ',', true, Encodings.UTF_8)) {
      assertNull(iterator1.next(new DataContainer<>()).getData());
    }
  }

}
