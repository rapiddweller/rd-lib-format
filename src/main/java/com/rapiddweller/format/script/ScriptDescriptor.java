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
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class ScriptDescriptor {
	
	public final String scriptEngine;
	public final ScriptLevel level;
	public final String text;

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
