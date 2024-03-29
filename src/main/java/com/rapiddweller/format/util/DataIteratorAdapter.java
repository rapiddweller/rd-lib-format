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

import com.rapiddweller.common.IOUtil;
import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.DataIterator;

/**
 * Adapter for a {@link DataIterator}.
 * Created: 24.07.2011 09:53:49
 *
 * @param <S> the type of data to iterate from the source
 * @param <T> the type of data to provide to the client
 * @author Volker Bergmann
 * @since 0.6.0
 */
public abstract class DataIteratorAdapter<S, T> implements DataIterator<T> {

  /**
   * The Source.
   */
  protected DataIterator<S> source;
  private final ThreadLocalDataContainer<S> sourceContainerProvider;

  /**
   * Instantiates a new Data iterator adapter.
   *
   * @param source the source
   */
  public DataIteratorAdapter(DataIterator<S> source) {
    this.source = source;
    this.sourceContainerProvider = new ThreadLocalDataContainer<>();
  }

  @Override
  public void close() {
    IOUtil.close(source);
  }

  /**
   * Next of source data container.
   *
   * @return the data container
   */
  protected DataContainer<S> nextOfSource() {
    return source.next(getSourceContainer());
  }

  /**
   * Gets source container.
   *
   * @return the source container
   */
  protected DataContainer<S> getSourceContainer() {
    return sourceContainerProvider.get();
  }

}
