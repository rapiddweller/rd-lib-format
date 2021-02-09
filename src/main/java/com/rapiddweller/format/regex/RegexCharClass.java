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
 * Parent class for classes that represent a single character in a regular expression.
 * Created: 04.04.2014 16:24:52
 *
 * @author Volker Bergmann
 * @since 0.8.0
 */
public abstract class RegexCharClass implements RegexPart {

  @Override
  public int minLength() {
    return 1;
  }

  @Override
  public Integer maxLength() {
    return 1;
  }

  /**
   * Gets char set.
   *
   * @return the char set
   */
  public abstract CharSet getCharSet();

}
