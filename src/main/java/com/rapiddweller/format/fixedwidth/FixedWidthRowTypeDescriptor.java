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

import com.rapiddweller.common.ArrayBuilder;
import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.SyntaxError;
import com.rapiddweller.common.accessor.GraphAccessor;
import com.rapiddweller.common.mutator.AnyMutator;

import java.text.ParseException;
import java.text.ParsePosition;

/**
 * Row type support for fixed-width files: formatting, parsing and verification
 * for array- and bean-type data.
 * Created: 28.03.2014 15:18:23
 *
 * @author Volker Bergmann
 * @since 0.7.3
 */
public class FixedWidthRowTypeDescriptor {

  private final String name;
  private final FixedWidthColumnDescriptor[] columnDescriptors;
  private final int rowLength;

  /**
   * Instantiates a new Fixed width row type descriptor.
   *
   * @param name              the name
   * @param columnDescriptors the column descriptors
   */
  public FixedWidthRowTypeDescriptor(String name, FixedWidthColumnDescriptor[] columnDescriptors) {
    this.name = name;
    this.columnDescriptors = columnDescriptors;
    this.rowLength = totalLength(columnDescriptors);
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Get column descriptors fixed width column descriptor [ ].
   *
   * @return the fixed width column descriptor [ ]
   */
  public FixedWidthColumnDescriptor[] getColumnDescriptors() {
    return columnDescriptors;
  }

  /**
   * Format bean string.
   *
   * @param rowBean the row bean
   * @return the string
   */
  public String formatBean(Object rowBean) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < columnDescriptors.length; i++) {
      String path = columnDescriptors[i].getName();
      Object value = GraphAccessor.getValue(path, rowBean);
      builder.append(columnDescriptors[i].format(value));
    }
    return builder.toString();
  }

  /**
   * Format array string.
   *
   * @param columnValues the column values
   * @return the string
   */
  public String formatArray(Object... columnValues) {
    if (columnValues.length != columnDescriptors.length) {
      throw new IllegalArgumentException("Row type '" + name + "' expects " + columnValues.length + " array elements " +
          ", but found: " + columnValues.length);
    }
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < columnDescriptors.length; i++) {
      builder.append(columnDescriptors[i].format(columnValues[i]));
    }
    return builder.toString();
  }

  /**
   * Parse as array object [ ].
   *
   * @param row the row
   * @return the object [ ]
   */
  public Object[] parseAsArray(String row) {
    if (row.length() != rowLength) {
      throw new SyntaxError("Row of type '" + name + "' has illegal length. " +
          "Expected: " + rowLength + ", found: " + row.length(), "'" + row + "'");
    }
    ArrayBuilder<Object> builder = new ArrayBuilder<Object>(Object.class);
    ParsePosition pos = new ParsePosition(0);
    for (int i = 0; i < columnDescriptors.length; i++) {
      FixedWidthColumnDescriptor columnDescriptor = columnDescriptors[i];
      int endIndex = pos.getIndex() + columnDescriptor.getWidth();
      String cellContent = row.substring(pos.getIndex(), endIndex);
      try {
        Object cellObject = columnDescriptor.parse(cellContent);
        builder.add(cellObject);
      } catch (ParseException e) {
        throw new SyntaxError("Error parsing column '" + descriptorName(columnDescriptor, i) + "'. " + e.getMessage(), cellContent);
      }
      pos.setIndex(endIndex);
    }
    return builder.toArray();
  }

  /**
   * Parse as bean t.
   *
   * @param <T>       the type parameter
   * @param row       the row
   * @param beanClass the bean class
   * @return the t
   */
  public <T> T parseAsBean(String row, Class<T> beanClass) {
    if (row.length() != rowLength) {
      throw new SyntaxError("Row of type '" + name + "' has illegal length. " +
          "Expected: " + rowLength + ", found: " + row.length(), "'" + row + "'");
    }
    T bean = BeanUtil.newInstance(beanClass);
    ParsePosition pos = new ParsePosition(0);
    for (FixedWidthColumnDescriptor columnDescriptor : columnDescriptors) {
      int endIndex = pos.getIndex() + columnDescriptor.getWidth();
      String cellContent = row.substring(pos.getIndex(), endIndex);
      try {
        Object cellObject = columnDescriptor.parse(cellContent);
        AnyMutator.setValue(bean, columnDescriptor.getName(), cellObject, true, true);
      } catch (ParseException e) {
        throw new SyntaxError("Error parsing column '" + columnDescriptor + "'. " + e.getMessage(), cellContent);
      }
      pos.setIndex(endIndex);
    }
    return bean;
  }


  // private helpers -------------------------------------------------------------------------------------------------

  private static String descriptorName(FixedWidthColumnDescriptor descriptor, int i) {
    return (descriptor.getName() != null ? descriptor.getName() : String.valueOf(i));
  }

  private static int totalLength(FixedWidthColumnDescriptor[] descriptors) {
    int result = 0;
    for (FixedWidthColumnDescriptor descriptor : descriptors) {
      result += descriptor.getWidth();
    }
    return result;
  }

}
