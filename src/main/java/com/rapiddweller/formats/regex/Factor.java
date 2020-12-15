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

/**
 * Represents a regular expression part composed of a SubPattern and a Quantifier.
 * 
 * Created: 18.08.2006 19:11:31
 */
public class Factor implements RegexPart {
	
    /** The sub pattern */
    private RegexPart atom;
    
    /** The quantifier */
    private Quantifier quantifier;
    
    // constructors ----------------------------------------------------------------------------------------------------
    
    public Factor(RegexPart pattern) {
        this(pattern, 1, 1);
    }
    
    public Factor(RegexPart atom, int minQuantity, Integer maxQuantity) {
        this(atom, new Quantifier(minQuantity, maxQuantity));
    }
    
    public Factor(RegexPart atom, Quantifier quantifier) {
        this.atom = atom;
        this.quantifier = quantifier;
    }
    
    // properties ------------------------------------------------------------------------------------------------------
    
    /** Returns the atom. 
     * @return the atom */
    public RegexPart getAtom() {
        return atom;
    }
    
    /** Returns the represented quantifier. 
     * @return the quantifier */
    public Quantifier getQuantifier() {
        return quantifier;
    }
    
	@Override
	public int minLength() {
		return atom.minLength() * quantifier.getMin();
	}
	
	@Override
	public Integer maxLength() {
		Integer maxAtomLength = atom.maxLength();
		Integer maxQuantifier = quantifier.getMax();
		return (maxAtomLength != null && maxQuantifier != null ? maxAtomLength * maxQuantifier : null);
	}
    
    // java.lang.Object overrides --------------------------------------------------------------------------------------
	
    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        final Factor that = (Factor) o;
        return (this.atom.equals(that.atom) && 
        		that.quantifier.equals(that.quantifier));
    }
    
    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public int hashCode() {
        return 29 * atom.hashCode() + quantifier.hashCode();
    }
    
    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public String toString() {
        return atom.toString() + quantifier.toString();
    }
    
}
