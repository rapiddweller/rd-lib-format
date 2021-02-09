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

/**
 * Represents a plain {@link ComplexType} of an XML schema.
 * Created: 16.05.2014 20:01:00
 *
 * @author Volker Bergmann
 * @since 0.8.2
 */
public class PlainComplexType extends ComplexType {

  private Integer minLength;
  private Integer maxLength;

  /**
   * Instantiates a new Plain complex type.
   *
   * @param name the name
   */
  public PlainComplexType(String name) {
    super(name);
  }

  /**
   * Gets min length.
   *
   * @return the min length
   */
  public Integer getMinLength() {
    return minLength;
  }

  /**
   * Sets min length.
   *
   * @param minLength the min length
   */
  public void setMinLength(Integer minLength) {
    this.minLength = minLength;
  }

  /**
   * Gets max length.
   *
   * @return the max length
   */
  public Integer getMaxLength() {
    return maxLength;
  }

  /**
   * Sets max length.
   *
   * @param maxLength the max length
   */
  public void setMaxLength(Integer maxLength) {
    this.maxLength = maxLength;
  }

  @Override
  public void printContent(String indent) {
    System.out.println(indent + super.toString());
    indent += "  ";
    for (Attribute attribute : attributes.values()) {
      attribute.printContent(indent);
    }
  }

}
