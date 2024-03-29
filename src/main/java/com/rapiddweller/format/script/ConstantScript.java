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

import com.rapiddweller.common.Context;
import com.rapiddweller.common.exception.ScriptException;

import java.io.IOException;
import java.io.Writer;

/**
 * Script implementation that evaluates to a String constant.<br/><br/>
 * Created: 16.06.2007 06:15:32
 * @author Volker Bergmann
 */
public class ConstantScript extends AbstractScript {

  private final String text;

  public ConstantScript(String text) {
    this.text = text;
  }

  public void setVariable(String variableName, Object variableValue) {
    // nothing to do
  }

  @Override
  public void execute(Context context, Writer out) throws IOException, ScriptException {
    out.write(text);
  }

}
