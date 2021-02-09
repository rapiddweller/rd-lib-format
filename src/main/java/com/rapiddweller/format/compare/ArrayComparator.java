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

package com.rapiddweller.format.compare;

import com.rapiddweller.common.Assert;
import com.rapiddweller.common.ProgrammerError;

/**
 * Compares arrays of objects using a {@link ComparisonModel}.
 * Created: 20.11.2013 17:40:38
 *
 * @author Volker Bergmann
 * @since 1.0.5
 */
public class ArrayComparator {

  private static final int IDENTICAL = 0;
  private static final int CHANGED = 1;
  private static final int REMOVED = 2;
  private static final int ADDED = 3;

  /**
   * Compare array comparison result.
   *
   * @param array1         the array 1
   * @param array2         the array 2
   * @param model          the model
   * @param parentLocator1 the parent locator 1
   * @param parentLocator2 the parent locator 2
   * @param diffFactory    the diff factory
   * @return the array comparison result
   */
  public static ArrayComparisonResult compare(Object[] array1, Object[] array2, ComparisonModel model,
                                              String parentLocator1, String parentLocator2, DiffFactory diffFactory) {
    return new ArrayComparator(array1, array2, model, parentLocator1, parentLocator2, diffFactory).compare();
  }

  private final String parentLocator1;
  private final String parentLocator2;
  private final Object[] array1;
  private final Object[] array2;
  private final ComparisonModel model;
  private final Match[] matches1;
  private final Match[] matches2;
  private final DiffFactory diffFactory;

  private ArrayComparator(Object[] array1, Object[] array2, ComparisonModel model, String parentLocator1, String parentLocator2,
                          DiffFactory diffFactory) {
    this.array1 = array1;
    this.array2 = array2;
    this.model = model;
    this.parentLocator1 = parentLocator1;
    this.parentLocator2 = parentLocator2;
    this.matches1 = new Match[array1.length];
    this.matches2 = new Match[array2.length];
    this.diffFactory = diffFactory;
  }

  private ArrayComparisonResult compare() {

    // 1. step: match identical elements
    for (int i1 = 0; i1 < array1.length; i1++) {
      Object e1 = array1[i1];
      if (i1 < array2.length && model.equal(e1, array2[i1])) {
        // identical element found at the same index
        matches1[i1] = matches2[i1] = new Match(i1, i1, IDENTICAL);
      } else {
        int i2 = indexOf(e1, array2, matches2);
        if (i2 >= 0) {
          // identical element found at different index
          matches1[i1] = matches2[i2] = new Match(i1, i2, IDENTICAL);
        }
      }
    }

    // 2. step: Match similar elements and consider remainders in first array to be missing
    for (int i1 = 0; i1 < array1.length; i1++) {
      if (matches1[i1] == null) {
        Object e1 = array1[i1];
        int i2 = indexOfSimilar(e1, array2, matches2);
        if (i2 >= 0) {
          // store as changed
          matches1[i1] = matches2[i2] = new Match(i1, i2, CHANGED);
        } else {
          // store as removed
          matches1[i1] = new Match(i1, -1, REMOVED);
        }
      }
    }

    // 3. step: Consider remainders in second array to be added
    for (int i2 = 0; i2 < array2.length; i2++) {
      if (matches2[i2] == null) {
        // store as added
        matches2[i2] = new Match(-1, i2, ADDED);
      }
    }

    // 4. step: assemble comparison result
    ArrayComparisonResult result = new ArrayComparisonResult();
    int i1 = 0;
    int i2 = 0;
    while (i1 < array1.length || i2 < array2.length) {
      Match match1 = (i1 < matches1.length ? matches1[i1] : null);
      Match match2 = (i2 < matches2.length ? matches2[i2] : null);
      if (match1 != null && match1.type == REMOVED) {
        Object missingObject = array1[match1.i1];
        result.add(diffFactory.missing(missingObject, model.classifierOf(missingObject), locator1(array1, match1.i1)));
        match1.consume();
        i1 = nextUnconsumed(matches1, i1);
      } else if (match2 != null && match2.type == ADDED) {
        Object newObject = array2[match2.i2];
        result.add(diffFactory.unexpected(newObject, model.classifierOf(newObject), locator2(array2, match2.i2)));
        match2.consume();
        i2 = nextUnconsumed(matches2, i2);
      } else {
        assert match1 != null;
        Assert.notNull(match1, "match1");
        assert match2 != null;
        Assert.notNull(match2, "match2");
        switch (match1.type) {
          case CHANGED:
            Object changedObject = array1[match1.i1];
            String classifier = model.classifierOf(changedObject);
            if (match1.i1 != i1 || match1.i2 != i2) {
              result.add(diffFactory.moved(array1[match1.i1], classifier, locator1(array1, match1.i1), locator2(array2, match1.i2)));
            }
            result.add(
                diffFactory.different(array1[match1.i1], array2[match1.i2], classifier, locator1(array1, match1.i1), locator2(array2, match1.i2)));
            break;
          case IDENTICAL:
            if ((match1.i1 != match1.i2) && (match1.i1 != i1 || match1.i2 != i2)) {
              Object movedObject = array1[match1.i1];
              result.add(diffFactory.moved(movedObject, model.classifierOf(movedObject), locator1(array1, match1.i1), locator2(array1, match1.i2)));
            }
            break;
          default:
            throw new ProgrammerError();
        }
        match1.consume();
        matches2[match1.i2].consume();
        i1 = nextUnconsumed(matches1, i1);
        i2 = nextUnconsumed(matches2, i2);
      }
    }
    return result;
  }

  private static int nextUnconsumed(Match[] matches, int startIndex) {
    int index = startIndex;
    while (index < matches.length && matches[index].consumed) {
      index++;
    }
    return index;
  }

  private int indexOf(Object element, Object[] array, Match[] matches) {
    for (int i = 0; i < array.length; i++) {
      if (matches[i] == null && model.equal(element, array[i])) {
        return i;
      }
    }
    return -1;
  }

  private int indexOfSimilar(Object element, Object[] candidates, Match[] matches) {
    for (int i = 0; i < candidates.length; i++) {
      if (matches[i] == null && model.correspond(element, candidates[i])) {
        return i;
      }
    }
    return -1;
  }

  private String locator1(Object[] array, int index) {
    return parentLocator1 + model.subPath(array, index);
  }

  private String locator2(Object[] array, int index) {
    return parentLocator2 + model.subPath(array, index);
  }


  /**
   * The type Match.
   */
  static class Match {
    /**
     * The 1.
     */
    public int i1, /**
     * The 2.
     */
    i2;
    /**
     * The Type.
     */
    public int type;
    /**
     * The Consumed.
     */
    public boolean consumed;

    /**
     * Instantiates a new Match.
     *
     * @param i1   the 1
     * @param i2   the 2
     * @param type the type
     */
    public Match(int i1, int i2, int type) {
      this.i1 = i1;
      this.i2 = i2;
      this.type = type;
      this.consumed = false;
    }

    /**
     * Consume.
     */
    void consume() {
      this.consumed = true;
    }

    @Override
    public String toString() {
      return type + " " + (i1 >= 0 ? String.valueOf(i1) : "") + " " + (i2 >= 0 ? String.valueOf(i2) : "");
    }

  }

}
