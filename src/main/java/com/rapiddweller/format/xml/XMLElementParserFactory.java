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

import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.common.ParseException;
import com.rapiddweller.common.xml.XMLUtil;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory method which provides {@link XMLElementParser}s.
 * Created: 05.12.2010 12:15:57
 *
 * @param <E> the type of elements to provide
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class XMLElementParserFactory<E> {

  /**
   * The Parsers.
   */
  protected List<XMLElementParser<E>> parsers;

  /**
   * Instantiates a new Xml element parser factory.
   */
  public XMLElementParserFactory() {
    this.parsers = new ArrayList<XMLElementParser<E>>();
  }

  /**
   * Add parser.
   *
   * @param parser the parser
   */
  public void addParser(XMLElementParser<E> parser) {
    this.parsers.add(parser);
  }

  /**
   * Gets parser.
   *
   * @param element    the element
   * @param parentPath the parent path
   * @return the parser
   */
  public XMLElementParser<E> getParser(Element element, E[] parentPath) {
    for (int i = parsers.size() - 1; i >= 0; i--) { // search for parsers in reverse order, to child classes can override parsers of parent classes
      XMLElementParser<E> parser = parsers.get(i);
      if (parser.supports(element, parentPath)) {
        return parser;
      }
    }
    Object parent = (ArrayUtil.isEmpty(parentPath) ? null : ArrayUtil.lastElementOf(parentPath));
    String message = "Syntax Error: Element '" + element.getNodeName() +
        "' not supported " + (parent != null ?
        "in the context of a " + parent.getClass().getSimpleName() :
        "as top level element");
    throw new ParseException(message, XMLUtil.format(element));
  }

}
