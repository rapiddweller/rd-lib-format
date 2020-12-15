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

import java.util.Arrays;

/**
 * Represents a sequence of regular expression factors.
 * 
 * @see Factor 
 */
public class Sequence implements RegexPart {
	
    /** The represented sequence of regular expression factors */
    private RegexPart[] factors;
    
    // constructors ----------------------------------------------------------------------------------------------------
    
    public Sequence(RegexPart ... factors) {
        this.factors = factors;
    }
    
    public RegexPart[] getFactors() {
        return factors;
    }
    
    
    // RegexPart interface implementation ------------------------------------------------------------------------------
    
	@Override
	public int minLength() {
		int min = 0;
		for (RegexPart part : factors)
			min += part.minLength();
		return min;
	}
	
	@Override
	public Integer maxLength() {
		int max = 0;
		for (RegexPart part : factors) {
			Integer partMaxLength = part.maxLength();
			if (partMaxLength == null) // if one sequence component is unlimited, then the whole sequence is unlimited
				return null;
			max += partMaxLength;
		}
		return max;
	}
    
    // java.lang.Object overrides --------------------------------------------------------------------------------------
	
    /** @see java.lang.Object#equals(Object) */
    @Override
    public boolean equals(Object o) {
        if (this == o)
        	return true;
        if (o == null || getClass() != o.getClass())
            return false;
        final Sequence that = (Sequence) o;
        return Arrays.equals(this.factors, that.factors);
    }

    /** @see java.lang.Object#equals(Object) */
    @Override
    public int hashCode() {
        return Arrays.hashCode(factors);
    }

    /** @see java.lang.Object#equals(Object) */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (Object factor : factors)
            buffer.append(factor);
        return buffer.toString();
    }

}
