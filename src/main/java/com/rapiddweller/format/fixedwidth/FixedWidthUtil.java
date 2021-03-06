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

package com.rapiddweller.format.fixedwidth;

import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.ParseUtil;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.SyntaxError;
import com.rapiddweller.common.format.Alignment;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Provides utility methods for processing flat files.
 * <p>
 * Created: 03.09.2007 23:39:57
 *
 * @author Volker Bergmann
 */
public class FixedWidthUtil {

  /**
   * Parse bean columns spec fixed width row type descriptor.
   *
   * @param properties  the properties
   * @param rowTypeName the row type name
   * @param nullString  the null string
   * @param locale      the locale
   * @return the fixed width row type descriptor
   * @throws ParseException the parse exception
   */
  public static FixedWidthRowTypeDescriptor parseBeanColumnsSpec(String properties, String rowTypeName, String nullString, Locale locale)
      throws ParseException {
    if (properties == null) {
      return null;
    }
    String[] propertyFormats = StringUtil.tokenize(properties, ',');
    FixedWidthColumnDescriptor[] columns = new FixedWidthColumnDescriptor[propertyFormats.length];
    for (int i = 0; i < propertyFormats.length; i++) {
      String propertyFormat = propertyFormats[i];
      int lbIndex = propertyFormat.indexOf('[');
      if (lbIndex < 0) {
        throw new ConfigurationError("'[' expected in property format descriptor '" + propertyFormat + "'");
      }
      int rbIndex = propertyFormat.indexOf(']');
      if (rbIndex < 0) {
        throw new ConfigurationError("']' expected in property format descriptor '" + propertyFormat + "'");
      }
      String propertyName = propertyFormat.substring(0, lbIndex);
      String formatSpec = propertyFormat.substring(lbIndex + 1, rbIndex);
      FixedWidthColumnDescriptor descriptor = parseColumnFormat(formatSpec, nullString, locale);
      descriptor.setName(propertyName);
      columns[i] = descriptor;
    }
    return new FixedWidthRowTypeDescriptor(rowTypeName, columns);
  }

  /**
   * Parse array columns spec fixed width row type descriptor.
   *
   * @param columnsSpec the columns spec
   * @param rowTypeName the row type name
   * @param nullString  the null string
   * @param locale      the locale
   * @return the fixed width row type descriptor
   * @throws ParseException the parse exception
   */
  public static FixedWidthRowTypeDescriptor parseArrayColumnsSpec(String columnsSpec, String rowTypeName, String nullString, Locale locale)
      throws ParseException {
    if (columnsSpec == null) {
      return null;
    }
    String[] columnFormats = StringUtil.tokenize(columnsSpec, ',');
    FixedWidthColumnDescriptor[] columns = new FixedWidthColumnDescriptor[columnFormats.length];
    for (int i = 0; i < columnFormats.length; i++) {
      columns[i] = parseColumnFormat(columnFormats[i], nullString, locale);
    }
    return new FixedWidthRowTypeDescriptor(rowTypeName, columns);
  }

  /**
   * Parse column format fixed width column descriptor.
   *
   * @param formatSpec the format spec
   * @param nullString the null string
   * @param locale     the locale
   * @return the fixed width column descriptor
   * @throws ParseException the parse exception
   */
  public static FixedWidthColumnDescriptor parseColumnFormat(String formatSpec, String nullString, Locale locale) throws ParseException {
    switch (formatSpec.charAt(0)) {
      case 'D':
        return parseDatePattern(formatSpec, nullString, locale);
      case 'N':
        return parseNumberPattern(formatSpec, nullString, locale);
      default:
        return parseWidthFormat(formatSpec, nullString);
    }
  }


  // private helpers -------------------------------------------------------------------------------------------------

  private static FixedWidthColumnDescriptor parseDatePattern(String formatSpec, String nullString, Locale locale) {
    if (!formatSpec.startsWith("D")) {
      throw new SyntaxError("Illegal date/time pattern", formatSpec);
    }
    String pattern = formatSpec.substring(1);
    Format format = new SimpleDateFormat(pattern, DateFormatSymbols.getInstance(locale));
    return new FixedWidthColumnDescriptor(null, format, nullString);
  }

  private static FixedWidthColumnDescriptor parseNumberPattern(String formatSpec, String nullString, Locale locale) {
    if (!formatSpec.startsWith("N")) {
      throw new SyntaxError("Illegal number pattern", formatSpec);
    }
    String pattern = formatSpec.substring(1);
    Format format = new DecimalFormat(pattern, DecimalFormatSymbols.getInstance(locale));
    return new FixedWidthColumnDescriptor(null, format, nullString);
  }

  private static FixedWidthColumnDescriptor parseWidthFormat(String formatSpec, String nullString) throws ParseException {
    ParsePosition pos = new ParsePosition(0);

    // parse width
    int width = (int) ParseUtil.parseNonNegativeInteger(formatSpec, pos);

    // parse fractionDigits
    NumberFormat format = null;
    int minFractionDigits = 0;
    int maxFractionDigits = 2;
    if (pos.getIndex() < formatSpec.length() && formatSpec.charAt(pos.getIndex()) == '.') {
      pos.setIndex(pos.getIndex() + 1);
      minFractionDigits = (int) ParseUtil.parseNonNegativeInteger(formatSpec, pos);
      maxFractionDigits = minFractionDigits;
      format = DecimalFormat.getInstance(Locale.US);
      format.setMinimumFractionDigits(minFractionDigits);
      format.setMaximumFractionDigits(maxFractionDigits);
      format.setGroupingUsed(false);
    }

    // parse alignment
    Alignment alignment = Alignment.LEFT;
    if (pos.getIndex() < formatSpec.length()) {
      char alignmentCode = formatSpec.charAt(pos.getIndex());
      switch (alignmentCode) {
        case 'l':
          alignment = Alignment.LEFT;
          break;
        case 'r':
          alignment = Alignment.RIGHT;
          break;
        case 'c':
          alignment = Alignment.CENTER;
          break;
        default:
          throw new ConfigurationError("Illegal alignment code '" + alignmentCode + "' in format descriptor '" + formatSpec + "'");
      }
      pos.setIndex(pos.getIndex() + 1);
    }
    char padChar = ' ';
    if (pos.getIndex() < formatSpec.length()) {
      padChar = formatSpec.charAt(pos.getIndex());
      pos.setIndex(pos.getIndex() + 1);
    }
    if (pos.getIndex() != formatSpec.length()) {
      throw new SyntaxError("Illegal column format", formatSpec);
    }
    return new FixedWidthColumnDescriptor(null, format, nullString, width, alignment, padChar);
  }

}
