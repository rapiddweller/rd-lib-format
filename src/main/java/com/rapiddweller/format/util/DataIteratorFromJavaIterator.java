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

import java.io.Closeable;
import java.util.Iterator;

/**
 * Provides {@link DataIterator}-style access to a Java {@link Iterator}.
 * Created: 24.07.2011 11:09:16
 *
 * @param <E> the type of data to iterate
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class DataIteratorFromJavaIterator<E> implements DataIterator<E> {

  /**
   * The Source.
   */
  protected Iterator<E> source;
  /**
   * The Type.
   */
  protected Class<E> type;

  /**
   * Instantiates a new Data iterator from java iterator.
   *
   * @param source the source
   * @param type   the type
   */
  public DataIteratorFromJavaIterator(Iterator<E> source, Class<E> type) {
    this.source = source;
    this.type = type;
  }

  @Override
  public Class<E> getType() {
    return type;
  }

  @Override
  public DataContainer<E> next(DataContainer<E> wrapper) {
    return (source.hasNext() ? wrapper.setData(source.next()) : null);
  }

  @Override
  public void close() {
    if (source instanceof Closeable) {
      IOUtil.close((Closeable) source);
    }
  }

}
