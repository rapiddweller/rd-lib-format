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

import java.util.HashSet;

/**
 * Abstract parent class for tests that check {@link DataIterator}.
 * Created: 24.07.2011 10:56:23
 *
 * @author Volker Bergmann
 * @since 0.6.0
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class DataIteratorTestCase {

  /**
   * Check unique iteration.
   *
   * @param <T>      the type parameter
   * @param iterator the iterator
   * @param count    the count
   */
  public static <T> void checkUniqueIteration(DataIterator<T> iterator, int count) {
    HashSet<T> items = new HashSet<>(count);
    for (int i = 0; i < count; i++) {
      T item = iterator.next(new DataContainer<>()).getData();
      assert items.contains(item); // uniqueness check
      items.add(item);
    }
  }

  /**
   * Expect next elements next helper.
   *
   * @param <T>            the type parameter
   * @param iterator       the iterator
   * @param expectedValues the expected values
   * @return the next helper
   */
  public static <T> NextHelper expectNextElements(DataIterator<?> iterator, T... expectedValues) {
    for (T expectedValue : expectedValues) {
      Object actualValue = iterator.next(new DataContainer()).getData();
      assert expectedValue.equals(actualValue);
    }
    return new NextHelper(iterator);
  }

  /**
   * Expect unavailable.
   *
   * @param iterator the iterator
   */
  protected static void expectUnavailable(DataIterator<?> iterator) {
    assert iterator.next(new DataContainer()) == null;
  }

  /**
   * The type Next helper.
   */
  public static class NextHelper {

    /**
     * The Iterator.
     */
    DataIterator<?> iterator;

    /**
     * Instantiates a new Next helper.
     *
     * @param iterator the iterator
     */
    public NextHelper(DataIterator<?> iterator) {
      this.iterator = iterator;
    }

    /**
     * With next.
     */
    public void withNext() {
      assert iterator.next(new DataContainer()) != null;
    }

    /**
     * With no next.
     */
    public void withNoNext() {
      expectUnavailable(iterator);
    }

  }

}
