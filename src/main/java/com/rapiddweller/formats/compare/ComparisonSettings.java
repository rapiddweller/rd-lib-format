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
package com.rapiddweller.formats.compare;

import java.util.HashSet;
import java.util.Set;

/**
 * Defines comparison settings: Which elements to ignore in comparisons 
 * and which differences to tolerate.
 * Created: 03.06.2014 15:39:25
 * @since 1.0.5
 * @author Volker Bergmann
 */

public class ComparisonSettings {

	protected ComparisonModel model;
	private Set<LocalDiffType> toleratedDiffs;

	public ComparisonSettings(ComparisonModel model) {
		this.model = model;
		this.toleratedDiffs = new HashSet<LocalDiffType>();
	}

	public ComparisonModel getModel() {
		return model;
	}
	
	public void addKeyExpression(String locator, String expression) {
		model.addKeyExpression(locator, expression);
	}

	public Set<LocalDiffType> getToleratedDiffs() {
		return toleratedDiffs;
	}
	
	public void addToleratedDiff(LocalDiffType toleratedDiff) {
		this.toleratedDiffs.add(toleratedDiff);
	}

	public void tolerateDifferentAt(String xPath) {
		this.toleratedDiffs.add(new LocalDiffType(DiffDetailType.DIFFERENT, xPath));
	}

	public void tolerateMissingAt(String xPath) {
		this.toleratedDiffs.add(new LocalDiffType(DiffDetailType.MISSING, xPath));
	}

	public void tolerateUnexpectedAt(String xPath) {
		this.toleratedDiffs.add(new LocalDiffType(DiffDetailType.UNEXPECTED, xPath));
	}

	public void tolerateMovedAt(String xPath) {
		this.toleratedDiffs.add(new LocalDiffType(DiffDetailType.MOVED, xPath));
	}

	public void tolerateAnyDiffAt(String xPath) {
		this.toleratedDiffs.add(new LocalDiffType(null, xPath));
	}

	public void tolerateGenericDiff(DiffDetailType type, String xPath) {
		this.toleratedDiffs.add(new LocalDiffType(type, xPath));
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[model=" + model + ", toleratedDiffs=" + toleratedDiffs + "]";
	}

}
