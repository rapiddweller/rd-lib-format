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

package com.rapiddweller.format.script;

import com.rapiddweller.common.StringUtil;

/**
 * Describes a script.
 * Created: 09.08.2010 16:40:50
 *
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class ScriptDescriptor {

  /**
   * The Script engine.
   */
  public final String scriptEngine;
  /**
   * The Level.
   */
  public final ScriptLevel level;
  /**
   * The Text.
   */
  public final String text;

  /**
   * Instantiates a new Script descriptor.
   *
   * @param text the text
   */
  public ScriptDescriptor(String text) {
    if (text != null && text.startsWith("{") && text.endsWith("}")) {
      text = text.substring(1, text.length() - 1);
      String[] tokens = StringUtil.splitOnFirstSeparator(text, ':');
      if (tokens.length > 1 && ScriptUtil.getFactory(tokens[0], false) != null) {
        this.scriptEngine = tokens[0];
        this.text = tokens[1];
      } else {
        this.scriptEngine = ScriptUtil.getDefaultScriptEngine();
        this.text = text;
      }
      this.level = ScriptLevel.SCRIPT;
    } else {
      this.scriptEngine = null;
      this.level = ScriptLevel.NONE;
      this.text = text;
    }
  }

}
