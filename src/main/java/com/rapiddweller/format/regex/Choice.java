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
package com.rapiddweller.format.regex;

import java.util.Arrays;
import java.util.List;

/**
 * Represents an alternative expression part of a regular expression, e.g. '(yes|no)'.
 * 
 * Created: 17.09.2006 16:14:14
 */
public class Choice implements RegexPart {
	
    /** The alternatives */
    private final RegexPart[] alternatives;
    
    
    // constructors ----------------------------------------------------------------------------------------------------
    
    /** Constructor that takes a list of alternative patterns 
     * @param alternatives the alternatives */
    public Choice(List<RegexPart> alternatives) {
    	RegexPart[] ra = new RegexPart[alternatives.size()];
        this.alternatives = alternatives.toArray(ra);
    }
    
    /** Constructor that takes an array of alternative patterns 
     * @param alternatives the alternatives */
    public Choice(RegexPart... alternatives) {
        this.alternatives = alternatives;
    }
    
    
    // properties ------------------------------------------------------------------------------------------------------
    
    /** Returns the alternative patterns 
     * @return the alternatives */
    public RegexPart[] getAlternatives() {
        return alternatives;
    }
    
    
    // RegexPart interface implementation ------------------------------------------------------------------------------
    
	@Override
	public int minLength() {
		if (this.alternatives.length == 0)
			return 0;
		int min = alternatives[0].minLength();
		for (int i = alternatives.length - 1; i >= 1; i--)
			min = Math.min(min, alternatives[i].minLength());
		return min;
	}

	@Override
	public Integer maxLength() {
		if (this.alternatives.length == 0)
			return 0;
		int max = 0;
		for (RegexPart candidate : alternatives) {
			Integer partMaxLength = candidate.maxLength();
			if (partMaxLength == null)
				return null; // if any option is unlimited, then the whole choice is unlimited
			if (partMaxLength > max)
				max = partMaxLength;
		}
		return max;
	}
	
	
    // java.lang.Object overrides --------------------------------------------------------------------------------------
    
    /** @see java.lang.Object#equals(Object) */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        return Arrays.equals(alternatives, ((Choice)o).alternatives);
    }
    
    /** @see java.lang.Object#hashCode() */
    @Override
    public int hashCode() {
        return Arrays.hashCode(alternatives);
    }
    
    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder("(");
        for (int i = 0; i < alternatives.length; i++) {
            if (i > 0)
                buffer.append('|');
            buffer.append(alternatives[i]);
        }
        buffer.append(')');
        return buffer.toString();
    }
    
}
