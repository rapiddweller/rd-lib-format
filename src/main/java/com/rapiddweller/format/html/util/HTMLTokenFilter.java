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

package com.rapiddweller.format.html.util;

import com.rapiddweller.common.Filter;
import com.rapiddweller.format.html.parser.HTMLTokenizer;

/**
 * {@link Filter} that accepts HTML tokens by type and name.
 * <p>
 * Created: 16.06.2007 05:54:38
 *
 * @author Volker Bergmann
 */
public class HTMLTokenFilter implements Filter<HTMLTokenizer> {

  private final int tokenType;
  private final String name;

  /**
   * Instantiates a new Html token filter.
   *
   * @param tokenType the token type
   * @param name      the name
   */
  public HTMLTokenFilter(int tokenType, String name) {
    this.tokenType = tokenType;
    this.name = name;
  }

  @Override
  public boolean accept(HTMLTokenizer candidate) {
    if (this.tokenType != candidate.tokenType()) {
      return false;
    }
    return (this.name != null && this.name.equalsIgnoreCase(candidate.name()));
  }

  /**
   * Gets token type.
   *
   * @return the token type
   */
  public int getTokenType() {
    return tokenType;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }
}
