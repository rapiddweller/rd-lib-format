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
package com.rapiddweller.formats.text;

import com.rapiddweller.commons.StringUtil;

/**
 * Behaves like a {@link StringBuilder}, but normalizes all inserted line separators to a default.
 * Created: 05.04.2010 08:54:42
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class LFNormalizingStringBuilder {

	private final StringBuilder builder;
	private final String lineSeparator;
	
	public LFNormalizingStringBuilder(String lineSeparator) {
	    this.lineSeparator = lineSeparator;
	    builder = new StringBuilder();
    }

    public String getLineSeparator() {
	    return lineSeparator;
    }
    
	public LFNormalizingStringBuilder append(String text) {
		builder.append(StringUtil.normalizeLineSeparators(text, lineSeparator));
		return this;
	}
	
	public LFNormalizingStringBuilder append(char c) {
		if (c != '\r' && c != '\n')
			builder.append(c);
		else
			builder.append(lineSeparator);
		return this;
	}
	
	@Override
	public String toString() {
	    return builder.toString();
	}
	
}
