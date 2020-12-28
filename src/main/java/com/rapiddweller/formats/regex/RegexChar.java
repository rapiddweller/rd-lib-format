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
package com.rapiddweller.formats.regex;

import com.rapiddweller.commons.CharSet;

/**
 * Represents a constant character as part of a regular expression.
 * Created: 04.04.2014 18:22:39
 * @since 0.8.0
 * @author Volker Bergmann
 */

public class RegexChar extends RegexCharClass {
	
	private final char c;
	private final CharSet charSet;

	public RegexChar(char c) {
		this.c = c;
		this.charSet = new CharSet().add(c);
	}

	public char getChar() {
		return c;
	}

	@Override
	public CharSet getCharSet() {
		return charSet;
	}
	
	@Override
	public int hashCode() {
		return c;
	}

	@Override
	public int minLength() {
		return 1;
	}

	@Override
	public Integer maxLength() {
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		RegexChar that = (RegexChar) obj;
		return (this.c == that.c);
	}
	
	@Override
	public String toString() {
		return String.valueOf(c);
	}

}
