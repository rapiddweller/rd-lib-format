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

package com.rapiddweller.format.regex;

/**
 * Represents a group in a regular expression, e.g. '(abc)'.
 */
public class Group implements RegexPart {

  /**
   * The regular sub expression
   */
  private final RegexPart regex;

  /**
   * Constructor that takes a sub expression
   *
   * @param regex the regular expression for the group
   */
  public Group(RegexPart regex) {
    this.regex = regex;
  }

  /**
   * Returns the sub expression.
   *
   * @return the sub expression
   */
  public RegexPart getRegex() {
    return regex;
  }


  // RegexPart interface implementation ------------------------------------------------------------------------------

  @Override
  public int minLength() {
    return regex.minLength();
  }

  @Override
  public Integer maxLength() {
    return regex.maxLength();
  }


  // java.lang.Object overrides --------------------------------------------------------------------------------------

  /**
   * @see java.lang.Object#equals(Object)
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return regex.equals(((Group) o).regex);
  }

  /**
   * @see java.lang.Object#equals(Object)
   */
  @Override
  public int hashCode() {
    return regex.hashCode();
  }

  /**
   * @see java.lang.Object#equals(Object)
   */
  @Override
  public String toString() {
    return "(" + regex + ")";
  }

}
