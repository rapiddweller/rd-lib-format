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

import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.converter.ThreadSafeConverter;

/**
 * Converter that normalizes white space on strings.
 * <p>
 * Created: 19.06.2007 07:36:32
 */
public class NormalizeSpaceConverter extends ThreadSafeConverter<String, String> {

  /**
   * Instantiates a new Normalize space converter.
   */
  public NormalizeSpaceConverter() {
    super(String.class, String.class);
  }

  @Override
  public String convert(String sourceValue) throws ConversionException {
    return StringUtil.normalizeSpace(sourceValue);
  }

}
