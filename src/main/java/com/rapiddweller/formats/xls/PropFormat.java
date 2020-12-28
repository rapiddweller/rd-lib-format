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

package com.rapiddweller.formats.xls;

/**
 * Describes how to format a bean property.
 * Created: 27.12.2015 07:55:04
 * @since 1.0.7
 * @author Volker Bergmann
 */

public class PropFormat {

	private final String name;
	private String pattern;
	
	public PropFormat(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPattern() {
		return pattern;
	}
	
	public PropFormat withDateFormat() {
		return withPattern("dd.MM.yyyy");
	}

	public PropFormat withIntFormat() {
		return withPattern("#,##0");
	}

	public PropFormat withPercentageFormat() {
		return withPattern("0%");
	}

	public PropFormat withDecimalFormat(int fractionDigits) {
		String pattern;
		switch (fractionDigits) {
			case 0: pattern = "#,##0";     break;
			case 1: pattern = "#,##0.0";   break;
			case 2: pattern = "#,##0.00";  break;
			case 3: pattern = "#,##0.000"; break;
			default: 
				StringBuilder builder = new StringBuilder("#,##0.");
				for (int i = 0; i < fractionDigits; i++)
					builder.append("0");
				pattern = builder.toString();
				break;
		}
		return withPattern(pattern);
	}

	public PropFormat withPattern(String pattern) {
		this.pattern = pattern;
		return this;
	}

	@Override
	public String toString() {
		return name + (pattern != null ? "[" + pattern + "]" : "");
	}
	
}
