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

import java.util.ArrayList;
import java.util.List;

import com.rapiddweller.commons.StringUtil;
import com.rapiddweller.commons.SystemInfo;

/**
 * Collects the differences between two data structures.
 * Created: 19.06.2014 15:58:59
 * @since 1.0.5
 * @author Volker Bergmann
 */

public class AggregateDiff {
	
	private final Object expected;
	private final Object actual;
	private final ComparisonSettings comparisonSettings;
	private final List<DiffDetail> details;
	
	public AggregateDiff(Object expected, Object actual, ComparisonSettings comparisonSettings) {
		this.expected = expected;
		this.actual = actual;
		this.comparisonSettings = comparisonSettings;
		this.details = new ArrayList<>();
	}
	
	public Object getExpected() {
		return expected;
	}
	
	public Object getActual() {
		return actual;
	}
	
	public ComparisonSettings getComparisonSettings() {
		return comparisonSettings;
	}
	
	public boolean isEmpty() {
		return details.isEmpty();
	}
	
	public List<DiffDetail> getDetails() {
		return details;
	}
	
	public int getDetailCount() {
		return details.size();
	}
	
	public DiffDetail getDetail(int index) {
		return this.details.get(index);
	}
	
	public void addDetail(DiffDetail diff) {
		this.details.add(diff);
	}

	@Override
	public String toString() {
		String LF = SystemInfo.getLineSeparator();
		StringBuilder builder = new StringBuilder("Aggregate diff:");
		if (details.isEmpty()) {
			builder.append(" Empty");
		} else {
			builder.append(LF);
			for (DiffDetail detail : details)
				builder.append("- ").append(StringUtil.normalizeSpace(detail.toString())).append(LF);
		}
		return builder.toString();
	}
	
}
