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
import com.rapiddweller.common.Context;
import com.rapiddweller.common.context.DefaultContext;
import com.rapiddweller.common.xml.XMLUtil;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Provides context informations and operations for XML parsing.
 * Created: 05.12.2010 12:09:54
 *
 * @param <E> the type of components that constitute the path
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class ParseContext<E> implements Context {

  /**
   * The Factory.
   */
  protected XMLElementParserFactory<E> factory;
  /**
   * The Path component type.
   */
  protected Class<E> pathComponentType;
  private final Context context;

  /**
   * Instantiates a new Parse context.
   *
   * @param pathComponentType the path component type
   */
  public ParseContext(Class<E> pathComponentType) {
    this(pathComponentType, new XMLElementParserFactory<>());
  }

  /**
   * Instantiates a new Parse context.
   *
   * @param pathComponentType the path component type
   * @param factory           the factory
   */
  public ParseContext(Class<E> pathComponentType, XMLElementParserFactory<E> factory) {
    this.pathComponentType = pathComponentType;
    this.factory = factory;
    this.context = new DefaultContext();
  }

  /**
   * Add parser.
   *
   * @param parser the parser
   */
  public void addParser(XMLElementParser<E> parser) {
    factory.addParser(parser);
  }

  /**
   * Parse element e.
   *
   * @param element    the element
   * @param parentPath the parent path
   * @return the e
   */
  public E parseElement(Element element, E[] parentPath) {
    XMLElementParser<E> parser = factory.getParser(element, parentPath);
    return parser.parse(element, parentPath, this);
  }

  /**
   * Parse child elements of list.
   *
   * @param element     the element
   * @param currentPath the current path
   * @return the list
   */
  public List<E> parseChildElementsOf(Element element, E[] currentPath) {
    List<E> result = new ArrayList<>();
    for (Element childElement : XMLUtil.getChildElements(element)) {
      result.add(parseChildElement(childElement, currentPath));
    }
    return result;
  }

  /**
   * Parse child element e.
   *
   * @param childElement the child element
   * @param currentPath  the current path
   * @return the e
   */
  public E parseChildElement(Element childElement, E[] currentPath) {
    return parseElement(childElement, currentPath);
  }

  /**
   * Create sub path e [ ].
   *
   * @param parentPath    the parent path
   * @param currentObject the current object
   * @return the e [ ]
   */
  @SuppressWarnings("unchecked")
  public E[] createSubPath(E[] parentPath, E currentObject) {
    if (parentPath == null) {
      return ArrayUtil.buildObjectArrayOfType(pathComponentType, currentObject);
    } else {
      return ArrayUtil.append(currentObject, parentPath);
    }
  }

  @Override
  public Object get(String key) {
    return context.get(key);
  }

  @Override
  public void set(String key, Object value) {
    context.set(key, value);
  }

  @Override
  public void remove(String key) {
    context.remove(key);
  }

  @Override
  public boolean contains(String key) {
    return context.contains(key);
  }

  @Override
  public Set<String> keySet() {
    return context.keySet();
  }

  @Override
  public Set<Entry<String, Object>> entrySet() {
    return context.entrySet();
  }

}
