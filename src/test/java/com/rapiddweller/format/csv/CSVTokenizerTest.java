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

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static com.rapiddweller.format.csv.CSVTokenType.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link CSVTokenizer}.
 * Created: 26.08.2006 17:51:14
 *
 * @author Volker Bergmann
 */
public class CSVTokenizerTest {

    @Test
    public void testConstructor() throws IOException {
        assertEquals(1, (new CSVTokenizer(Reader.nullReader())).line);
        assertEquals(1, (new CSVTokenizer(Reader.nullReader(), 'A')).line);
        assertEquals(1, (new CSVTokenizer(Reader.nullReader(), '\u0000')).line);
        assertEquals(1, (new CSVTokenizer("string://")).line);
        assertEquals(1, (new CSVTokenizer("string://", 'A')).line);
        assertEquals(1, (new CSVTokenizer("string://", 'A', "UTF-8")).line);
    }

    @Test
    public void testConstructor2() throws IOException {
        Reader nullReaderResult = Reader.nullReader();
        nullReaderResult.skip(0L);
        assertEquals(1, (new CSVTokenizer(nullReaderResult)).line);
    }


    @Test
    public void testEmpty() throws IOException {
        CSVTokenizer tokenizer = createTokenizer("");
        assertNextToken(tokenizer, EOF, null);
        assertNextToken(tokenizer, EOF, null);
    }

    @Test
    public void testA() throws IOException {
        CSVTokenizer tokenizer = createTokenizer("A");
        assertNextToken(tokenizer, CELL, "A");
        assertNextToken(tokenizer, EOF, null);
        assertNextToken(tokenizer, EOF, null);
    }

    @Test
    public void testAB() throws IOException {
        CSVTokenizer tokenizer = createTokenizer("A\tv,B");
        assertNextToken(tokenizer, CELL, "A\tv");
        assertNextToken(tokenizer, CELL, "B");
        assertNextToken(tokenizer, EOF, null);
        assertNextToken(tokenizer, EOF, null);
    }

    @Test
    public void testEmptyAndNull() throws IOException {
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
    public void testEmptyFirstCell() throws IOException {
        CSVTokenizer tokenizer = createTokenizer(",,A");
        assertNextToken(tokenizer, CELL, null);
        assertNextToken(tokenizer, CELL, null);
        assertNextToken(tokenizer, CELL, "A");
        assertNextToken(tokenizer, EOF, null);
        assertNextToken(tokenizer, EOF, null);
    }

    @Test
    public void testABTab() throws IOException {
        CSVTokenizer tokenizer = createTokenizer("A\tB", '\t');
        assertNextToken(tokenizer, CELL, "A");
        assertNextToken(tokenizer, CELL, "B");
        assertNextToken(tokenizer, EOF, null);
        assertNextToken(tokenizer, EOF, null);
    }

    @Test
    public void testABL() throws IOException {
        CSVTokenizer tokenizer = createTokenizer("A,B\n");
        assertNextToken(tokenizer, CELL, "A");
        assertNextToken(tokenizer, CELL, "B");
        assertNextToken(tokenizer, EOL, null);
        assertNextToken(tokenizer, EOF, null);
        assertNextToken(tokenizer, EOF, null);
    }

    @Test
    public void testABLC() throws IOException {
        CSVTokenizer tokenizer = createTokenizer("A,B\nC");
        assertNextToken(tokenizer, CELL, "A");
        assertNextToken(tokenizer, CELL, "B");
        assertNextToken(tokenizer, EOL, null);
        assertNextToken(tokenizer, CELL, "C");
        assertNextToken(tokenizer, EOF, null);
        assertNextToken(tokenizer, EOF, null);
    }

    @Test
    public void testABLCL() throws IOException {
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
    public void testQuotes() throws IOException {
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
    public void testQuoteEscaping() throws IOException {
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
    public void testQuoteThenEmpty() throws IOException {
        CSVTokenizer tokenizer = createTokenizer("\"A\";;X", ';');
        assertNextToken(tokenizer, CELL, "A");
        assertNextToken(tokenizer, CELL, null);
        assertNextToken(tokenizer, CELL, "X");
        assertNextToken(tokenizer, EOF, null);
    }

    @Test
    public void testLFInQuote() throws IOException {
        CSVTokenizer tokenizer = createTokenizer("\"A\nB\"");
        assertNextToken(tokenizer, CELL, "A\nB");
        assertNextToken(tokenizer, EOF, null);
        assertNextToken(tokenizer, EOF, null);
    }

    @Test
    public void testFile() throws IOException {
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
    public void testSkipLine() throws IOException {
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
    public void testSkipLine2() throws IOException {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        (new CSVTokenizer(Reader.nullReader())).skipLine();
    }

    @Test
    public void testSkipLine3() throws IOException {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        (new CSVTokenizer(new StringReader("S"))).skipLine();
    }

    @Test
    public void testSkipLine4() throws IOException {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        (new CSVTokenizer("")).skipLine();
    }

    @Test
    public void testClose() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        (new CSVTokenizer(Reader.nullReader())).close();
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

    private static void assertNextToken(CSVTokenizer tokenizer, CSVTokenType tokenType, String cell) throws IOException {
        CSVTokenType found = tokenizer.next();
        assertEquals(tokenType, found);
        assertEquals(tokenType, tokenizer.ttype);
        assertEquals(cell, tokenizer.cell);
    }

    @Test
    public void testNext() throws IOException {
        CSVTokenizer csvTokenizer = new CSVTokenizer(Reader.nullReader());
        assertEquals(CSVTokenType.EOF, csvTokenizer.next());
        assertNull(csvTokenizer.cell);
        assertNull(csvTokenizer.lastType);
        assertEquals(CSVTokenType.EOF, csvTokenizer.ttype);
    }

    @Test
    public void testNext2() throws IOException {
        CSVTokenizer csvTokenizer = new CSVTokenizer(new StringReader("S"));
        assertEquals(CSVTokenType.CELL, csvTokenizer.next());
        assertEquals("S", csvTokenizer.cell);
        assertNull(csvTokenizer.lastType);
        assertEquals(CSVTokenType.CELL, csvTokenizer.ttype);
    }

    @Test
    public void testNext3() throws IOException {
        CSVTokenizer csvTokenizer = new CSVTokenizer("");
        assertEquals(CSVTokenType.CELL, csvTokenizer.next());
        assertEquals("com", csvTokenizer.cell);
        assertNull(csvTokenizer.lastType);
        assertEquals(CSVTokenType.CELL, csvTokenizer.ttype);
    }
}
