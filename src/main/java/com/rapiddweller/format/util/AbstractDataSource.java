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

import com.rapiddweller.format.DataSource;

/**
 * Abstract implementation of the {@link DataSource} interface, which holds a 'type' attribute
 * and provides an empty close() method.
 * Created: 04.08.2011 09:52:03
 * @param <E> the type of data to iterate
 * @author Volker Bergmann
 * @since 0.6.0
 */
public abstract class AbstractDataSource<E> implements DataSource<E> {

  protected Class<E> type;

  protected AbstractDataSource(Class<E> type) {
    this.type = type;
  }

  @Override
  public Class<E> getType() {
    return type;
  }

  @Override
  public void close() {
    // nothing to do
  }

}
