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
 * Represents an HTML anchor.
 * Created: 06.01.2014 08:55:54
 *
 * @author Volker Bergmann
 * @since 0.7.1
 */
public class Anchor extends HtmlElement<Anchor> {

  /**
   * Instantiates a new Anchor.
   *
   * @param label the label
   */
  public Anchor(String label) {
    this(new TextComponent(label));
  }

  /**
   * Instantiates a new Anchor.
   *
   * @param label the label
   */
  public Anchor(HtmlComponent label) {
    super("a", true);
    this.withComponents(label);
  }

  /**
   * With href anchor.
   *
   * @param url the url
   * @return the anchor
   */
  public Anchor withHref(String url) {
    return this.withAttribute("href", url);
  }

}
