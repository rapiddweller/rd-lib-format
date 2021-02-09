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

import com.rapiddweller.common.Converter;
import com.rapiddweller.common.NullSafeComparator;

/**
 * Represents a difference between the state of two objects.
 * Created: 21.11.2013 11:29:35
 *
 * @author Volker Bergmann
 * @since 1.0.5
 */
public class DiffDetail {

  /**
   * The Expected.
   */
  protected final Object expected;
  /**
   * The Actual.
   */
  protected final Object actual;
  /**
   * The Object classifier.
   */
  protected final String objectClassifier;
  /**
   * The Type.
   */
  protected final DiffDetailType type;
  /**
   * The Locator of expected.
   */
  protected final String locatorOfExpected;
  /**
   * The Locator of actual.
   */
  protected final String locatorOfActual;
  /**
   * The Formatter.
   */
  protected final Converter<Object, String> formatter;

  /**
   * Instantiates a new Diff detail.
   *
   * @param expected         the expected
   * @param actual           the actual
   * @param objectClassifier the object classifier
   * @param type             the type
   * @param formatter        the formatter
   */
  public DiffDetail(Object expected, Object actual, String objectClassifier, DiffDetailType type, Converter<Object, String> formatter) {
    this(expected, actual, objectClassifier, type, null, null, formatter);
  }

  /**
   * Instantiates a new Diff detail.
   *
   * @param expected          the expected
   * @param actual            the actual
   * @param objectClassifier  the object classifier
   * @param type              the type
   * @param locatorOfExpected the locator of expected
   * @param locatorOfActual   the locator of actual
   * @param formatter         the formatter
   */
  public DiffDetail(Object expected, Object actual, String objectClassifier, DiffDetailType type, String locatorOfExpected, String locatorOfActual,
                    Converter<Object, String> formatter) {
    this.expected = expected;
    this.actual = actual;
    this.objectClassifier = objectClassifier;
    this.type = type;
    this.locatorOfExpected = locatorOfExpected;
    this.locatorOfActual = locatorOfActual;
    this.formatter = formatter;
  }

  /**
   * Gets expected.
   *
   * @return the expected
   */
  public Object getExpected() {
    return expected;
  }

  /**
   * Gets actual.
   *
   * @return the actual
   */
  public Object getActual() {
    return actual;
  }

  /**
   * Gets object classifier.
   *
   * @return the object classifier
   */
  public String getObjectClassifier() {
    return objectClassifier;
  }

  /**
   * Gets type.
   *
   * @return the type
   */
  public DiffDetailType getType() {
    return type;
  }

  /**
   * Gets locator of expected.
   *
   * @return the locator of expected
   */
  public String getLocatorOfExpected() {
    return locatorOfExpected;
  }

  /**
   * Gets locator of actual.
   *
   * @return the locator of actual
   */
  public String getLocatorOfActual() {
    return locatorOfActual;
  }

  /**
   * Gets formatter.
   *
   * @return the formatter
   */
  public Converter<Object, String> getFormatter() {
    return formatter;
  }


  // java.lang.Object overrides --------------------------------------------------------------------------------------

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((locatorOfExpected == null) ? 0 : locatorOfExpected.hashCode());
    result = prime * result + ((locatorOfActual == null) ? 0 : locatorOfActual.hashCode());
    result = prime * result + ((objectClassifier == null) ? 0 : objectClassifier.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    result = prime * result + ((expected == null) ? 0 : expected.hashCode());
    result = prime * result + ((actual == null) ? 0 : actual.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    DiffDetail that = (DiffDetail) obj;
    return (NullSafeComparator.equals(this.expected, that.expected) &&
        NullSafeComparator.equals(this.actual, that.actual) &&
        NullSafeComparator.equals(this.type, that.type) &&
        NullSafeComparator.equals(this.locatorOfExpected, that.locatorOfExpected) &&
        NullSafeComparator.equals(this.locatorOfActual, that.locatorOfActual));
  }

  @Override
  public String toString() {
    switch (type) {
      case DIFFERENT:
        return formatDifferent();
      case MISSING:
        return formatMissing();
      case UNEXPECTED:
        return formatUnexpected();
      case MOVED:
        return formatMoved();
      default:
        return formatGenericDiff();
    }
  }


  // private helpers -------------------------------------------------------------------------------------------------

  private String formatDifferent() {
    if (NullSafeComparator.equals(locatorOfExpected, locatorOfActual)) {
      return "Different " + objectClassifier + ": expected " + formatObject(expected) +
          " but found " + formatObject(actual) + (locatorOfActual != null ? " at " + locatorOfActual : "");
    } else {
      return "Different " + objectClassifier + ": expected " + formatObject(expected) +
          (locatorOfExpected != null ? " at " + locatorOfExpected : "") +
          " but found " + formatObject(actual) + (locatorOfActual != null ? " at " + locatorOfActual : "");
    }
  }

  private String formatMissing() {
    return "Missing " + objectClassifier + " " + formatObject(expected) + " at " + locatorOfExpected;
  }

  private String formatUnexpected() {
    return "Unexpected " + objectClassifier + " " + formatObject(actual) + " found at " + locatorOfActual;
  }

  private String formatMoved() {
    return "Moved " + objectClassifier + " " + formatObject(expected) + " from " + locatorOfExpected + " to " + locatorOfActual;
  }

  private String formatGenericDiff() {
    return type + " " + objectClassifier + ", expected " + formatObject(expected) + ", found " + actual + " " + locatorOfExpected + " " +
        locatorOfActual;
  }

  private String formatObject(Object value) {
    return formatter.convert(value);
  }

}
