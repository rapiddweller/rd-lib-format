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

import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import static com.rapiddweller.format.csv.CSVTokenType.CELL;
import static com.rapiddweller.format.csv.CSVTokenType.EOF;
import static com.rapiddweller.format.csv.CSVTokenType.EOL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link CSVTokenizer}.
 * Created: 26.08.2006 17:51:14
 * @author Volker Bergmann
 */
public class CSVTokenizerTest {

  @Test
  public void testEmpty() {
    CSVTokenizer tokenizer = createTokenizer("");
    assertNextToken(tokenizer, EOF, null);
    assertNextToken(tokenizer, EOF, null);
  }

  @Test
  public void testAB() {
    CSVTokenizer tokenizer = createTokenizer("A\tv,B");
    assertNextToken(tokenizer, CELL, "A\tv");
    assertNextToken(tokenizer, CELL, "B");
    assertNextToken(tokenizer, EOF, null);
    assertNextToken(tokenizer, EOF, null);
  }

  @Test
  public void testEmptyAndNull() {
    CSVTokenizer tokenizer = createTokenizer("\"\",,A,,");
    assertNextToken(tokenizer, CELL, "");
    assertNextToken(tokenizer, CELL, null);
    assertNextToken(tokenizer, CELL, "A");
    assertNextToken(tokenizer, CELL, null);
    assertNextToken(tokenizer, CELL, null);
    assertNextToken(tokenizer, EOF, null);
    assertNextToken(tokenizer, EOF, null);
  }

  @Test
  public void testEmptyFirstCell() {
    CSVTokenizer tokenizer = createTokenizer(",,A");
    assertNextToken(tokenizer, CELL, null);
    assertNextToken(tokenizer, CELL, null);
    assertNextToken(tokenizer, CELL, "A");
    assertNextToken(tokenizer, EOF, null);
    assertNextToken(tokenizer, EOF, null);
  }

  @Test
  public void testABTab() {
    CSVTokenizer tokenizer = createTokenizer("A\tB", '\t');
    assertNextToken(tokenizer, CELL, "A");
    assertNextToken(tokenizer, CELL, "B");
    assertNextToken(tokenizer, EOF, null);
    assertNextToken(tokenizer, EOF, null);
  }

  @Test
  public void testABL() {
    CSVTokenizer tokenizer = createTokenizer("A,B\n");
    assertNextToken(tokenizer, CELL, "A");
    assertNextToken(tokenizer, CELL, "B");
    assertNextToken(tokenizer, EOL, null);
    assertNextToken(tokenizer, EOF, null);
    assertNextToken(tokenizer, EOF, null);
  }

  @Test
  public void testABLC() {
    CSVTokenizer tokenizer = createTokenizer("A,B\nC");
    assertNextToken(tokenizer, CELL, "A");
    assertNextToken(tokenizer, CELL, "B");
    assertNextToken(tokenizer, EOL, null);
    assertNextToken(tokenizer, CELL, "C");
    assertNextToken(tokenizer, EOF, null);
    assertNextToken(tokenizer, EOF, null);
  }

  @Test
  public void testABLCL() {
    CSVTokenizer tokenizer = createTokenizer("A,B\nC\n");
    assertNextToken(tokenizer, CELL, "A");
    assertNextToken(tokenizer, CELL, "B");
    assertNextToken(tokenizer, EOL, null);
    assertNextToken(tokenizer, CELL, "C");
    assertNextToken(tokenizer, EOL, null);
    assertNextToken(tokenizer, EOF, null);
    assertNextToken(tokenizer, EOF, null);
  }

  @Test
  public void testQuotes() {
    CSVTokenizer tokenizer = createTokenizer("\"A\",B\n\"C\"\n");
    assertNextToken(tokenizer, CELL, "A");
    assertNextToken(tokenizer, CELL, "B");
    assertNextToken(tokenizer, EOL, null);
    assertNextToken(tokenizer, CELL, "C");
    assertNextToken(tokenizer, EOL, null);
    assertNextToken(tokenizer, EOF, null);
    assertNextToken(tokenizer, EOF, null);
  }

  @Test
  public void testQuoteEscaping() {
    CSVTokenizer tokenizer = createTokenizer("\"A\"\"A\",\"\"\"B\"\" is B\"\n" +
        "\"C was \"\"C\"\"\",\"\"\"D\"\" is \"\"D\"\"\"\n");
    assertNextToken(tokenizer, CELL, "A\"A");
    assertNextToken(tokenizer, CELL, "\"B\" is B");
    assertNextToken(tokenizer, EOL, null);
    assertNextToken(tokenizer, CELL, "C was \"C\"");
    assertNextToken(tokenizer, CELL, "\"D\" is \"D\"");
    assertNextToken(tokenizer, EOL, null);
    assertNextToken(tokenizer, EOF, null);
    assertNextToken(tokenizer, EOF, null);
  }

  @Test
  public void testQuoteThenEmpty() {
    CSVTokenizer tokenizer = createTokenizer("\"A\";;X", ';');
    assertNextToken(tokenizer, CELL, "A");
    assertNextToken(tokenizer, CELL, null);
    assertNextToken(tokenizer, CELL, "X");
    assertNextToken(tokenizer, EOF, null);
  }

  @Test
  public void testLFInQuote() {
    CSVTokenizer tokenizer = createTokenizer("\"A\nB\"");
    assertNextToken(tokenizer, CELL, "A\nB");
    assertNextToken(tokenizer, EOF, null);
    assertNextToken(tokenizer, EOF, null);
  }

  @Test
  public void testFile() {
    CSVTokenizer tokenizer = new CSVTokenizer("file://com/rapiddweller/format/csv/names.csv", ',');
    assertNextToken(tokenizer, CELL, "Alice");
    assertNextToken(tokenizer, CELL, "Bob");
    assertNextToken(tokenizer, EOL, null);
    assertNextToken(tokenizer, CELL, "Charly");
    assertNextToken(tokenizer, EOL, null);
    assertNextToken(tokenizer, EOL, null);
    assertNextToken(tokenizer, CELL, "Dieter");
    assertNextToken(tokenizer, CELL, "Indiana\nJones");
    assertNextToken(tokenizer, EOL, null);
    assertNextToken(tokenizer, EOF, null);
    assertNextToken(tokenizer, EOF, null);
  }

  @Test
  public void testSkipLine() {
    // testing \r
    CSVTokenizer tokenizer = createTokenizer("1\r2");
    tokenizer.skipLine();
    assertNextToken(tokenizer, CELL, "2");
    assertNextToken(tokenizer, EOF, null);
    // testing \n
    tokenizer = createTokenizer("1\n2");
    tokenizer.skipLine();
    assertNextToken(tokenizer, CELL, "2");
    assertNextToken(tokenizer, EOF, null);
    // testing \n
    tokenizer = createTokenizer("1\n2");
    tokenizer.skipLine();
    assertNextToken(tokenizer, CELL, "2");
    assertNextToken(tokenizer, EOF, null);
  }

  @Test
  public void testLastTtype() {
    assertNull((new CSVTokenizer(Reader.nullReader())).lastTtype());
  }

  // helpers ---------------------------------------------------------------------------------------------------------

  private static CSVTokenizer createTokenizer(String content) {
    return createTokenizer(content, ',');
  }

  private static CSVTokenizer createTokenizer(String content, char separator) {
    StringReader reader = new StringReader(content);
    return new CSVTokenizer(reader, separator);
  }

  private static void assertNextToken(CSVTokenizer tokenizer, CSVTokenType tokenType, String cell) {
    CSVTokenType found = tokenizer.next();
    assertEquals(tokenType, found);
    assertEquals(tokenType, tokenizer.ttype);
    assertEquals(cell, tokenizer.cell);
  }

  @Test
  public void testNext() {
    CSVTokenizer csvTokenizer = new CSVTokenizer(Reader.nullReader());
    assertEquals(CSVTokenType.EOF, csvTokenizer.next());
    assertNull(csvTokenizer.cell);
    assertNull(csvTokenizer.lastType);
    assertEquals(CSVTokenType.EOF, csvTokenizer.ttype);
  }

  @Test
  public void testNext2() {
    CSVTokenizer csvTokenizer = new CSVTokenizer(new StringReader("S"));
    assertEquals(CSVTokenType.CELL, csvTokenizer.next());
    assertEquals("S", csvTokenizer.cell);
    assertNull(csvTokenizer.lastType);
    assertEquals(CSVTokenType.CELL, csvTokenizer.ttype);
  }

  @Test
  public void testNext3() {
    CSVTokenizer csvTokenizer = new CSVTokenizer("");
    assertEquals(CSVTokenType.CELL, csvTokenizer.next());
    assertEquals("com", csvTokenizer.cell);
    assertNull(csvTokenizer.lastType);
    assertEquals(CSVTokenType.CELL, csvTokenizer.ttype);
  }

}
