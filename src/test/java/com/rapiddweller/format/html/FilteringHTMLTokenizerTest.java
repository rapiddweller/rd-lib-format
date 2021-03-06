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

import com.rapiddweller.format.html.parser.DefaultHTMLTokenizer;
import com.rapiddweller.format.html.parser.FilteringHTMLTokenizer;
import com.rapiddweller.format.html.parser.HTMLTokenizer;
import com.rapiddweller.format.html.util.HTMLTokenFilter;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link FilteringHTMLTokenizer}.
 * Created: 16.06.2007 05:53:50
 *
 * @author Volker Bergmann
 * @since 0.1
 */
public class FilteringHTMLTokenizerTest {

  private static final String HTML = "<html><body>Links<ul>" +
      "<li><a href='http://databene.org'>Great Tools</a></li>" +
      "<li><a href='http://bergmann-it.de'>Volker Bergmann</a></li>" +
      "</ul></body></html>";

  /**
   * Test link iteration.
   *
   * @throws IOException    the io exception
   * @throws ParseException the parse exception
   */
  @Test
  public void testLinkIteration() throws IOException, ParseException {
    HTMLTokenizer source = new DefaultHTMLTokenizer(new StringReader(HTML));
    HTMLTokenFilter filter = new HTMLTokenFilter(HTMLTokenizer.START_TAG, "a");
    HTMLTokenizer tokenizer = new FilteringHTMLTokenizer(source, filter);

    tokenizer.nextToken();
    assertEquals(HTMLTokenizer.START_TAG, tokenizer.tokenType());
    assertEquals("a", tokenizer.name());
    assertEquals("http://databene.org", tokenizer.attributes().get("href"));

    tokenizer.nextToken();
    assertEquals(HTMLTokenizer.START_TAG, tokenizer.tokenType());
    assertEquals("a", tokenizer.name());
    assertEquals("http://bergmann-it.de", tokenizer.attributes().get("href"));

    assertEquals(HTMLTokenizer.END, tokenizer.nextToken());
  }

}
