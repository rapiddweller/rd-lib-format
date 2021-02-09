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

import com.rapiddweller.format.DataContainer;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link CSVLineIterator}.
 * Created: 29.09.2006 16:15:23
 *
 * @author Volker Bergmann
 * @since 0.1
 */
public class CSVLineIteratorTest {

  /**
   * Test constructor.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor() throws IOException {
    assertEquals(0, (new CSVLineIterator(Reader.nullReader(), 'A')).lineCount());
    assertEquals(0, (new CSVLineIterator(Reader.nullReader(), 'A', true)).lineCount());
  }


  /**
   * Test constructor 11.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor11() throws IOException {
    CSVLineIterator actualCsvLineIterator = new CSVLineIterator("string://", 'A', "UTF-8");
    assertEquals("CSVLineIterator[string://]", actualCsvLineIterator.toString());
    assertEquals(0, actualCsvLineIterator.lineCount());
  }

  /**
   * Test constructor 12.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor12() throws IOException {
    CSVLineIterator actualCsvLineIterator = new CSVLineIterator("file:", 'A', "UTF-8");
    assertEquals(1, actualCsvLineIterator.getHeaders().length);
    assertEquals("CSVLineIterator[file:]", actualCsvLineIterator.toString());
    assertEquals(0, actualCsvLineIterator.lineCount());
  }

  /**
   * Test constructor 14.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor14() throws IOException {
    CSVLineIterator actualCsvLineIterator = new CSVLineIterator("string://", 'A', true);
    assertEquals("CSVLineIterator[string://]", actualCsvLineIterator.toString());
    assertEquals(0, actualCsvLineIterator.lineCount());
  }

  /**
   * Test constructor 15.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor15() throws IOException {
    CSVLineIterator actualCsvLineIterator = new CSVLineIterator("file:", 'A', true);
    assertEquals(1, actualCsvLineIterator.getHeaders().length);
    assertEquals("CSVLineIterator[file:]", actualCsvLineIterator.toString());
    assertEquals(0, actualCsvLineIterator.lineCount());
  }

  /**
   * Test constructor 16.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor16() throws IOException {
    CSVLineIterator actualCsvLineIterator = new CSVLineIterator("file:", 'A', false);
    assertEquals(1, actualCsvLineIterator.getHeaders().length);
    assertEquals("CSVLineIterator[file:]", actualCsvLineIterator.toString());
    assertEquals(0, actualCsvLineIterator.lineCount());
  }


  /**
   * Test constructor 18.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor18() throws IOException {
    CSVLineIterator actualCsvLineIterator = new CSVLineIterator("string://", 'A', true, "UTF-8");
    assertEquals("CSVLineIterator[string://]", actualCsvLineIterator.toString());
    assertEquals(0, actualCsvLineIterator.lineCount());
  }

  /**
   * Test constructor 19.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor19() throws IOException {
    CSVLineIterator actualCsvLineIterator = new CSVLineIterator("file:", 'A', true, "UTF-8");
    assertEquals(1, actualCsvLineIterator.getHeaders().length);
    assertEquals("CSVLineIterator[file:]", actualCsvLineIterator.toString());
    assertEquals(0, actualCsvLineIterator.lineCount());
  }

  /**
   * Test constructor 2.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor2() throws IOException {
    CSVLineIterator actualCsvLineIterator = new CSVLineIterator(new StringReader("S"), 'A');
    assertEquals(1, actualCsvLineIterator.getHeaders().length);
    assertEquals(0, actualCsvLineIterator.lineCount());
  }

  /**
   * Test constructor 20.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor20() throws IOException {
    CSVLineIterator actualCsvLineIterator = new CSVLineIterator("file:", 'A', false, "UTF-8");
    assertEquals(1, actualCsvLineIterator.getHeaders().length);
    assertEquals("CSVLineIterator[file:]", actualCsvLineIterator.toString());
    assertEquals(0, actualCsvLineIterator.lineCount());
  }

  /**
   * Test constructor 3.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor3() throws IOException {
    CSVLineIterator actualCsvLineIterator = new CSVLineIterator(new StringReader("S"), 'A', true);
    assertEquals(1, actualCsvLineIterator.getHeaders().length);
    assertEquals(0, actualCsvLineIterator.lineCount());
  }


  /**
   * Test constructor 5.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor5() throws IOException {
    CSVLineIterator actualCsvLineIterator = new CSVLineIterator("string://");
    assertEquals("CSVLineIterator[string://]", actualCsvLineIterator.toString());
    assertEquals(0, actualCsvLineIterator.lineCount());
  }

  /**
   * Test constructor 6.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor6() throws IOException {
    CSVLineIterator actualCsvLineIterator = new CSVLineIterator("file:");
    assertEquals(1, actualCsvLineIterator.getHeaders().length);
    assertEquals("CSVLineIterator[file:]", actualCsvLineIterator.toString());
    assertEquals(0, actualCsvLineIterator.lineCount());
  }


  /**
   * Test constructor 8.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor8() throws IOException {
    CSVLineIterator actualCsvLineIterator = new CSVLineIterator("string://", 'A');
    assertEquals("CSVLineIterator[string://]", actualCsvLineIterator.toString());
    assertEquals(0, actualCsvLineIterator.lineCount());
  }

  /**
   * Test constructor 9.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor9() throws IOException {
    CSVLineIterator actualCsvLineIterator = new CSVLineIterator("file:", 'A');
    assertEquals(1, actualCsvLineIterator.getHeaders().length);
    assertEquals("CSVLineIterator[file:]", actualCsvLineIterator.toString());
    assertEquals(0, actualCsvLineIterator.lineCount());
  }

  /**
   * Test parse headers.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testParseHeaders() throws IOException {
    CSVLineIterator csvLineIterator = new CSVLineIterator("");
    csvLineIterator.parseHeaders();
    assertEquals(1, csvLineIterator.getHeaders().length);
  }

  /**
   * Test parse headers 2.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testParseHeaders2() throws IOException {
    CSVLineIterator csvLineIterator = new CSVLineIterator(Reader.nullReader(), 'A');
    csvLineIterator.parseHeaders();
    assertEquals(0, csvLineIterator.getHeaders().length);
  }

  /**
   * Test parse headers 3.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testParseHeaders3() throws IOException {
    CSVLineIterator csvLineIterator = new CSVLineIterator("", 'A', true);
    csvLineIterator.parseHeaders();
    assertEquals(1, csvLineIterator.getHeaders().length);
  }

  /**
   * Test set headers.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testSetHeaders() throws IOException {
    CSVLineIterator csvLineIterator = new CSVLineIterator("");
    String[] stringArray = new String[] {"foo", "foo", "foo"};
    csvLineIterator.setHeaders(stringArray);
    assertSame(stringArray, csvLineIterator.getHeaders());
  }

  /**
   * Test set headers 2.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testSetHeaders2() throws IOException {
    CSVLineIterator csvLineIterator = new CSVLineIterator("");
    csvLineIterator.setHeaders(null);
    assertEquals(0, csvLineIterator.getHeaders().length);
  }

  /**
   * Test next.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNext() throws IOException {
    CSVLineIterator csvLineIterator = new CSVLineIterator("");
    DataContainer<String[]> dataContainer = new DataContainer<String[]>();
    DataContainer<String[]> actualNextResult = csvLineIterator.next(dataContainer);
    assertSame(dataContainer, actualNextResult);
    assertEquals(1, actualNextResult.getData().length);
    assertEquals(1, csvLineIterator.lineCount());
  }

  /**
   * Test next 2.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNext2() throws IOException {
    CSVLineIterator csvLineIterator = new CSVLineIterator(Reader.nullReader(), 'A');
    assertNull(csvLineIterator.next(new DataContainer<String[]>()));
  }

  /**
   * Test next 3.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNext3() throws IOException {
    assertNull((new CSVLineIterator(Reader.nullReader(), 'A')).next(null));
  }

  /**
   * Test next 4.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNext4() throws IOException {
    CSVLineIterator csvLineIterator = new CSVLineIterator(new StringReader("S"), 'A');
    DataContainer<String[]> dataContainer = new DataContainer<String[]>();
    DataContainer<String[]> actualNextResult = csvLineIterator.next(dataContainer);
    assertSame(dataContainer, actualNextResult);
    assertEquals(1, actualNextResult.getData().length);
  }

  /**
   * Test next 5.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNext5() throws IOException {
    CSVLineIterator csvLineIterator = new CSVLineIterator("", 'A', true);
    DataContainer<String[]> dataContainer = new DataContainer<String[]>();
    DataContainer<String[]> actualNextResult = csvLineIterator.next(dataContainer);
    assertSame(dataContainer, actualNextResult);
    assertEquals(1, actualNextResult.getData().length);
    assertEquals(1, csvLineIterator.lineCount());
  }

  /**
   * Test cells by headers.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testCellsByHeaders() throws IOException {
    assertEquals(3, (new CSVLineIterator("")).cellsByHeaders(new String[] {"foo", "foo", "foo"},
        new String[] {"foo", "foo", "foo"}).length);
  }

  /**
   * Test cell by header.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testCellByHeader() throws IOException {
    assertNull((new CSVLineIterator("")).cellByHeader("Header", new String[] {"foo", "foo", "foo"}));
  }

  /**
   * Test close.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testClose() throws IOException {
    // TODO: This test is incomplete.
    //   Reason: No meaningful assertions found.
    //   To help Diffblue Cover to find assertions, please add getters to the
    //   class under test that return fields written by the method under test.
    //   See https://diff.blue/R004

    (new CSVLineIterator("")).close();
  }

  /**
   * Test close 2.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testClose2() throws IOException {
    // TODO: This test is incomplete.
    //   Reason: No meaningful assertions found.
    //   To help Diffblue Cover to find assertions, please add getters to the
    //   class under test that return fields written by the method under test.
    //   See https://diff.blue/R004

    (new CSVLineIterator(Reader.nullReader(), 'A')).close();
  }

  /**
   * Test line count.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testLineCount() throws IOException {
    assertEquals(0, (new CSVLineIterator("")).lineCount());
  }

  /**
   * Test process.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testProcess() throws IOException {
    // TODO: This test is incomplete.
    //   Reason: No meaningful assertions found.
    //   To help Diffblue Cover to find assertions, please add getters to the
    //   class under test that return fields written by the method under test.
    //   See https://diff.blue/R004

    CSVLineIterator.process(Reader.nullReader(), 'A', true, null);
  }


  /**
   * Test process 3.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testProcess3() throws IOException {
    // TODO: This test is incomplete.
    //   Reason: No meaningful assertions found.
    //   To help Diffblue Cover to find assertions, please add getters to the
    //   class under test that return fields written by the method under test.
    //   See https://diff.blue/R004

    CSVLineIterator.process("string://", 'A', "UTF-8", true, null);
  }


  /**
   * Test parse.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testParse() throws IOException {
    assertEquals(0, CSVLineIterator.parse(Reader.nullReader(), 'A', true).length);
    assertEquals(1, CSVLineIterator.parse(new StringReader("S"), 'A', true).length);
    assertEquals(0, CSVLineIterator.parse("string://", 'A', "UTF-8", true).length);
    assertEquals(2, CSVLineIterator.parse("file:", 'A', "UTF-8", true).length);
    assertEquals(1, CSVLineIterator.parse("file:", '\n', "UTF-8", true).length);
    assertEquals(2, CSVLineIterator.parse("file:", 'A', "UTF-8", false).length);
  }


  /**
   * Test to string.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testToString() throws IOException {
    assertEquals("CSVLineIterator[]", (new CSVLineIterator("")).toString());
  }

  /**
   * Test ignoring empty lines.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testIgnoringEmptyLines() throws IOException {
    CSVLineIterator iterator = new CSVLineIterator("file://com/rapiddweller/format/csv/names.csv", ',', true);
    DataContainer<String[]> container = new DataContainer<String[]>();
    assertArrayEquals(new String[] {"Alice", "Bob"}, iterator.next(container).getData());
    assertArrayEquals(new String[] {"Charly"}, iterator.next(container).getData());
    assertArrayEquals(new String[] {"Dieter", "Indiana\nJones"}, iterator.next(container).getData());
    assertNull(iterator.next(container));
    iterator.close();
  }

  /**
   * Test including empty lines.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testIncludingEmptyLines() throws IOException {
    CSVLineIterator iterator = new CSVLineIterator("file://com/rapiddweller/format/csv/names.csv", ',', false);
    DataContainer<String[]> container = new DataContainer<String[]>();
    assertArrayEquals(new String[] {"Alice", "Bob"}, iterator.next(container).getData());
    assertArrayEquals(new String[] {"Charly"}, iterator.next(container).getData());
    assertArrayEquals(new String[] {}, iterator.next(container).getData());
    assertArrayEquals(new String[] {"Dieter", "Indiana\nJones"}, iterator.next(container).getData());
    assertNull(iterator.next(container));
    iterator.close();
  }

  /**
   * Test empty file.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testEmptyFile() throws IOException {
    CSVLineIterator iterator = new CSVLineIterator("string://", ',', false);
    DataContainer<String[]> container = new DataContainer<String[]>();
    assertNull(iterator.next(container));
    iterator.close();
  }

  /**
   * Test three empty lines.
   *
   * @throws IOException the io exception
   */
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

  /**
   * Test each line ending with cr lf.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testEachLineEndingWithCrLf() throws IOException {
    CSVLineIterator iterator = new CSVLineIterator("string://DATA\n\nDATA2\n", ',', false);
    DataContainer<String[]> container = new DataContainer<String[]>();
    assertTrue(Arrays.equals(new String[] {"DATA"}, iterator.next(container).getData()));
    assertTrue(Arrays.equals(new String[0], iterator.next(container).getData()));
    assertTrue(Arrays.equals(new String[] {"DATA2"}, iterator.next(container).getData()));
    assertNull(iterator.next(container));
    iterator.close();
  }

  /**
   * Test last line without cr lf.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testLastLineWithoutCrLf() throws IOException {
    CSVLineIterator iterator = new CSVLineIterator("string://DATA\n\nDATA2", ',', false);
    DataContainer<String[]> container = new DataContainer<String[]>();
    assertTrue(Arrays.equals(new String[] {"DATA"}, iterator.next(container).getData()));
    assertTrue(Arrays.equals(new String[0], iterator.next(container).getData()));
    assertTrue(Arrays.equals(new String[] {"DATA2"}, iterator.next(container).getData()));
    assertNull(iterator.next(container));
    iterator.close();
  }

  /**
   * Test empty row at beginning.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testEmptyRowAtBeginning() throws IOException {
    CSVLineIterator iterator = new CSVLineIterator("string://\nDATA\nDATA2\n", ',', false);
    DataContainer<String[]> container = new DataContainer<String[]>();
    assertTrue(Arrays.equals(new String[0], iterator.next(container).getData()));
    assertTrue(Arrays.equals(new String[] {"DATA"}, iterator.next(container).getData()));
    assertTrue(Arrays.equals(new String[] {"DATA2"}, iterator.next(container).getData()));
    assertNull(iterator.next(container));
    iterator.close();
  }

  /**
   * Test empty row between.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testEmptyRowBetween() throws IOException {
    CSVLineIterator iterator = new CSVLineIterator("string://DATA\n\nDATA2\n", ',', false);
    DataContainer<String[]> container = new DataContainer<String[]>();
    assertTrue(Arrays.equals(new String[] {"DATA"}, iterator.next(container).getData()));
    assertTrue(Arrays.equals(new String[0], iterator.next(container).getData()));
    assertTrue(Arrays.equals(new String[] {"DATA2"}, iterator.next(container).getData()));
    assertNull(iterator.next(container));
    iterator.close();
  }

  /**
   * Test empty row at end.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testEmptyRowAtEnd() throws IOException {
    CSVLineIterator iterator = new CSVLineIterator("string://DATA\nDATA2\n\n", ',', false);
    DataContainer<String[]> container = new DataContainer<String[]>();
    assertTrue(Arrays.equals(new String[] {"DATA"}, iterator.next(container).getData()));
    assertTrue(Arrays.equals(new String[] {"DATA2"}, iterator.next(container).getData()));
    assertTrue(Arrays.equals(new String[0], iterator.next(container).getData()));
    assertNull(iterator.next(container));
    iterator.close();
  }

  /**
   * Test empty cells.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testEmptyCells() throws IOException {
    CSVLineIterator iterator = new CSVLineIterator("string://name,\"\",,x\n,\"\",");
    DataContainer<String[]> container = new DataContainer<String[]>();
    assertTrue(Arrays.equals(new String[] {"name", "", null, "x"}, iterator.next(container).getData()));
    assertTrue(Arrays.equals(new String[] {null, "", null}, iterator.next(container).getData()));
    assertNull(iterator.next(container));
    iterator.close();
  }

}
