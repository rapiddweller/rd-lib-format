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

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Locale;

import com.rapiddweller.common.ReaderLineIterator;
import org.junit.Test;

/**
 * Tests the {@link MultiTypeBeanFixedWidthWriter}.
 * Created: 14.03.2014 16:26:52
 * @since 0.7.2
 * @author Volker Bergmann
 */

public class MultiTypeBeanFixedWidthWriterTest {

	@Test
	public void testDefaultFormats() throws Exception {
		String fileName = "target" + File.separator + getClass().getSimpleName() + ".fcw";
		FileWriter out = new FileWriter(fileName);
		MultiTypeBeanFixedWidthWriter writer = new MultiTypeBeanFixedWidthWriter(out);
		writer.addRowFormat("FWPerson", FixedWidthUtil.parseBeanColumnsSpec("name[8],age[3r0],pet.name[7]", "FWPerson", "", Locale.US));
		writer.addRowFormat("FWCity", FixedWidthUtil.parseBeanColumnsSpec("name[18]", "FWCity", "", Locale.US));
		writer.write(new FWPerson("Alice", 23, new FWPet("Miez")));
		writer.write(new FWCity("New York"));
		writer.close();
		ReaderLineIterator iterator = new ReaderLineIterator(new FileReader(fileName));
		assertTrue(iterator.hasNext());
		assertEquals("Alice   023Miez   ", iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals("New York          ", iterator.next());
		assertFalse(iterator.hasNext());
		iterator.close();
	}
	
}
