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

import com.rapiddweller.common.format.Alignment;
import com.rapiddweller.common.format.NullSafeFormat;

import java.awt.*;
import java.text.DecimalFormat;

/**
 * Provides style information for rendering numbers.
 * Created: 25.12.2015 08:18:03
 * @since 1.0.7
 * @author Volker Bergmann
 */

public class StyledNumberFormat extends StyledFormat {

	private static final long serialVersionUID = 1L;
	
	private final boolean negativeRed;
	
	public StyledNumberFormat(String pattern, boolean negativeRed) {
		super(new NullSafeFormat(new DecimalFormat(pattern), ""));
		this.negativeRed = negativeRed;
	}

	@Override
	public Alignment getAlignment() {
		return Alignment.RIGHT;
	}
	
	@Override
	public Color getForegroundColor(Object value, boolean isSelected) {
		return (negativeRed && value instanceof Number && ((Number) value).doubleValue() < 0 ? Color.RED : null);
	}

	@Override
	public Color getBackgroundColor(Object value, boolean isSelected) {
		return null;
	}

}
