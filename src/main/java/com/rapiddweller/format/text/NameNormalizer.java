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
package com.rapiddweller.format.text;

import com.rapiddweller.common.StringUtil;

/**
 * Normalizes names in capitalization and spacing. 
 * This includes trimming left and right, internal 
 * space normalization and starting each single word 
 * with a capital letter.
 * 
 * Created at 20.11.2008 19:39:20
 * @since 0.4.6
 * @author Volker Bergmann
 */

public class NameNormalizer extends NormalizeSpaceConverter {
	
    @Override
	public String convert(String sourceValue) {
        return StringUtil.normalizeName(StringUtil.normalizeSpace(sourceValue));
    }
}
