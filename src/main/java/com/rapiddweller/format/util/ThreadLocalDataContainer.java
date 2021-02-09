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

/**
 * Holds a {@link DataContainer} for each thread using the {@link ThreadLocal} mechanism.
 * Created: 24.07.2011 14:46:18
 *
 * @param <E> the type of data to hold
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class ThreadLocalDataContainer<E> extends ThreadLocal<DataContainer<E>> {

  @Override
  protected DataContainer<E> initialValue() {
    return new DataContainer<E>();
  }

  @Override
  public String toString() {
    return get().toString();
  }


}
