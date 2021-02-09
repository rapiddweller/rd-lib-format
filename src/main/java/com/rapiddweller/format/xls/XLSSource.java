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

package com.rapiddweller.format.xls;

import com.rapiddweller.format.DataIterator;
import com.rapiddweller.format.DataSource;
import com.rapiddweller.format.util.OrthogonalArrayIterator;

/**
 * Defined XLSSource as abstraction for XLS row or column data sources.
 * Created: 08.12.2011 16:51:08
 *
 * @author Volker Bergmann
 * @since 0.6.5
 */
public class XLSSource implements DataSource<Object[]> {

  private final String uri;
  private final boolean formatted;
  private final String emptyMarker;
  private final String nullMarker;
  private final boolean rowBased;

  /**
   * Instantiates a new Xls source.
   *
   * @param uri         the uri
   * @param formatted   the formatted
   * @param emptyMarker the empty marker
   * @param nullMarker  the null marker
   * @param rowBased    the row based
   */
  public XLSSource(String uri, boolean formatted, String emptyMarker, String nullMarker, boolean rowBased) {
    this.uri = uri;
    this.formatted = formatted;
    this.emptyMarker = emptyMarker;
    this.nullMarker = nullMarker;
    this.rowBased = rowBased;
  }

  @Override
  public Class<Object[]> getType() {
    return Object[].class;
  }

  @Override
  public DataIterator<Object[]> iterator() {
    try {
      XLSLineIterator iterator = new XLSLineIterator(uri);
      iterator.setFormatted(formatted);
      if (emptyMarker != null) {
        iterator.setEmptyMarker(emptyMarker);
      }
      if (nullMarker != null) {
        iterator.setNullMarker(nullMarker);
      }
      if (!rowBased) {
        return new OrthogonalArrayIterator<Object>(iterator);
      } else {
        return iterator;
      }
    } catch (Exception e) {
      throw new RuntimeException("Error creating iterator for " + uri, e);
    }
  }

  @Override
  public void close() {
    // nothing to do here
  }

}
