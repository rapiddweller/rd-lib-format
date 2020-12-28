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
package com.rapiddweller.formats.html;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.*;
import java.text.ParseException;
import java.util.Map;
import java.util.HashMap;

import com.rapiddweller.commons.SystemInfo;
import com.rapiddweller.formats.html.parser.DefaultHTMLTokenizer;
import com.rapiddweller.formats.html.parser.HTMLTokenizer;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Tests the {@link DefaultHTMLTokenizer}.
 * Created: 25.01.2007 13:29:54
 * @since 0.1
 * @author Volker Bergmann
 */
public class DefaultHTMLTokenizerTest {

    private final Logger logger = LogManager.getLogger(DefaultHTMLTokenizerTest.class);

    private static final TestSetup DOCUMENT_TYPE = new TestSetup(
            "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">",
            new HT(HTMLTokenizer.DOCUMENT_TYPE, "DOCTYPE"));

    private static final TestSetup TEXT = new TestSetup(
            "1st. some Text!",
            new HT(HTMLTokenizer.TEXT, "1st. some Text!"));

    private static final TestSetup START_TAG = new TestSetup(
            "<x>",
            new HT(HTMLTokenizer.START_TAG, "x"));

    private static final TestSetup END_TAG = new TestSetup(
            "</x>",
            new HT(HTMLTokenizer.END_TAG, "x"));

    private static final TestSetup CLOSED_TAG = new TestSetup(
            "<x y z='0'/>",
            new HT(HTMLTokenizer.CLOSED_TAG, "x", "y", null, "z", "0"));

    private static final TestSetup COMMENT = new TestSetup(
            "<!--ab-c<s>d--ef</s>ghi-->",
            new HT(HTMLTokenizer.COMMENT, "<!--ab-c<s>d--ef</s>ghi-->"));

    private static final TestSetup COMMENTS = new TestSetup(
            "<!----##--->X<!-------##---->",
            new HT(HTMLTokenizer.COMMENT, "<!----##--->"),
            new HT(HTMLTokenizer.TEXT, "X"),
            new HT(HTMLTokenizer.COMMENT, "<!-------##---->")
            );

    private static final TestSetup PROCESSING_INSTRUCTION = new TestSetup(
            "<?xml version = \"1.0\" ?>",
            new HT(HTMLTokenizer.PROCESSING_INSTRUCTION, "xml", "version", "1.0"));

    private static final TestSetup SPACED_CLOSED_TAG = new TestSetup(
            "<x y z = '0' />",
            new HT(HTMLTokenizer.CLOSED_TAG, "x", "y", null, "z", "0"));

    private static final TestSetup TEXT_TAG = new TestSetup(
            "<b>Bold!!!</b>",
            new HT(HTMLTokenizer.START_TAG, "b"),
            new HT(HTMLTokenizer.TEXT, "Bold!!!"),
            new HT(HTMLTokenizer.END_TAG, "b")
    );
    private static final TestSetup TAG_TEXT_COMMENT = new TestSetup(
            "<x><y/>z<!--ab-c<s>d--ef</s>ghi-->z</x>",
            new HT(HTMLTokenizer.START_TAG, "x"),
            new HT(HTMLTokenizer.CLOSED_TAG, "y"),
            new HT(HTMLTokenizer.TEXT, "z"),
            new HT(HTMLTokenizer.COMMENT, "<!--ab-c<s>d--ef</s>ghi-->"),
            new HT(HTMLTokenizer.TEXT, "z"),
            new HT(HTMLTokenizer.END_TAG, "x")
    );

    private static final TestSetup SCRIPT =
        new TestSetup("<a href=javascript:addAbo(53,true,true,true,true,true,true,'GERMAN')>",
            new HT(HTMLTokenizer.START_TAG, "a", "href", "javascript:addAbo(53,true,true,true,true,true,true,'GERMAN')")
        );

    private static final TestSetup COMPARATION_TEXT =
        new TestSetup("a < 3 || a > 5",
            new HT(HTMLTokenizer.TEXT, "a < 3 || a > 5")
        );

    private static final TestSetup TAG_LIKE_TEXT =
        new TestSetup("< a >",
            new HT(HTMLTokenizer.TEXT, "< a >")
        );

    private static final String SEP = SystemInfo.getLineSeparator();

    private static final TestSetup MIXED = new TestSetup(
            "<html>" + SEP +
            "\t<?XXX level=\"3\"?>" + SEP +
            "\t<!-- some comment -->" + SEP +
            "</html>",
            new HT(HTMLTokenizer.START_TAG, "html"),
            new HT(HTMLTokenizer.TEXT, SEP + '\t'),
            new HT(HTMLTokenizer.PROCESSING_INSTRUCTION, "XXX", "level", "3"),
            new HT(HTMLTokenizer.TEXT, SEP + '\t'),
            new HT(HTMLTokenizer.COMMENT, "<!-- some comment -->"),
            new HT(HTMLTokenizer.TEXT, SEP),
            new HT(HTMLTokenizer.END_TAG, "html")
    );


    private static final TestSetup[] TESTS = {
        DOCUMENT_TYPE,
        TEXT,
        START_TAG,
        END_TAG,
        CLOSED_TAG,
        COMMENT,
        COMMENTS,
        PROCESSING_INSTRUCTION,

        TAG_LIKE_TEXT,
        COMPARATION_TEXT,
        SPACED_CLOSED_TAG,
        SCRIPT,
        TEXT_TAG,
        TAG_TEXT_COMMENT,
        MIXED
    };

    // testUnprefixed methods ----------------------------------------------------------------------------------------------------

    @Test
    public void testText() throws IOException, ParseException {
        for (TestSetup testString : TESTS)
            checkText(testString);
    }

    @Test
    public void testTokens() throws IOException, ParseException {
        for (TestSetup test : TESTS)
            checkTokens(test);
    }
    
/*
    public void testCommentText() throws IOException, ParseException {
        Reader source = new StringReader(COMMENT_HTML);
        HTMLReader reader = new HTMLReader(source);
        assertParts(reader,
                new HTMLStartTag("x"),
                new HTMLClosedTag("y"),
                new HTMLText("z"),
                new HTMLComment("ab-c<s>d--ef</s>ghi"),
                new HTMLText("z"),
                new HTMLEndTag("x")
        );
        reader.close();
    }
*/
    // private helpers -------------------------------------------------------------------------------------------------

    public void checkText(TestSetup test) throws IOException, ParseException {
        Reader in = new StringReader(test.html);
        Writer out = new StringWriter();
        HTMLTokenizer tokenizer = new DefaultHTMLTokenizer(in);
        while (tokenizer.nextToken() != HTMLTokenizer.END)
            out.write(tokenizer.text());
        out.close();
        in.close();
        assertEquals(test.html, out.toString());
    }

    public void checkTokens(TestSetup test) throws IOException, ParseException {
        logger.debug("checking: " + test.html);
        Reader in = new StringReader(test.html);
        Writer out = new StringWriter();
        HTMLTokenizer tokenizer = new DefaultHTMLTokenizer(in);
        for (HT token : test.tokens) {
            tokenizer.nextToken();
            assertEquals(token.type, tokenizer.tokenType());
            if (token.type == HTMLTokenizer.TEXT || token.type == HTMLTokenizer.COMMENT)
                assertEquals(token.name, tokenizer.text());
            else
                assertEquals(token.name, tokenizer.name());
            assertEquals(token.attributes, tokenizer.attributes());
        }
        assertEquals("Fond more tokens than expected for: " + test.html, HTMLTokenizer.END, tokenizer.nextToken());
        out.close();
        in.close();
    }
/*
    private void assertParts(HTMLReader reader, HTMLPart ... parts) throws IOException, ParseException {
        for (HTMLPart part : parts) {
            assertNotNull(part);
            assertEquals(part, reader.nextPart());
        }
    }
*/

    private static class TestSetup {
        public String html;
        public HT[] tokens;

        public TestSetup(String html, HT ... tokens) {
            this.html = html;
            this.tokens = tokens;
        }
    }

    private static class HT {

        public int type;
        public String name;
        public Map<String, String> attributes;
//        public String[] attributes;

        public HT(int type, String name, String ... attributes) {
            this.type = type;
            this.name = name;
            this.attributes = buildMap(attributes);
//            this.attributes = attributes;
        }

        private static Map<String, String> buildMap(String[] attributes) {
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < attributes.length; i += 2) {
                String key = attributes[i];
                String value = null;
                if (i < attributes.length - 1)
                    value = attributes[i + 1];
                map.put(key, value);
            }
            return map;
        }
/* These are never used
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            final HT ht = (HT) o;

            if (type != ht.type)
                return false;
            if (!name.equals(ht.name))
                return false;
            return attributes.equals(ht.attributes);
        }

        public int hashCode() {
            int result;
            result = type;
            result = 29 * result + name.hashCode();
            return result;
        }
*/
    }
}
