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

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Provides style information for rendering date objects.
 * Created: 25.12.2015 10:48:27
 *
 * @author Volker Bergmann
 * @since 1.0.7
 */
public class StyledDateFormat extends StyledFormat {

  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new Styled date format.
   */
  public StyledDateFormat() {
    this(DateFormat.getDateInstance());
  }

  /**
   * Instantiates a new Styled date format.
   *
   * @param pattern the pattern
   * @param locale  the locale
   */
  public StyledDateFormat(String pattern, Locale locale) {
    this(new SimpleDateFormat(pattern, locale));
  }

  /**
   * Instantiates a new Styled date format.
   *
   * @param format the format
   */
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
