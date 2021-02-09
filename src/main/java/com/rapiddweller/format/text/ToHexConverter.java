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
import com.rapiddweller.common.Converter;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.converter.ThreadSafeConverter;

import java.text.MessageFormat;

/**
 * {@link Converter} that transforms an object into its hexadecimal representation.
 * It works with integral numbers, characters and strings.
 * Created: 29.10.2009 08:44:53
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class ToHexConverter extends ThreadSafeConverter<Object, String> {

  private final boolean upperCase;
  private final String pattern;
  private final int length;

  /**
   * Instantiates a new To hex converter.
   */
  public ToHexConverter() {
    this(false);
  }

  /**
   * Instantiates a new To hex converter.
   *
   * @param upperCase the upper case
   */
  public ToHexConverter(boolean upperCase) {
    this(upperCase, null);
  }

  /**
   * Instantiates a new To hex converter.
   *
   * @param upperCase the upper case
   * @param pattern   the pattern
   */
  public ToHexConverter(boolean upperCase, String pattern) {
    this(upperCase, pattern, -1);
  }

  /**
   * Instantiates a new To hex converter.
   *
   * @param upperCase the upper case
   * @param pattern   the pattern
   * @param length    the length
   */
  public ToHexConverter(boolean upperCase, String pattern, int length) {
    super(Object.class, String.class);
    this.upperCase = upperCase;
    this.pattern = pattern;
    this.length = length;
  }

  @Override
  public String convert(Object sourceValue) throws ConversionException {
    if (sourceValue == null) {
      return null;
    }
    Class<?> sourceType = sourceValue.getClass();
    if (sourceType == Long.class) {
      return convertLong((Long) sourceValue, upperCase, pattern, length);
    } else if (sourceType == Integer.class) {
      return convertInt((Integer) sourceValue, upperCase, pattern, length);
    } else if (sourceType == Short.class) {
      return convertShort((Short) sourceValue, upperCase, pattern, length);
    } else if (sourceType == Byte.class) {
      return convertByte((Byte) sourceValue, upperCase, pattern, length);
    } else if (sourceType == Character.class) {
      return convertChar((Character) sourceValue, upperCase, pattern, length);
    } else if (sourceType == String.class) {
      return convertString((String) sourceValue, upperCase, pattern, length);
    } else {
      throw new IllegalArgumentException("Can't render '" + sourceType + "' in hex format.");
    }
  }

  /**
   * Convert long string.
   *
   * @param sourceValue the source value
   * @param upperCase   the upper case
   * @param pattern     the pattern
   * @param length      the length
   * @return the string
   */
  public static String convertLong(Long sourceValue, boolean upperCase, String pattern, int length) {
    String base = Long.toHexString(sourceValue);
    return postProcess(base, upperCase, pattern, length);
  }

  /**
   * Convert int string.
   *
   * @param sourceValue the source value
   * @param upperCase   the upper case
   * @param pattern     the pattern
   * @param length      the length
   * @return the string
   */
  public static String convertInt(int sourceValue, boolean upperCase, String pattern, int length) {
    String base = Integer.toHexString(sourceValue);
    return postProcess(base, upperCase, pattern, length);
  }

  /**
   * Convert short string.
   *
   * @param sourceValue the source value
   * @param upperCase   the upper case
   * @param pattern     the pattern
   * @param length      the length
   * @return the string
   */
  public static String convertShort(short sourceValue, boolean upperCase, String pattern, int length) {
    String base = Integer.toHexString(sourceValue);
    if (base.length() == 8) {
      base = base.substring(4);
    }
    return postProcess(base, upperCase, pattern, length);
  }

  /**
   * Convert byte string.
   *
   * @param sourceValue the source value
   * @param upperCase   the upper case
   * @param pattern     the pattern
   * @param length      the length
   * @return the string
   */
  public static String convertByte(byte sourceValue, boolean upperCase, String pattern, int length) {
    String base = Integer.toHexString(sourceValue);
    if (base.length() == 8) {
      base = base.substring(6);
    }
    return postProcess(base, upperCase, pattern, length);
  }

  /**
   * Convert char string.
   *
   * @param sourceValue the source value
   * @param upperCase   the upper case
   * @param pattern     the pattern
   * @param length      the length
   * @return the string
   */
  public static String convertChar(Character sourceValue, boolean upperCase, String pattern, int length) {
    String base = convertInt(sourceValue, upperCase, null, 2);
    return postProcess(base, upperCase, pattern, length);
  }

  /**
   * Convert string string.
   *
   * @param sourceValue the source value
   * @param upperCase   the upper case
   * @param pattern     the pattern
   * @param length      the length
   * @return the string
   */
  public static String convertString(String sourceValue, boolean upperCase, String pattern, int length) {
    StringBuilder builder = new StringBuilder(sourceValue.length() * 2);
    for (int i = 0; i < sourceValue.length(); i++) {
      builder.append(convertChar(sourceValue.charAt(i), upperCase, null, 2));
    }
    return postProcess(builder.toString(), upperCase, pattern, length);
  }

  private static String postProcess(String base, boolean upperCase, String pattern, int length) {
    if (upperCase) {
      base = base.toUpperCase();
    }
    if (length > 0) {
      base = StringUtil.padLeft(base, length, '0');
    }
    if (pattern != null) {
      base = MessageFormat.format(pattern, base);
    }
    return base;
  }

}
