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

package com.rapiddweller.format;

import java.io.Closeable;

/**
 * Iterates through data. Implementors are expected to be thread-safe.
 * Created: 24.07.2011 08:49:16
 *
 * @param <E> the type of iterated data
 * @author Volker Bergmann
 * @since 0.6.0
 */
public interface DataIterator<E> extends Closeable {

  /**
   * Returns the type of the iterated elements.
   *
   * @return the type of iterated data
   */
  Class<E> getType();

  /**
   * Returns the container with the next data element if available, otherwise null.
   *
   * @param container a {@link DataContainer} to receive the iterated data
   * @return a {@link DataContainer} that holds the next element, or null if none is available
   */
  DataContainer<E> next(DataContainer<E> container);

  /**
   * Closes the iterator.
   */
  @Override
  void close();

}
