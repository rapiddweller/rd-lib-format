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

package com.rapiddweller.formats.style;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.rapiddweller.commons.format.Alignment;
import com.rapiddweller.commons.format.NullSafeFormat;

/**
 * Provides style information for rendering date objects.
 * Created: 25.12.2015 10:48:27
 * @since 1.0.7
 * @author Volker Bergmann
 */

public class StyledDateFormat extends StyledFormat {

	private static final long serialVersionUID = 1L;

	public StyledDateFormat() {
		this(DateFormat.getDateInstance());
	}

	public StyledDateFormat(String pattern, Locale locale) {
		this(new SimpleDateFormat(pattern, locale));
	}

	public StyledDateFormat(DateFormat format) {
		super(new NullSafeFormat(format, null));
	}

	@Override
	public Alignment getAlignment() {
		return Alignment.RIGHT;
	}

	@Override
	public Color getForegroundColor(Object value, boolean isSelected) {
		return null;
	}

	@Override
	public Color getBackgroundColor(Object value, boolean isSelected) {
		return null;
	}

}
