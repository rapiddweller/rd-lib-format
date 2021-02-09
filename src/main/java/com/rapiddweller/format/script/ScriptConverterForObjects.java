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

package com.rapiddweller.format.script;

import com.rapiddweller.common.Context;
import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.Converter;
import com.rapiddweller.common.converter.ConverterWrapper;

/**
 * {@link Converter} can recognize and resolve script expressions in {@link String} values,
 * forwarding values of other Java type 'as is'.
 * Created: 07.08.2011 08:27:27
 *
 * @author Volker Bergmann
 * @since 0.5.9
 */
public class ScriptConverterForObjects extends ConverterWrapper<String, Object>
    implements Converter<Object, Object> {

  /**
   * Instantiates a new Script converter for objects.
   *
   * @param context the context
   */
  public ScriptConverterForObjects(Context context) {
    super(new ScriptConverterForStrings(context));
  }

  @Override
  public Class<Object> getSourceType() {
    return Object.class;
  }

  @Override
  public Class<Object> getTargetType() {
    return Object.class;
  }

  @Override
  public Object convert(Object sourceValue) throws ConversionException {
    // I might iterate through mixed sets of strings and numbers (e.g. from an XLS file)...
    if (sourceValue instanceof String) //...so I only apply script evaluation on strings
    {
      return realConverter.convert((String) sourceValue);
    } else {
      return sourceValue;
    }
  }

}
