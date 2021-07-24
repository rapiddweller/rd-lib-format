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

package com.rapiddweller.format.xls;

/**
 * Describes how to format a bean property.
 * Created: 27.12.2015 07:55:04
 *
 * @author Volker Bergmann
 * @since 1.0.7
 */
public class PropFormat {

  private final String name;
  private String pattern;

  /**
   * Instantiates a new Prop format.
   *
   * @param name the name
   */
  public PropFormat(String name) {
    this.name = name;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets pattern.
   *
   * @return the pattern
   */
  public String getPattern() {
    return pattern;
  }

  /**
   * With date format prop format.
   *
   * @return the prop format
   */
  public PropFormat withDateFormat() {
    return withPattern("dd.MM.yyyy");
  }

  /**
   * With int format prop format.
   *
   * @return the prop format
   */
  public PropFormat withIntFormat() {
    return withPattern("#,##0");
  }

  /**
   * With percentage format prop format.
   *
   * @return the prop format
   */
  public PropFormat withPercentageFormat() {
    return withPattern("0%");
  }

  /**
   * With decimal format prop format.
   *
   * @param fractionDigits the fraction digits
   * @return the prop format
   */
  public PropFormat withDecimalFormat(int fractionDigits) {
    String pattern;
    switch (fractionDigits) {
      case 0:
        pattern = "#,##0";
        break;
      case 1:
        pattern = "#,##0.0";
        break;
      case 2:
        pattern = "#,##0.00";
        break;
      case 3:
        pattern = "#,##0.000";
        break;
      default:
        pattern = "#,##0." + "0".repeat(Math.max(0, fractionDigits));
        break;
    }
    return withPattern(pattern);
  }

  /**
   * With pattern prop format.
   *
   * @param pattern the pattern
   * @return the prop format
   */
  public PropFormat withPattern(String pattern) {
    this.pattern = pattern;
    return this;
  }

  @Override
  public String toString() {
    return name + (pattern != null ? "[" + pattern + "]" : "");
  }

}
