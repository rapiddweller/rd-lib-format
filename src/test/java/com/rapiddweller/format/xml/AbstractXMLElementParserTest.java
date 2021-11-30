/*
 * Copyright (C) 2011-2021 Volker Bergmann (volker.bergmann@bergmann-it.de).
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

import org.apache.xerces.dom.DocumentImpl;
import org.junit.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link AbstractXMLElementParser}.
 * Created: 14.12.2012 20:24:54
 * @author Volker Bergmann
 * @since 0.6.15
 */
public class AbstractXMLElementParserTest {

  @Test
  public void testRenderUnsupportedAttributesMessage() {
    MyXMLElementParser parser = new MyXMLElementParser();
    DocumentImpl doc = new DocumentImpl();
    Attr attr = doc.createAttribute("att1");
    String expectedMessage = "Illegal attribute for element <elem>: att1.";
    assertEquals(expectedMessage, expectedMessage);
  }

  static class MyXMLElementParser extends AbstractXMLElementParser<Object> {

    static final AttrInfoSupport ATTR_SUPPORT;

    static {
      ATTR_SUPPORT = new AttrInfoSupport("XXX-0001");
      ATTR_SUPPORT.add("req1", true, "XXX-0002");
      ATTR_SUPPORT.add("req2", true, "XXX-0003");
      ATTR_SUPPORT.add("opt1", false, "XXX-0004");
      ATTR_SUPPORT.add("opt2", false, "XXX-0005");
    }

    public MyXMLElementParser() {
      super("elem", ATTR_SUPPORT);
    }

    @Override
    protected Object doParse(Element element, Element[] parentXmlPath,  Object[] parentComponentPath, ParseContext<Object> context) {
      return null;
    }

    @Override
    public boolean supportsElementName(String elementName) {
      return "elem".equals(elementName);
    }
  }

}
