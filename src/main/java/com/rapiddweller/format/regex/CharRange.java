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

import com.rapiddweller.common.CharSet;

/**
 * Represents a character within a range from {@link #first} to {@link #last}.
 * Created: 04.04.2014 17:05:57
 *
 * @author Volker Bergmann
 * @since 0.8.0
 */
public class CharRange extends RegexCharClass {

  private final String name;
  private final char first;
  private final char last;

  /**
   * Instantiates a new Char range.
   *
   * @param name  the name
   * @param first the first
   * @param last  the last
   */
  public CharRange(String name, char first, char last) {
    this.name = name;
    this.first = first;
    this.last = last;
  }

  @Override
  public CharSet getCharSet() {
    return new CharSet(name, first, last);
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public int hashCode() {
    return 31 * first + last;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    CharRange that = (CharRange) obj;
    return (this.first == that.first && this.last == that.last);
  }

}
