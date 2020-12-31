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

import static org.junit.Assert.*;

import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.csv.CSVLineIterator;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * Tests the {@link CSVLineIterator}.
 * Created: 29.09.2006 16:15:23
 * @since 0.1
 * @author Volker Bergmann
 */
public class CSVLineIteratorTest {

	@Test
    public void testIgnoringEmptyLines() throws IOException {
        CSVLineIterator iterator = new CSVLineIterator("file://com/rapiddweller/format/csv/names.csv", ',', true);
        DataContainer<String[]> container = new DataContainer<String[]>();
        assertTrue(Arrays.equals(new String[] { "Alice", "Bob" },               iterator.next(container).getData()));
        assertTrue(Arrays.equals(new String[] { "Charly" },                     iterator.next(container).getData()));
        assertTrue(Arrays.equals(new String[] { "Dieter", "Indiana\nJones" }, iterator.next(container).getData()));
        assertNull(iterator.next(container));
        iterator.close();
    }

	@Test
    public void testIncludingEmptyLines() throws IOException {
        CSVLineIterator iterator = new CSVLineIterator("file://com/rapiddweller/format/csv/names.csv", ',', false);
        DataContainer<String[]> container = new DataContainer<String[]>();
        assertTrue(Arrays.equals(new String[] { "Alice", "Bob" },               iterator.next(container).getData()));
        assertTrue(Arrays.equals(new String[] { "Charly" },                     iterator.next(container).getData()));
        assertTrue(Arrays.equals(new String[] { },                              iterator.next(container).getData()));
        assertTrue(Arrays.equals(new String[] { "Dieter", "Indiana\nJones" }, iterator.next(container).getData()));
        assertNull(iterator.next(container));
        iterator.close();
    }
	
	@Test
    public void testEmptyFile() throws IOException {
        CSVLineIterator iterator = new CSVLineIterator("string://", ',', false);
        DataContainer<String[]> container = new DataContainer<String[]>();
        assertNull(iterator.next(container));
        iterator.close();
    }
	
	@Test
    public void testThreeEmptyLines() throws IOException {
        CSVLineIterator iterator = new CSVLineIterator("string://\n\n\n", ',', false);
        DataContainer<String[]> container = new DataContainer<String[]>();
        assertTrue(Arrays.equals(new String[0], iterator.next(container).getData()));
        assertTrue(Arrays.equals(new String[0], iterator.next(container).getData()));
        assertTrue(Arrays.equals(new String[0], iterator.next(container).getData()));
        assertNull(iterator.next(container));
        iterator.close();
    }
	
	@Test
    public void testEachLineEndingWithCrLf() throws IOException {
        CSVLineIterator iterator = new CSVLineIterator("string://DATA\n\nDATA2\n", ',', false);
        DataContainer<String[]> container = new DataContainer<String[]>();
        assertTrue(Arrays.equals(new String[] { "DATA" }, iterator.next(container).getData()));
        assertTrue(Arrays.equals(new String[0], iterator.next(container).getData()));
        assertTrue(Arrays.equals(new String[] { "DATA2" }, iterator.next(container).getData()));
        assertNull(iterator.next(container));
        iterator.close();
    }
	
	@Test
    public void testLastLineWithoutCrLf() throws IOException {
        CSVLineIterator iterator = new CSVLineIterator("string://DATA\n\nDATA2", ',', false);
        DataContainer<String[]> container = new DataContainer<String[]>();
        assertTrue(Arrays.equals(new String[] { "DATA" }, iterator.next(container).getData()));
        assertTrue(Arrays.equals(new String[0], iterator.next(container).getData()));
        assertTrue(Arrays.equals(new String[] { "DATA2" }, iterator.next(container).getData()));
        assertNull(iterator.next(container));
        iterator.close();
    }
	
	@Test
    public void testEmptyRowAtBeginning() throws IOException {
        CSVLineIterator iterator = new CSVLineIterator("string://\nDATA\nDATA2\n", ',', false);
        DataContainer<String[]> container = new DataContainer<String[]>();
        assertTrue(Arrays.equals(new String[0], iterator.next(container).getData()));
        assertTrue(Arrays.equals(new String[] { "DATA" }, iterator.next(container).getData()));
        assertTrue(Arrays.equals(new String[] { "DATA2" }, iterator.next(container).getData()));
        assertNull(iterator.next(container));
        iterator.close();
    }
	
	@Test
    public void testEmptyRowBetween() throws IOException {
        CSVLineIterator iterator = new CSVLineIterator("string://DATA\n\nDATA2\n", ',', false);
        DataContainer<String[]> container = new DataContainer<String[]>();
        assertTrue(Arrays.equals(new String[] { "DATA" }, iterator.next(container).getData()));
        assertTrue(Arrays.equals(new String[0], iterator.next(container).getData()));
        assertTrue(Arrays.equals(new String[] { "DATA2" }, iterator.next(container).getData()));
        assertNull(iterator.next(container));
        iterator.close();
    }
	
	@Test
    public void testEmptyRowAtEnd() throws IOException {
        CSVLineIterator iterator = new CSVLineIterator("string://DATA\nDATA2\n\n", ',', false);
        DataContainer<String[]> container = new DataContainer<String[]>();
        assertTrue(Arrays.equals(new String[] { "DATA" }, iterator.next(container).getData()));
        assertTrue(Arrays.equals(new String[] { "DATA2" }, iterator.next(container).getData()));
        assertTrue(Arrays.equals(new String[0], iterator.next(container).getData()));
        assertNull(iterator.next(container));
        iterator.close();
    }
	
	@Test
    public void testEmptyCells() throws IOException {
        CSVLineIterator iterator = new CSVLineIterator("string://name,\"\",,x\n,\"\",");
        DataContainer<String[]> container = new DataContainer<String[]>();
		assertTrue(Arrays.equals(new String[] { "name", "", null, "x" }, iterator.next(container).getData()));
		assertTrue(Arrays.equals(new String[] { null, "", null }, iterator.next(container).getData()));
        assertNull(iterator.next(container));
        iterator.close();
    }
	
}
