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

import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.common.format.Alignment;
import com.rapiddweller.common.format.PadFormat;

import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Describes a column of a flat file.<br/><br/>
 * Created: 07.06.2007 13:06:39
 * @author Volker Bergmann
 */
public class FixedWidthColumnDescriptor {

  private String name;
  private final int width;
  private final PadFormat format;


  // constructors ----------------------------------------------------------------------------------------------------

  public FixedWidthColumnDescriptor(int width, Alignment alignment) {
    this((String) null, width, alignment, ' ');
  }

  public FixedWidthColumnDescriptor(String name, int width, Alignment alignment) {
    this(name, width, alignment, ' ');
  }

  public FixedWidthColumnDescriptor(int width, Alignment alignment, char padChar) {
    this((String) null, width, alignment, padChar);
  }

  public FixedWidthColumnDescriptor(String name, int width, Alignment alignment, char padChar) {
    this(name, null, "", width, alignment, padChar);
  }

  public FixedWidthColumnDescriptor(Format format, int width, Alignment alignment, char padChar) {
    this(null, format, "", width, alignment, padChar);
  }

  public FixedWidthColumnDescriptor(String name, Format format, String nullString) {
    this(name, format, nullString, formatWidth(format), Alignment.LEFT, ' ');
  }

  public FixedWidthColumnDescriptor(String name, Format format, String nullString, int width, Alignment alignment, char padChar) {
    this.name = name;
    this.width = width;
    this.format = new PadFormat(format, nullString, width, alignment, padChar);
  }


  // properties ------------------------------------------------------------------------------------------------------

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getWidth() {
    return width;
  }

  public Format getFormat() {
    return format;
  }


  // functional interface --------------------------------------------------------------------------------------------

  public String format(Object object) {
    try {
      return format.format(object);
    } catch (IllegalArgumentException e) {
      // enrich error message from PadFormat with the name of the column - if available
      if (StringUtil.isEmpty(this.name)) {
        throw ExceptionFactory.getInstance().illegalArgument("Error parsing column '" + this.name + "'. ", e);
      } else {
        throw e;
      }
    }
  }

  public Object parse(String text) throws ParseException {
    try {
      return format.parseObject(text);
    } catch (IllegalArgumentException e) {
      // enrich error message from PadFormat with the name of the column - if available
      if (StringUtil.isEmpty(this.name)) {
        throw ExceptionFactory.getInstance().illegalArgument("Error parsing column '" + this.name + "'. ", e);
      } else {
        throw e;
      }
    }
  }


  // private helpers -------------------------------------------------------------------------------------------------

  private static int formatWidth(Format format) {
    if (format instanceof DateFormat) {
      return format.format(new Date()).length();
    } else if (format instanceof NumberFormat) {
      return format.format(0).length();
    } else {
      return 0;
    }
  }


  // java.lang.Object overrides --------------------------------------------------------------------------------------

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((format == null) ? 0 : format.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final FixedWidthColumnDescriptor other = (FixedWidthColumnDescriptor) obj;
    if (format == null) {
      if (other.format != null) {
        return false;
      }
    } else if (!format.equals(other.format)) {
      return false;
    }
    if (name == null) {
      return other.name == null;
    } else {
      return name.equals(other.name);
    }
  }

  @Override
  public String toString() {
    return name + '[' + format + ']';
  }

}
