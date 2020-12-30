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

import com.rapiddweller.format.csv.CSVTokenType;
import com.rapiddweller.format.csv.CSVTokenizer;
import org.junit.Test;

import java.io.StringReader;
import java.io.IOException;

import static com.rapiddweller.format.csv.CSVTokenType.*;
import static org.junit.Assert.*;

/**
 * Tests the {@link CSVTokenizer}.
 * Created: 26.08.2006 17:51:14
 * @author Volker Bergmann
 */
public class CSVTokenizerTest {

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
        assertNextToken(tokenizer,  EOF, null);
        assertNextToken(tokenizer,  EOF, null);
    }

	@Test
    public void testEmptyFirstCell() throws IOException {
    	CSVTokenizer tokenizer = createTokenizer(",,A");
        assertNextToken(tokenizer, CELL, null);
        assertNextToken(tokenizer, CELL, null);
        assertNextToken(tokenizer, CELL, "A");
        assertNextToken(tokenizer,  EOF, null);
        assertNextToken(tokenizer,  EOF, null);
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
    	CSVTokenizer tokenizer = createTokenizer("A,B\r\n");
        assertNextToken(tokenizer, CELL, "A");
        assertNextToken(tokenizer, CELL, "B");
        assertNextToken(tokenizer, EOL, null);
        assertNextToken(tokenizer, EOF, null);
        assertNextToken(tokenizer, EOF, null);
    }

	@Test
    public void testABLC() throws IOException {
    	CSVTokenizer tokenizer = createTokenizer("A,B\r\nC");
        assertNextToken(tokenizer, CELL, "A");
        assertNextToken(tokenizer, CELL, "B");
        assertNextToken(tokenizer, EOL, null);
        assertNextToken(tokenizer, CELL, "C");
        assertNextToken(tokenizer, EOF, null);
        assertNextToken(tokenizer, EOF, null);
    }

	@Test
    public void testABLCL() throws IOException {
    	CSVTokenizer tokenizer = createTokenizer("A,B\r\nC\r\n");
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
    	CSVTokenizer tokenizer = createTokenizer("\"A\",B\r\n\"C\"\r\n");
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
    	CSVTokenizer tokenizer = createTokenizer("\"A\"\"A\",\"\"\"B\"\" is B\"\r\n" +
                "\"C was \"\"C\"\"\",\"\"\"D\"\" is \"\"D\"\"\"\r\n");
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
        CSVTokenizer tokenizer = createTokenizer("\"A\r\nB\"");
        assertNextToken(tokenizer, CELL, "A\r\nB");
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
        assertNextToken(tokenizer, CELL, "Indiana\r\nJones");
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
    	tokenizer = createTokenizer("1\r\n2");
        tokenizer.skipLine();
        assertNextToken(tokenizer, CELL, "2");
        assertNextToken(tokenizer, EOF, null);
    	// testing \r\n
    	tokenizer = createTokenizer("1\n2");
        tokenizer.skipLine();
        assertNextToken(tokenizer, CELL, "2");
        assertNextToken(tokenizer, EOF, null);
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
}
