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
package com.rapiddweller.formats.regex;

import java.util.ArrayList;
import java.util.List;

import com.rapiddweller.commons.CharSet;

/**
 * Represents a custom character class with inclusions and exclusions.
 * 
 * Created at 20.09.2009 11:18:28
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class CustomCharClass extends RegexCharClass {
	
	private List<RegexCharClass> inclusions;
	private List<RegexCharClass> exclusions;
	
	// constructors ----------------------------------------------------------------------------------------------------
	
	public CustomCharClass() {
		this(null);
	}
	
    public CustomCharClass(List<? extends RegexCharClass> includedSets) {
	    this(includedSets, null);
    }
    
    public CustomCharClass(List<? extends RegexCharClass> includedSets, List<? extends RegexCharClass> excludedSets) {
	    this.inclusions = new ArrayList<RegexCharClass>();
	    if (includedSets != null)
	    	this.inclusions.addAll(includedSets);
	    this.exclusions = new ArrayList<RegexCharClass>();
	    if (excludedSets != null)
	    	this.exclusions.addAll(excludedSets);
    }
    
    
    // interface -------------------------------------------------------------------------------------------------------
    
	@Override
	public CharSet getCharSet() {
		CharSet set = new CharSet();
		for (RegexCharClass inclusion : inclusions)
			set.addAll(inclusion.getCharSet().getSet());
		for (RegexCharClass exclusion : exclusions)
			set.removeAll(exclusion.getCharSet().getSet());
		return set;
	}
	
	public void addInclusion(RegexCharClass set) {
		this.inclusions.add(set);
	}
	
	public void addExclusions(RegexCharClass set) {
		this.exclusions.add(set);
	}
	
	public boolean hasInclusions() {
		return !inclusions.isEmpty();
	}
	
	
	// private helpers -------------------------------------------------------------------------------------------------
	
    private static void appendToString(List<?> objects, StringBuilder builder) {
	    for (Object object : objects)
	    	builder.append(object);
    }
    
    
	// java.lang.Object overrides --------------------------------------------------------------------------------------
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("[");
		appendToString(inclusions, result);
		if (!exclusions.isEmpty()) {
			result.append('^');
			appendToString(exclusions, result);
		}
		return result.append(']').toString();
	}
	
	@Override
	public int hashCode() {
		return 31 * exclusions.hashCode() + inclusions.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		CustomCharClass that = (CustomCharClass) obj;
		return (this.inclusions.equals(that.inclusions) && 
				this.exclusions.equals(that.exclusions));
	}

}