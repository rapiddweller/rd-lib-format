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

import com.rapiddweller.commons.Assert;

/**
 * Represents a constant string which is part of a regular expression.
 * Created: 04.04.2014 16:09:47
 * @since 0.8.0
 * @author Volker Bergmann
 */

public class RegexString implements RegexPart {
	
	private final String string;
	
	public RegexString(String string) {
		Assert.notNull(string, "string");
		this.string = string;
	}
	
	public String getString() {
		return string;
	}
	
	@Override
	public int minLength() {
		return string.length();
	}
	
	@Override
	public Integer maxLength() {
		return string.length();
	}
	
	@Override
	public int hashCode() {
		return string.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		RegexString that = (RegexString) obj;
		return (this.string.equals(that.string));
	}
	
	@Override
	public String toString() {
		return string;
	}

}
