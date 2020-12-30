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
package com.rapiddweller.format.html;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.*;
import java.text.ParseException;

import com.rapiddweller.common.Encodings;
import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.format.html.HTML2XML;

/**
 * Tests the {@link HTML2XML} class.
 * Created: 25.01.2007 17:31:28
 * @since 0.1
 * @author Volker Bergmann
 */
public class HTML2XMLTest {

    private static final String SEP = SystemInfo.getLineSeparator();
    private static final String XML_HEADER_ROW =
    	"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + SEP;

    private static final String HTML1 = "<html>" + SEP +
            "\t<?XXX level=\"3\"?>" + SEP +
            "\t<!-- some comment -->" + SEP +
            "\t<tr>" + SEP +
            "\t\t<td>" + SEP +
            "\t\t\t<a></td>" + SEP +
            "\t\t<td>" + SEP +
            "\t\t\t<img src=\"http://databene.org\"></td>" + SEP +
            "\t\t<td>" + SEP +
            "\t\t\t<img></td>" + SEP +
            "\t</tr>" + SEP +
            "</html>";

    private static final String XML1 =
        XML_HEADER_ROW +
        "<html>" + SEP +
        "\t<?XXX level=\"3\"?>" + SEP +
        "\t<!-- some comment -->" + SEP +
        "\t<tr>" + SEP +
        "\t\t<td>" + SEP +
        "\t\t\t<a></a></td>" + SEP +
        "\t\t<td>" + SEP +
        "\t\t\t<img src=\"http://databene.org\"></img></td>" + SEP +
        "\t\t<td>" + SEP +
        "\t\t\t<img></img></td>" + SEP +
        "\t</tr>" + SEP +
        "</html>";

    private static final String HTML2 = 
    	"<a><img src=\"http://databene.org\"><br></a>";

    private static final String XML2 =
        XML_HEADER_ROW +
        "<html><a><img src=\"http://databene.org\"></img><br></br></a></html>";

    private static final String HTML3 = 
    	"R&B";

    private static final String XML3 =
        XML_HEADER_ROW +
        "<html>R&amp;B</html>";
    
    private static final String HTML4 = 
    	"<img src='image.php?s=1&amp;u=1&amp;type=sigpic&amp;dateline=1' a" +
    	"lt='<a href=http://www.xyz.com/image.php?type=pic&amp;userid=1&amp;dateline=1>" +
    	"http://www.xyz.com/image....ine=1</a>' border='0' />";
    
    private static final String XML4 =
        XML_HEADER_ROW +
        "<html><img src=\"image.php?s=1&amp;u=1&amp;type=sigpic&amp;dateline=1\" " +
        "alt=\"&lt;a href=http://www.xyz.com/image.php?type=pic&amp;userid=1&amp;dateline=1&gt;" +
        "http://www.xyz.com/image....ine=1&lt;/a&gt;\" border=\"0\"/></html>";
    
    
    // tests -----------------------------------------------------------------------------------------------------------

    @Test
    public void testNormal() throws Exception {
		check(HTML1, XML1);
    }

    @Test
    public void testMissingHtml() throws Exception {
		check(HTML2, XML2);
    }

    @Test
    public void testAmpersand() throws Exception {
		check(HTML3, XML3);
    }
    
    @Test
    public void testXmlInAttribute() throws Exception {
		check(HTML4, XML4);
    }
    
    // helpers ---------------------------------------------------------------------------------------------------------

	private static void check(String source, String result) throws Exception {
		checkStrings(source, result);
		checkStreams(source, result);
	}

	private static void checkStreams(String source, String result) throws IOException,
			ParseException {
		StringReader in = new StringReader(source);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
        HTML2XML.convert(in, out, Encodings.UTF_8);
        in.close();
        out.close();
		assertEquals(result, out.toString());
	}
	
	private static void checkStrings(String source, String result) 
			throws IOException, ParseException {
		assertEquals(result, HTML2XML.convert(source));
	}

}
