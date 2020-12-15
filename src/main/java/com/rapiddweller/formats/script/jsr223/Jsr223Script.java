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
package com.rapiddweller.formats.script.jsr223;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.script.ScriptEngine;

import com.rapiddweller.commons.Assert;
import com.rapiddweller.commons.Context;
import com.rapiddweller.formats.script.Script;
import com.rapiddweller.formats.script.ScriptException;

/**
 * Provides {@link Script} functionality based on JSR 227: Scripting for the Java platform.
 * 
 * Created at 23.12.2008 07:19:54
 * @since 0.4.7
 * @author Volker Bergmann
 */

public class Jsr223Script implements Script {
	
	private ScriptEngine engine;

	private String text;

	public Jsr223Script(String text, ScriptEngine engine) {
		Assert.notEmpty(text, "text");
		Assert.notNull(engine, "engine");
		this.text = text;
		this.engine = engine;
	}

	@Override
	public Object evaluate(Context context) throws ScriptException {
		try {
			engine.put("benerator", context);
			for (Map.Entry<String, Object> entry : context.entrySet())
				engine.put(entry.getKey(), entry.getValue());
			return engine.eval(text);
		} catch (javax.script.ScriptException e) {
			throw new ScriptException("Error in evaluating script", e);
		}
	}

	@Override
	public void execute(Context context, Writer out) throws ScriptException, IOException {
		out.write(String.valueOf(evaluate(context)));
	}

	@Override
	public String toString() {
		return text;
	}
}
