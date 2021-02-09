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

/**
 * Creates common HTML elements in utility methods.
 * Created: 06.01.2014 08:11:27
 *
 * @author Volker Bergmann
 * @since 0.7.1
 */
public class HtmlFactory {

  private HtmlFactory() {
  }

  /**
   * Bold bold.
   *
   * @param content the content
   * @return the bold
   */
  public static Bold bold(String content) {
    return new Bold(content);
  }

  /**
   * Bold bold.
   *
   * @param content the content
   * @return the bold
   */
  public static Bold bold(HtmlComponent content) {
    return new Bold(content);
  }

  /**
   * Font font.
   *
   * @param content the content
   * @return the font
   */
  public static Font font(String content) {
    return new Font(content);
  }

  /**
   * Font font.
   *
   * @param content the content
   * @return the font
   */
  public static Font font(HtmlComponent content) {
    return new Font(content);
  }

  /**
   * Url anchor anchor.
   *
   * @param label the label
   * @param url   the url
   * @return the anchor
   */
  public static Anchor urlAnchor(String label, String url) {
    return new Anchor(label).withHref(url);
  }

  /**
   * Br html element.
   *
   * @return the html element
   */
  @SuppressWarnings("rawtypes")
  public static HtmlElement<?> br() {
    return new HtmlElement("br", true);
  }

}
