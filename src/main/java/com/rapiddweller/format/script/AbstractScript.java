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
import com.rapiddweller.common.converter.ToStringConverter;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Abstract implementation of the Script interface. 
 * When inheriting from it, you must overwrite at least one of the methods 
 * <code>evaluate()</code> and <code>execute()</code>.
 * 
 * Created at 23.12.2008 07:15:39
 * @since 0.4.7
 * @author Volker Bergmann
 */

public abstract class AbstractScript implements Script {

	@Override
	public Object evaluate(Context context) throws ScriptException {
		try {
			StringWriter writer = new StringWriter();
			execute(context, writer);
			return writer.toString();
		} catch (IOException e) {
			throw new ScriptException(e);
		}
	}

	@Override
	public void execute(Context context, Writer out) throws ScriptException, IOException {
		out.write(ToStringConverter.convert(evaluate(context), ""));
	}
}
