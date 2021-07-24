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

package com.rapiddweller.format.html.model;

import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.common.collection.OrderedNameMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Parent class for HTML element classes.
 * Created: 06.01.2014 08:13:00
 *
 * @param <E> Generic parameter to be set by child classes to the child class itself
 * @author Volker Bergmann
 * @since 0.7.1
 */
public class HtmlElement<E extends HtmlElement<?>> extends HtmlComponent {

  private final String tagName;
  /**
   * The Inline.
   */
  protected boolean inline;
  /**
   * The Attributes.
   */
  protected OrderedNameMap<String> attributes;
  private final List<HtmlComponent> components;

  /**
   * Instantiates a new Html element.
   *
   * @param tagName the tag name
   * @param inline  the inline
   */
  public HtmlElement(String tagName, boolean inline) {
    this.tagName = tagName;
    this.inline = inline;
    this.attributes = new OrderedNameMap<>();
    this.components = new ArrayList<>();
  }

  /**
   * With class e.
   *
   * @param klass the klass
   * @return the e
   */
  public E withClass(String klass) {
    return this.withAttribute("class", klass);
  }

  /**
   * With style e.
   *
   * @param style the style
   * @return the e
   */
  public E withStyle(String style) {
    return this.withAttribute("style", style);
  }

  /**
   * With title e.
   *
   * @param title the title
   * @return the e
   */
  public E withTitle(String title) {
    return withAttribute("title", title);
  }

  /**
   * With align e.
   *
   * @param align the align
   * @return the e
   */
  public E withAlign(String align) {
    return withAttribute("align", align);
  }

  /**
   * With valign e.
   *
   * @param valign the valign
   * @return the e
   */
  public E withValign(String valign) {
    return withAttribute("valign", valign);
  }

  /**
   * With attribute e.
   *
   * @param attributeName  the attribute name
   * @param attributeValue the attribute value
   * @return the e
   */
  @SuppressWarnings("unchecked")
  protected E withAttribute(String attributeName, String attributeValue) {
    attributes.put(attributeName, attributeValue);
    return (E) this;
  }

  /**
   * Add break e.
   *
   * @return the e
   */
  public E addBreak() {
    return addComponent(HtmlFactory.br());
  }

  /**
   * With raw text content e.
   *
   * @param text the text
   * @return the e
   */
  public E withRawTextContent(String text) {
    return withTextContent(text, false, false);
  }

  /**
   * With text content e.
   *
   * @param text             the text
   * @param escape           the escape
   * @param convertLinefeeds the convert linefeeds
   * @return the e
   */
  public E withTextContent(String text, boolean escape, boolean convertLinefeeds) {
    return this.withComponents(new TextComponent(text, escape, convertLinefeeds));
  }

  /**
   * With components e.
   *
   * @param newComponents the new components
   * @return the e
   */
  @SuppressWarnings("unchecked")
  public E withComponents(HtmlComponent... newComponents) {
    setComponents(newComponents);
    return (E) this;
  }

  /**
   * Sets components.
   *
   * @param newComponents the new components
   */
  public void setComponents(HtmlComponent... newComponents) {
    this.components.clear();
    for (HtmlComponent component : newComponents) {
      addComponent(component);
    }
  }

  /**
   * Add component e.
   *
   * @param textToAdd the text to add
   * @return the e
   */
  public E addComponent(String textToAdd) {
    return addComponent(new TextComponent(textToAdd));
  }

  /**
   * Add components.
   *
   * @param components the components
   */
  public void addComponents(HtmlComponent... components) {
    for (HtmlComponent component : components) {
      addComponent(component);
    }
  }

  /**
   * Add component e.
   *
   * @param componentToAdd the component to add
   * @return the e
   */
  @SuppressWarnings("unchecked")
  public E addComponent(HtmlComponent componentToAdd) {
    this.components.add(componentToAdd);
    return (E) this;
  }

  /**
   * Gets tag name.
   *
   * @return the tag name
   */
  public String getTagName() {
    return tagName;
  }

  /**
   * Is inline boolean.
   *
   * @return the boolean
   */
  public boolean isInline() {
    return inline;
  }


  // rendering -------------------------------------------------------------------------------------------------------

  /**
   * Format start tag string.
   *
   * @return the string
   */
  protected String formatStartTag() {
    StringBuilder builder = new StringBuilder();
    builder.append('<').append(tagName);
    if (!attributes.isEmpty()) {
      formatAttributes(builder);
    }
    builder.append('>');
    if (!inline) {
      builder.append(SystemInfo.getLineSeparator());
    }
    return builder.toString();
  }

  /**
   * Format atomic tag string.
   *
   * @return the string
   */
  protected String formatAtomicTag() {
    StringBuilder builder = new StringBuilder();
    builder.append('<').append(tagName);
    if (!attributes.isEmpty()) {
      formatAttributes(builder);
    }
    builder.append("/>");
    if (!inline) {
      builder.append(SystemInfo.getLineSeparator());
    }
    return builder.toString();
  }

  /**
   * Format attributes.
   *
   * @param builder the builder
   */
  protected void formatAttributes(StringBuilder builder) {
    for (Map.Entry<String, String> att : attributes.entrySet()) {
      builder.append(' ').append(att.getKey()).append("=\"").append(att.getValue()).append('"');
    }
  }

  /**
   * Format components string.
   *
   * @return the string
   */
  protected String formatComponents() {
    StringBuilder builder = new StringBuilder();
    for (HtmlComponent component : this.components) {
      builder.append(component);
    }
    return builder.toString();
  }

  /**
   * Format end tag string.
   *
   * @return the string
   */
  protected String formatEndTag() {
    StringBuilder builder = new StringBuilder();
    if (!inline) {
      builder.append(SystemInfo.getLineSeparator());
    }
    builder.append("</").append(tagName).append('>');
    if (!inline) {
      builder.append(SystemInfo.getLineSeparator());
    }
    return builder.toString();
  }

  @Override
  public String toString() {
    if (components.isEmpty()) {
      return formatAtomicTag();
    } else {
      return formatStartTag() + formatComponents() + formatEndTag();
    }
  }

}
