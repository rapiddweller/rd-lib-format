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

import java.util.HashSet;
import java.util.Set;

/**
 * Defines comparison settings: Which elements to ignore in comparisons
 * and which differences to tolerate.
 * Created: 03.06.2014 15:39:25
 *
 * @author Volker Bergmann
 * @since 1.0.5
 */
public class ComparisonSettings {

  /**
   * The Model.
   */
  protected ComparisonModel model;
  private final Set<LocalDiffType> toleratedDiffs;

  /**
   * Instantiates a new Comparison settings.
   *
   * @param model the model
   */
  public ComparisonSettings(ComparisonModel model) {
    this.model = model;
    this.toleratedDiffs = new HashSet<>();
  }

  /**
   * Gets model.
   *
   * @return the model
   */
  public ComparisonModel getModel() {
    return model;
  }

  /**
   * Add key expression.
   *
   * @param locator    the locator
   * @param expression the expression
   */
  public void addKeyExpression(String locator, String expression) {
    model.addKeyExpression(locator, expression);
  }

  /**
   * Gets tolerated diffs.
   *
   * @return the tolerated diffs
   */
  public Set<LocalDiffType> getToleratedDiffs() {
    return toleratedDiffs;
  }

  /**
   * Add tolerated diff.
   *
   * @param toleratedDiff the tolerated diff
   */
  public void addToleratedDiff(LocalDiffType toleratedDiff) {
    this.toleratedDiffs.add(toleratedDiff);
  }

  /**
   * Tolerate different at.
   *
   * @param xPath the x path
   */
  public void tolerateDifferentAt(String xPath) {
    this.toleratedDiffs.add(new LocalDiffType(DiffDetailType.DIFFERENT, xPath));
  }

  /**
   * Tolerate missing at.
   *
   * @param xPath the x path
   */
  public void tolerateMissingAt(String xPath) {
    this.toleratedDiffs.add(new LocalDiffType(DiffDetailType.MISSING, xPath));
  }

  /**
   * Tolerate unexpected at.
   *
   * @param xPath the x path
   */
  public void tolerateUnexpectedAt(String xPath) {
    this.toleratedDiffs.add(new LocalDiffType(DiffDetailType.UNEXPECTED, xPath));
  }

  /**
   * Tolerate moved at.
   *
   * @param xPath the x path
   */
  public void tolerateMovedAt(String xPath) {
    this.toleratedDiffs.add(new LocalDiffType(DiffDetailType.MOVED, xPath));
  }

  /**
   * Tolerate any diff at.
   *
   * @param xPath the x path
   */
  public void tolerateAnyDiffAt(String xPath) {
    this.toleratedDiffs.add(new LocalDiffType(null, xPath));
  }

  /**
   * Tolerate generic diff.
   *
   * @param type  the type
   * @param xPath the x path
   */
  public void tolerateGenericDiff(DiffDetailType type, String xPath) {
    this.toleratedDiffs.add(new LocalDiffType(type, xPath));
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[model=" + model + ", toleratedDiffs=" + toleratedDiffs + "]";
  }

}
