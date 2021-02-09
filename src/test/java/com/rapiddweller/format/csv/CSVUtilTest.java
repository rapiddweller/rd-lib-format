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
 *
 * @author Volker Bergmann
 * @since 0.6.2
 */
public class CSVUtilTest {

  /**
   * Test parse header 2.
   */
  @Test
  public void testParseHeader2() {
    assertThrows(ConfigurationError.class, () -> CSVUtil.parseHeader("string://", 'A', "UTF-8"));
  }

  /**
   * Test parse header 3.
   */
  @Test
  public void testParseHeader3() {
    assertEquals(1, CSVUtil.parseHeader("file:", 'A', "UTF-8").length);
  }

  /**
   * Test parse header 4.
   */
  @Test
  public void testParseHeader4() {
    assertThrows(RuntimeException.class, () -> CSVUtil.parseHeader("://", 'A', "UTF-8"));
  }

  /**
   * Test parse rows.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testParseRows() throws IOException {
    assertEquals(0, CSVUtil.parseRows("string://", 'A').length);
    assertEquals(2, CSVUtil.parseRows("file:", 'A').length);
    assertEquals(0, CSVUtil.parseRows("string://", 'A', "UTF-8").length);
    assertEquals(2, CSVUtil.parseRows("file:", 'A', "UTF-8").length);
  }

  /**
   * Test parse csv row.
   */
  @Test
  public void testParseCSVRow() {
    assertEquals(1, CSVUtil.parseCSVRow("Text").length);
  }

  /**
   * Test render cell.
   */
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
    assertEquals("Text", CSVUtil.renderCell("Text", 'A'));
    assertEquals("", CSVUtil.renderCell(null, 'A'));
    assertEquals("\"java.lang.String\"", CSVUtil.renderCell("java.lang.String", 'a'));
    assertEquals("\"\"\"\"", CSVUtil.renderCell("\"", 'a'));
  }

  /**
   * Test format header with line feed.
   */
  @Test
  public void testFormatHeaderWithLineFeed() {
    assertEquals("Property Names\n", CSVUtil.formatHeaderWithLineFeed('A', "Property Names"));
  }

  /**
   * Test write row.
   *
   * @throws Exception the exception
   */
  @Test
  public void testWriteRow() throws Exception {
    StringWriter out = new StringWriter();
    CSVUtil.writeRow(out, ',', null, "A", "B,C", "D\"E\"F", "G,\"H\",I");
    assertEquals(",A,\"B,C\",\"D\"\"E\"\"F\",\"G,\"\"H\"\",I\"" + SystemInfo.getLineSeparator(), out.toString());
  }

  /**
   * Test write row 2.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testWriteRow2() throws IOException {
    // TODO: This test is incomplete.
    //   Reason: No meaningful assertions found.
    //   To help Diffblue Cover to find assertions, please add getters to the
    //   class under test that return fields written by the method under test.
    //   See https://diff.blue/R004

    CSVUtil.writeRow(Writer.nullWriter(), 'A', "Cells");
  }

  /**
   * Test write row 3.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testWriteRow3() throws IOException {
    // TODO: This test is incomplete.
    //   Reason: No meaningful assertions found.
    //   To help Diffblue Cover to find assertions, please add getters to the
    //   class under test that return fields written by the method under test.
    //   See https://diff.blue/R004

    CSVUtil.writeRow(new StringWriter(), 'A', "Cells");
  }

  /**
   * Test write row 4.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testWriteRow4() throws IOException {
    // TODO: This test is incomplete.
    //   Reason: No meaningful assertions found.
    //   To help Diffblue Cover to find assertions, please add getters to the
    //   class under test that return fields written by the method under test.
    //   See https://diff.blue/R004

    CSVUtil.writeRow(Writer.nullWriter(), 'A', "line.separator", "line.separator");
  }

  /**
   * Test write row 5.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testWriteRow5() throws IOException {
    // TODO: This test is incomplete.
    //   Reason: No meaningful assertions found.
    //   To help Diffblue Cover to find assertions, please add getters to the
    //   class under test that return fields written by the method under test.
    //   See https://diff.blue/R004

    CSVUtil.writeRow(Writer.nullWriter(), 'a', "line.separator", "line.separator");
  }

  /**
   * Test write row 6.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testWriteRow6() throws IOException {
    // TODO: This test is incomplete.
    //   Reason: No meaningful assertions found.
    //   To help Diffblue Cover to find assertions, please add getters to the
    //   class under test that return fields written by the method under test.
    //   See https://diff.blue/R004

    CSVUtil.writeRow(Writer.nullWriter(), 'a', "\"", "line.separator");
  }
}
