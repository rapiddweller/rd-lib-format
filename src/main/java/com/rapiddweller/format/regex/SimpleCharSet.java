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
package com.rapiddweller.format.regex;

import java.util.Set;

import com.rapiddweller.common.CharSet;

/**
 * Represents a character from a simple character set.
 * Created: 04.04.2014 17:59:13
 * @since 0.8.0
 * @author Volker Bergmann
 */

public class SimpleCharSet extends RegexCharClass {
	
	private final String name;
	private final CharSet chars;
	
	public SimpleCharSet(String name, Set<Character> chars) {
		this(name, new CharSet(chars));
	}
	
	public SimpleCharSet(String name, CharSet chars) {
		this.name = name;
		this.chars = chars;
	}
	
	@Override
	public CharSet getCharSet() {
		return chars;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		return chars.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		SimpleCharSet that = (SimpleCharSet) obj;
		return (this.chars.equals(that.chars));
	}

}
