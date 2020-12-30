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

import java.util.ArrayList;
import java.util.List;

/**
 * Provides the result of a comparison.
 * Created: 20.11.2013 17:54:31
 * @since 1.0.5
 * @author Volker Bergmann
 */

public class ArrayComparisonResult {
	
	private final List<DiffDetail> diffs;
	
	public ArrayComparisonResult() {
		this.diffs = new ArrayList<DiffDetail>();
	}
	
	public void add(DiffDetail diff) {
		this.diffs.add(diff);
	}

	public boolean identical() {
		return (diffs.size() == 0);
	}

	public List<DiffDetail> getDiffs() {
		return diffs;
	}

}
