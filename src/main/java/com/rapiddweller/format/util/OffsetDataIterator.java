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

/**
 * Forwards the data of another {@link DataIterator}, swallowing the first elements
 * provided by its {@link #next(DataContainer)} method.
 * Created: 18.09.2014 10:01:00
 *
 * @param <E> the type of data to iterate
 * @author Volker Bergmann
 * @since 1.0
 */
public class OffsetDataIterator<E> extends DataIteratorProxy<E> {

  /**
   * Instantiates a new Offset data iterator.
   *
   * @param source the source
   * @param offset the offset
   */
  public OffsetDataIterator(DataIterator<E> source, int offset) {
    super(source);
    // consume the first 'offset' elements of the source
    DataContainer<E> container = new DataContainer<>();
    for (int i = 0; i < offset; i++) {
      source.next(container);
    }
  }

}
