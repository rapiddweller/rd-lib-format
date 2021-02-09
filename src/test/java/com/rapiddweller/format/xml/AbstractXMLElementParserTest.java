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

package com.rapiddweller.format.xml;

import com.rapiddweller.common.CollectionUtil;
import org.junit.Test;
import org.w3c.dom.Element;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link AbstractXMLElementParser}.
 * Created: 14.12.2012 20:24:54
 *
 * @author Volker Bergmann
 * @since 0.6.15
 */
public class AbstractXMLElementParserTest {

  /**
   * Test render unsupported attributes message.
   */
  @Test
  public void testRenderUnsupportedAttributesMessage() {
    MyXMLElementParser parser = new MyXMLElementParser();
    StringBuilder message = parser.renderUnsupportedAttributesMessage("att1");
    String expectedMessage = "attribute 'att1' is not supported. The attributes supported by <elem> are: " +
        "req1, req2, opt1, opt2";
    assertEquals(expectedMessage, message.toString());
  }

  /**
   * The type My xml element parser.
   */
  static class MyXMLElementParser extends AbstractXMLElementParser<Object> {

    /**
     * Instantiates a new My xml element parser.
     */
    public MyXMLElementParser() {
      super("elem", CollectionUtil.toSortedSet("req1", "req2"),
          CollectionUtil.toSortedSet("opt1", "opt2"));
    }

    @Override
    protected Object doParse(Element element, Object[] parentPath, ParseContext<Object> context) {
      return null;
    }

  }

}
