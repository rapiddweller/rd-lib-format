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

import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.SystemInfo;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Tests the {@link CSVUtil} class.
 * Created: 16.09.2011 13:17:50
 * @author Volker Bergmann
 * @since 0.6.2
 */
public class CSVUtilTest {

  @Test
  public void testParseCSVRow() {
    assertEquals(1, CSVUtil.parseCSVRow("Text").length);
  }

  @Test
  public void testRenderCell() {
    // simple test
    assertEquals("Alice", CSVUtil.renderCell("Alice", ',', true));
    // test cell with comma
    assertEquals("\"Alice,Bob\"", CSVUtil.renderCell("Alice,Bob", ',', true));
    // test cell with quotes
    assertEquals("\"\"\"Ha! Ha!\"\" Said the clown\"", CSVUtil.renderCell("\"Ha! Ha!\" Said the clown", ',', true));
    // test cell with quotes and comma
    assertEquals("\"\"\"One, two, three\"\" and so\"", CSVUtil.renderCell("\"One, two, three\" and so", ',', true));
    assertEquals("Text", CSVUtil.renderCell("Text", 'A', true));
    assertEquals("", CSVUtil.renderCell(null, 'A', true));
    assertEquals("\"java.lang.String\"", CSVUtil.renderCell("java.lang.String", 'a', true));
    assertEquals("\"\"\"\"", CSVUtil.renderCell("\"", 'a', true));
  }

  @Test
  public void testFormatHeaderWithLineFeed() {
    assertEquals("Property Names\n", CSVUtil.formatHeaderWithLineFeed('A', "Property Names"));
  }

  @Test
  public void testWriteRow() throws Exception {
    StringWriter out = new StringWriter();
    CSVUtil.writeRow(out, ',', null, "A", "B,C", "D\"E\"F", "G,\"H\",I");
    assertEquals(",A,\"B,C\",\"D\"\"E\"\"F\",\"G,\"\"H\"\",I\"" + SystemInfo.getLineSeparator(), out.toString());
  }

}
