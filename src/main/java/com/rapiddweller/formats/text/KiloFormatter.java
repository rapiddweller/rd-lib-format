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

import java.text.DecimalFormatSymbols;
import java.util.Locale;

import com.rapiddweller.commons.ArrayUtil;

/**
 * Renders a numerical value applying quantitative Symbols like 5 K for 5000.
 * Created: 13.12.2012 14:02:39
 * @since 0.5.21
 * @author Volker Bergmann
 */
public class KiloFormatter {
	
	public static final int BASE_1000 = 1000;
	public static final int BASE_1024 = 1024;
	
	public static final int DEFAULT_BASE = BASE_1000;
	public static final String[] SYMBOLS = { "", "K", "M", "G", "T", "E" };
	
	private final int base;
	private final char decimalSeparator;

	public KiloFormatter(int base) {
		this(base, Locale.getDefault());
	}

	public KiloFormatter(int base, Locale locale) {
		this.base = base;
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(locale);
		this.decimalSeparator = symbols.getDecimalSeparator();
	}

	public String format(long value) {
		return convert(value, base);
	}
	
	public String convert(long value, int base) {
		long threshold = 1;
		for (int i = 0; i < SYMBOLS.length; i++) {
			if (value < threshold * base)
				return formatNumber(value, threshold, SYMBOLS[i]);
			threshold *= base;
		}
		return formatNumber(value, threshold / base, ArrayUtil.lastElementOf(SYMBOLS));
	}

	private String formatNumber(long value, long threshold, String symbol) {
		long prefix = value / threshold;
		long postfix = (value - prefix * threshold + threshold / 20) * 10 / threshold;
		if (postfix >= 10) {
			prefix++;
			postfix -= 10;
		}
		StringBuilder builder = new StringBuilder();
		builder.append(prefix);
		if (postfix != 0 && prefix / 10 == 0)
			builder.append(decimalSeparator).append(postfix);
		if (symbol.length() > 0)
			builder.append(' ').append(symbol);
		return builder.toString();
	}
	
}
