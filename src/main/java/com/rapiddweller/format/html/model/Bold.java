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
 * Represents an HTML bold element.
 * Created: 06.01.2014 08:08:47
 *
 * @author Volker Bergmann
 * @since 0.7.1
 */
public class Bold extends HtmlElement<Bold> {

  /**
   * Instantiates a new Bold.
   *
   * @param text the text
   */
  public Bold(String text) {
    this(new TextComponent(text));
  }

  /**
   * Instantiates a new Bold.
   *
   * @param component the component
   */
  public Bold(HtmlComponent component) {
    super("b", true);
    this.withComponents(component);
  }

}
