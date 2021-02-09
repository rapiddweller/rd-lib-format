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

import com.rapiddweller.common.Filter;
import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.DataIterator;

/**
 * {@link DataIterator} proxy which applies a {@link Filter} to the data provides by its source.
 * Created: 24.07.2011 10:19:41
 *
 * @param <E> the type of data to iterate
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class FilteringDataIterator<E> extends DataIteratorProxy<E> {

  /**
   * The Filter.
   */
  protected Filter<E> filter;

  /**
   * Instantiates a new Filtering data iterator.
   *
   * @param source the source
   * @param filter the filter
   */
  public FilteringDataIterator(DataIterator<E> source, Filter<E> filter) {
    super(source);
    this.filter = filter;
  }

  @Override
  public DataContainer<E> next(DataContainer<E> wrapper) {
    DataContainer<E> result;
    do {
      result = source.next(wrapper);
    } while (result != null && !filter.accept(result.getData()));
    return result;
  }

}
