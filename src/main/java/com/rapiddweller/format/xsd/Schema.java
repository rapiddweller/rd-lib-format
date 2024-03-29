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

import com.rapiddweller.common.Named;
import com.rapiddweller.common.Visitor;
import com.rapiddweller.common.collection.OrderedNameMap;

import java.util.Map;

/**
 * Represents an XML schema.
 * Created: 16.05.2014 18:30:35
 *
 * @author Volker Bergmann
 * @since 0.8.2
 */
public class Schema extends SchemaElement implements Named {

  private final Map<String, SimpleType> simpleTypes;
  private final Map<String, ComplexType> complexTypes;
  private ComplexMember member;

  /**
   * Instantiates a new Schema.
   */
  public Schema() {
    this.simpleTypes = new OrderedNameMap<>();
    this.complexTypes = new OrderedNameMap<>();
  }

  @Override
  public String getName() {
    return (member != null ? member.getName() : null);
  }

  /**
   * Add simple type.
   *
   * @param simpleType the simple type
   */
  public void addSimpleType(SimpleType simpleType) {
    this.simpleTypes.put(simpleType.getName(), simpleType);
  }

  /**
   * Add complex type.
   *
   * @param complexType the complex type
   */
  public void addComplexType(ComplexType complexType) {
    this.complexTypes.put(complexType.getName(), complexType);
  }

  /**
   * Gets complex type.
   *
   * @param name the name
   * @return the complex type
   */
  public ComplexType getComplexType(String name) {
    return complexTypes.get(name);
  }

  /**
   * Gets member.
   *
   * @return the member
   */
  public ComplexMember getMember() {
    return member;
  }

  /**
   * Sets member.
   *
   * @param member the member
   */
  public void setMember(ComplexMember member) {
    this.member = member;
  }

  /**
   * Print content.
   */
  public void printContent() {
    System.out.println("Schema" + renderShortDocumentation());
    for (SimpleType simpleType : simpleTypes.values()) {
      simpleType.printContent("  ");
    }
    for (ComplexType complexType : complexTypes.values()) {
      complexType.printContent("  ");
    }
    member.printContent("  ");
  }

  @Override
  public void accept(Visitor<SchemaElement> visitor) {
    super.accept(visitor);
    for (SimpleType type : simpleTypes.values()) {
      type.accept(visitor);
    }
    for (ComplexType type : complexTypes.values()) {
      type.accept(visitor);
    }
    member.accept(visitor);
  }

}
