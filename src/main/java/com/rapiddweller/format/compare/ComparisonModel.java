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

import java.util.List;

/**
 * Provides comparison for equality and comparison for objects of a given parameterized type E.
 * Created: 20.11.2013 17:40:05
 *
 * @author Volker Bergmann
 * @since 1.0.5
 */
public interface ComparisonModel {
  /**
   * Classifier of string.
   *
   * @param object the object
   * @return the string
   */
  String classifierOf(Object object);

  /**
   * Add key expression.
   *
   * @param locator       the locator
   * @param keyExpression the key expression
   */
  void addKeyExpression(String locator, String keyExpression);

  /**
   * Gets key expressions.
   *
   * @return the key expressions
   */
  List<KeyExpression> getKeyExpressions();

  /**
   * Equal boolean.
   *
   * @param o1 the o 1
   * @param o2 the o 2
   * @return the boolean
   */
  boolean equal(Object o1, Object o2);

  /**
   * Correspond boolean.
   *
   * @param o1 the o 1
   * @param o2 the o 2
   * @return the boolean
   */
  boolean correspond(Object o1, Object o2);

  /**
   * Sub path string.
   *
   * @param array the array
   * @param index the index
   * @return the string
   */
  String subPath(Object[] array, int index);
}
