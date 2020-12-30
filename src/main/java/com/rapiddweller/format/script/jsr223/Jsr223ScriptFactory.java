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
package com.rapiddweller.format.script.jsr223;

import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.rapiddweller.common.FileUtil;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.format.script.Script;
import com.rapiddweller.format.script.ScriptFactory;

/**
 * Creates {@link Jsr223Script}s.
 * 
 * Created at 23.12.2008 07:35:08
 * @since 0.4.7
 * @author Volker Bergmann
 */

public class Jsr223ScriptFactory implements ScriptFactory {

	private static ScriptEngineManager factory = new ScriptEngineManager();
	
	private ScriptEngine engine;
	
	public Jsr223ScriptFactory(ScriptEngine engine) {
		this.engine = engine;
	}

	@Override
	public Script parseText(String text) {
		return parseText(text, engine);
	}

	@Override
	public Script readFile(String uri) throws IOException {
		String text = IOUtil.getContentOfURI(uri);
		String type = FileUtil.suffix(uri);
		return parseText(text, type);
	}
	
	public static Script parseText(String text, String engineId) {
		return new Jsr223Script(text, factory.getEngineByName(engineId));
	}

	private static Script parseText(String text, ScriptEngine engine) {
		return new Jsr223Script(text, engine);
	}

}
