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
 * Utility class for {@link DataIterator}-related functionality.
 * Created: 24.07.2011 16:15:48
 *
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class DataUtil {

  private DataUtil() {
  }

  /**
   * Next not null data t.
   *
   * @param <T>      the type parameter
   * @param iterator the iterator
   * @return the t
   */
  public static <T> T nextNotNullData(DataIterator<T> iterator) {
    DataContainer<T> container = iterator.next(new DataContainer<T>());
    if (container == null) {
      throw new IllegalArgumentException("iterator is unavailable though a value is expected: " + iterator);
    }
    return container.getData();
  }

}
