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

package com.rapiddweller.format.style;

import java.awt.Color;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

import com.rapiddweller.common.format.Alignment;

/**
 * Provides style information for rendering data.
 * Created: 25.12.2015 08:10:16
 * @since 1.0.7
 * @author Volker Bergmann
 */

public abstract class StyledFormat extends Format {

	private static final long serialVersionUID = 1L;

	private final Format realFormat;
	
	public StyledFormat(Format realFormat) {
		this.realFormat = realFormat;
	}
	
	public abstract Alignment getAlignment();

	@Override
	public StringBuffer format(Object object, StringBuffer toAppendTo, FieldPosition pos) {
		return realFormat.format(object, toAppendTo, pos);
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		return realFormat.parseObject(source, pos);
	}

	public abstract Color getForegroundColor(Object value, boolean isSelected);
	
	public abstract Color getBackgroundColor(Object value, boolean isSelected);
	
}
