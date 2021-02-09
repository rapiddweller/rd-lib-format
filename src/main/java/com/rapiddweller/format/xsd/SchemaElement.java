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

package com.rapiddweller.format.xsd;

import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.Visitor;

/**
 * Parent class of all components that are part of an XML schema.
 * Created: 16.05.2014 18:31:43
 *
 * @author Volker Bergmann
 * @since 0.8.2
 */
public class SchemaElement {

  /**
   * The Documentation.
   */
  protected String documentation;

  /**
   * Gets documentation.
   *
   * @return the documentation
   */
  public String getDocumentation() {
    return documentation;
  }

  /**
   * Sets documentation.
   *
   * @param documentation the documentation
   */
  public void setDocumentation(String documentation) {
    this.documentation = documentation;
  }

  /**
   * Render short documentation string.
   *
   * @return the string
   */
  protected String renderShortDocumentation() {
    return (documentation != null ? " (" + StringUtil.normalizeSpace(documentation) + ")" : "");
  }

  /**
   * Accept.
   *
   * @param visitor the visitor
   */
  public void accept(Visitor<SchemaElement> visitor) {
    visitor.visit(this);
  }

}
