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

package com.rapiddweller.format.html;

/**
 * Abstract implementation of an HTML anchor &lt;a&gt; with label and target.
 * Created: 13.06.2011 12:14:52
 *
 * @author Volker Bergmann
 * @since 0.5.8
 */
public abstract class Anchor {

  /**
   * The Label.
   */
  public final String label;
  /**
   * The Target.
   */
  public final String target;

  /**
   * Instantiates a new Anchor.
   *
   * @param label the label
   */
  public Anchor(String label) {
    this(label, null);
  }

  /**
   * Instantiates a new Anchor.
   *
   * @param label  the label
   * @param target the target
   */
  public Anchor(String label, String target) {
    this.label = label;
    this.target = target;
  }

}
