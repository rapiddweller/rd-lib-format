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

import com.rapiddweller.common.Converter;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.DataIterator;

import java.io.Closeable;

/**
 * {@link DataIterator} proxy which applies a {@link Converter} to the data provided by its source.
 * Created: 24.07.2011 09:52:35
 *
 * @param <S> the type of data to iterate from the source
 * @param <T> the type of data to provide to the client
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class ConvertingDataIterator<S, T> extends DataIteratorAdapter<S, T> {

  /**
   * The Converter.
   */
  protected Converter<S, T> converter;

  /**
   * Instantiates a new Converting data iterator.
   *
   * @param source    the source
   * @param converter the converter
   */
  public ConvertingDataIterator(DataIterator<S> source, Converter<S, T> converter) {
    super(source);
    this.converter = converter;
  }

  @Override
  public Class<T> getType() {
    return converter.getTargetType();
  }

  @Override
  public synchronized DataContainer<T> next(DataContainer<T> container) {
    DataContainer<S> sourceWrapper = nextOfSource();
    if (sourceWrapper == null) {
      return null;
    }
    return container.setData(converter.convert(sourceWrapper.getData()));
  }

  @Override
  public void close() {
    if (converter instanceof Closeable) {
      IOUtil.close((Closeable) converter);
    }
    super.close();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[source:" + source + ", converter:" + converter + "]";
  }

}
