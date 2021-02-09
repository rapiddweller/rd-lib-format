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

package com.rapiddweller.format.dot;

/**
 * Represents a Dot node style.
 * Created: 27.05.2014 07:45:29
 *
 * @author Volker Bergmann
 * @since 0.8.2
 */
public enum NodeStyle {
  /**
   * Solid node style.
   */
  solid,
  /**
   * Dashed node style.
   */
  dashed,
  /**
   * Dotted node style.
   */
  dotted,
  /**
   * Bold node style.
   */
  bold,
  /**
   * Rounded node style.
   */
  rounded,
  /**
   * Diagonals node style.
   */
  diagonals,
  /**
   * Filled node style.
   */
  filled,
  /**
   * Striped node style.
   */
  striped,
  /**
   * Wedged node style.
   */
  wedged
}
