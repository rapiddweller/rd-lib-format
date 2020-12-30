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

import java.io.StringWriter;

import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.format.csv.CSVUtil;
import org.junit.Test;

/**
 * Tests the {@link CSVUtil} class.
 * Created: 16.09.2011 13:17:50
 * @since 0.6.2
 * @author Volker Bergmann
 */
public class CSVUtilTest {

	@Test
	public void testRenderCell() {
		// simple test
		assertEquals("Alice", CSVUtil.renderCell("Alice", ','));
		// test cell with comma
		assertEquals("\"Alice,Bob\"", CSVUtil.renderCell("Alice,Bob", ','));
		// test cell with quotes
		assertEquals("\"\"\"Ha! Ha!\"\" Said the clown\"", CSVUtil.renderCell("\"Ha! Ha!\" Said the clown", ','));
		// test cell with quotes and comma
		assertEquals("\"\"\"One, two, three\"\" and so\"", CSVUtil.renderCell("\"One, two, three\" and so", ','));
	}
	
	@Test
	public void testWriteRow() throws Exception {
		StringWriter out = new StringWriter();
		CSVUtil.writeRow(out, ',', null, "A", "B,C", "D\"E\"F", "G,\"H\",I");
		assertEquals(",A,\"B,C\",\"D\"\"E\"\"F\",\"G,\"\"H\"\",I\"" + SystemInfo.getLineSeparator(), out.toString());
	}
}
