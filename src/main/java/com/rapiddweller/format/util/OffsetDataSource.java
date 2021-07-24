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

package com.rapiddweller.format.util;

import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.DataIterator;
import com.rapiddweller.format.DataSource;

/**
 * {@link DataSource} proxy which provides a subset of the source's data defined by an offset.
 * Created: 24.07.2011 09:59:24
 *
 * @param <E> the type of data to iterate
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class OffsetDataSource<E> extends DataSourceProxy<E> {

  /**
   * The Offset.
   */
  protected int offset;

  /**
   * Instantiates a new Offset data source.
   *
   * @param source the source
   * @param offset the offset
   */
  public OffsetDataSource(DataSource<E> source, int offset) {
    super(source);
    this.offset = offset;
  }

  @Override
  public DataIterator<E> iterator() {
    DataContainer<E> container = new DataContainer<>();
    DataIterator<E> result = super.iterator();
    for (int i = 0; i < offset; i++) {
      result.next(container);
    }
    return result;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + '[' + offset + ", " + source + ']';
  }

}
