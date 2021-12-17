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
import com.rapiddweller.format.DataIterator;
import com.rapiddweller.format.DataSource;

import java.io.Closeable;

/**
 * Provides {@link DataSource}-style access to a Java-SDK-{@link Iterable}.<br/><br/>
 * Created: 24.07.2011 11:07:04
 * @param <E> the type of data to iterate
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class DataSourceFromIterable<E> extends AbstractDataSource<E> {

  protected Iterable<E> source;

  public DataSourceFromIterable(Iterable<E> source, Class<E> type) {
    super(type);
    this.source = source;
  }

  @Override
  public DataIterator<E> iterator() {
    return new DataIteratorFromJavaIterator<>(source.iterator(), type);
  }

  @Override
  public void close() {
    if (source instanceof Closeable) {
      IOUtil.close((Closeable) source);
    }
    super.close();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[" + source + "]";
  }

}
