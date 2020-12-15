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
package com.rapiddweller.formats.csv;

import static org.junit.Assert.*;

import com.rapiddweller.commons.Encodings;
import com.rapiddweller.formats.DataContainer;
import com.rapiddweller.formats.csv.CSVSingleColumIterator;
import com.rapiddweller.formats.util.DataIteratorTestCase;
import org.junit.Test;

/**
 * Tests the {@link CSVSingleColumIterator}.
 * Created: 14.10.2009 12:06:42
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class CSVSingleColumnIteratorTest extends DataIteratorTestCase {

	private static final String FILENAME = "file://com/rapiddweller/formats/csv/persons.csv";

	@Test
	public void testValidColumns() throws Exception {
		CSVSingleColumIterator iterator0 = new CSVSingleColumIterator(FILENAME, 0, ',', true, Encodings.UTF_8);
		try {
			expectNextElements(iterator0, "name", "Alice", "Bob").withNoNext();
		} finally {
			iterator0.close();
		}
		CSVSingleColumIterator iterator1 = new CSVSingleColumIterator(FILENAME, 1, ',', true, Encodings.UTF_8);
		try {
			expectNextElements(iterator1, "age", "23", "34").withNoNext();
		} finally {
			iterator1.close();
		}
	}
	
	@SuppressWarnings("resource")
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeColumn() throws Exception {
		new CSVSingleColumIterator(FILENAME, -1, ',', true, Encodings.UTF_8);
	}
	
	@Test
	public void testNonExistingColumn() throws Exception {
		CSVSingleColumIterator iterator1 = new CSVSingleColumIterator(FILENAME, 2, ',', true, Encodings.UTF_8);
		try {
			assertNull(iterator1.next(new DataContainer<String>()).getData());
		} finally {
			iterator1.close();
		}
	}
	
}
