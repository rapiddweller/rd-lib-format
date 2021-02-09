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

/**
 * Enumerates the kinds of difference that may be diagnosed on the lowest level of comparison, the {@link DiffDetail}.
 * Created: 21.11.2013 11:33:30
 *
 * @author Volker Bergmann
 * @since 1.0.5
 */
public enum DiffDetailType {

  /**
   * An expected element was found but differs in some way.
   */
  DIFFERENT,

  /**
   * An element was which was not expected at this place.
   */
  UNEXPECTED,

  /**
   * An expected element was not found.
   */
  MISSING,

  /**
   * An expected element was found at an unexpected place.
   */
  MOVED

}
