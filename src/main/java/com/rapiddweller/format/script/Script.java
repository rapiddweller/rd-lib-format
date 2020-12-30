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

import java.io.IOException;
import java.io.Writer;

/**
 * Abstraction of a Script.
 * 
 * Created: 03.02.2007 11:50:27
 * @author Volker Bergmann
 */
public interface Script {
    void execute(Context context, Writer out) throws ScriptException, IOException;
    Object evaluate(Context context) throws ScriptException;
}
