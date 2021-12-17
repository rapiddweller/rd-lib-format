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

package com.rapiddweller.format.compare;

import com.rapiddweller.common.NullSafeComparator;

/**
 * Provides a key definition for an object based on a {@link #locator} expression
 * for the related object and a {@link KeyExpression} to derive a key from the object.
 * The syntax of the locator and the keyExpression depends on the type of the related
 * object structure and comparator implementation, e.g. XPath expressions for XML.<br/><br/>
 * Created: 13.06.2016 18:31:01
 * @author Volker Bergmann
 * @since 1.0.11
 */
public class KeyExpression {

  private final String locator;
  private final String expression;

  public KeyExpression(String locator, String expression) {
    this.locator = locator;
    this.expression = expression;
  }

  public String getLocator() {
    return locator;
  }

  public String getExpression() {
    return expression;
  }

  @Override
  public int hashCode() {
    return ((expression == null) ? 0 : expression.hashCode()) * 31
        + ((locator == null) ? 0 : locator.hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    KeyExpression that = (KeyExpression) obj;
    return NullSafeComparator.equals(this.expression, that.expression)
        && NullSafeComparator.equals(this.locator, that.locator);
  }

  @Override
  public String toString() {
    return locator + " -> " + expression;
  }

}
