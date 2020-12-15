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

import com.rapiddweller.commons.ConversionException;
import com.rapiddweller.commons.StringUtil;
import com.rapiddweller.commons.converter.ThreadSafeConverter;

/**
 * Splits a String by a separator character
 * 
 * Created: 19.06.2007 07:33:03
 * @author Volker Bergmann
 */
public class SplitStringConverter extends ThreadSafeConverter<String, String[]> {

    private char separator;

    public SplitStringConverter(char separator) {
    	super(String.class, String[].class);
        this.separator = separator;
    }

    @Override
	public String[] convert(String sourceValue) throws ConversionException {
        return StringUtil.tokenize(sourceValue, separator);
    }
}
