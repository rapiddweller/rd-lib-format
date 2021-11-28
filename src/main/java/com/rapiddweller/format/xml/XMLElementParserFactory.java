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

import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.common.exception.SyntaxError;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory method which provides {@link XMLElementParser}s.
 * Created: 05.12.2010 12:15:57
 * @param <E> the type of elements to provide
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class XMLElementParserFactory<E> {

  public static final String ILLEGAL_ROOT_ELEMENT = "Illegal root element";
  public static final String ILLEGAL_CHILD_ELEMENT = "Illegal child element";
  private static final String ILLEGAL_ELEMENT = "Illegal element";

  protected List<XMLElementParser<E>> parsers;

  public XMLElementParserFactory() {
    this.parsers = new ArrayList<>();
  }

  // interface -------------------------------------------------------------------------------------------------------

  public void addParser(XMLElementParser<E> parser) {
    this.parsers.add(parser);
  }

  public XMLElementParser<E> getParser(Element element, Element[] parentXmlPath, E[] parentComponentPath) {
    XMLElementParser<E> parser = findParser(element, parentXmlPath, parentComponentPath);
    if (parser != null) {
      return parser;
    } else {
      throw checkUnsupportedElement(element, parentXmlPath);
    }
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private XMLElementParser<E> findParser(Element element, Element[] parentXmlPath, E[] parentComponentPath) {
    for (int i = parsers.size() - 1; i >= 0; i--) { // search for parsers in reverse order, to child classes can override parsers of parent classes
      XMLElementParser<E> parser = parsers.get(i);
      if (parser.supports(element, parentXmlPath, parentComponentPath)) {
        return parser;
      }
    }
    return null;
  }

  private SyntaxError checkUnsupportedElement(Element element, Element[] parentXmlPath) {
    boolean nameSupported = elementNameSupported(element.getNodeName());
    Element parent = ArrayUtil.lastElementOf(parentXmlPath);
    String message;
    if (parent == null) {
      message = ILLEGAL_ROOT_ELEMENT + ": " + element.getNodeName();
    } else if (nameSupported){
      message = ILLEGAL_CHILD_ELEMENT + " of <" + parent.getNodeName() + ">: <" + element.getNodeName() + ">";
    } else {
      message = ILLEGAL_ELEMENT + ": <" + element.getNodeName() + ">";
    }
    return ExceptionFactory.getInstance().syntaxErrorForXmlElement(message, element, null);
  }

  private boolean elementNameSupported(String name) {
    for (int i = parsers.size() - 1; i >= 0; i--) { // search for parsers in reverse order, to child classes can override parsers of parent classes
      XMLElementParser<E> parser = parsers.get(i);
      if (parser.supportsElementName(name)) {
        return true;
      }
    }
    return false;
  }

}
