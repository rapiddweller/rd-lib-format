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

/**
 * Represents a quantifier of a regular expression, e.g. '{1,3}'.
 * 
 */
public class Quantifier {

    /** minimum length */
    private final int min;

    /** maximum length */
    private final Integer max;

    /** Complete constructor that takes values for all attributes 
     * @param min the minimum quantity
     * @param max the maximum quantity */
    public Quantifier(int min, Integer max) {
        this.min = min;
        this.max = max;
    }

    /** Returns the minimum quantity. 
     * @return the minimum quantity */
    public int getMin() {
        return min;
    }

    /** Returns the maximum quantity.
     * @return the maximum quantity */
    public Integer getMax() {
        return max;
    }

    // java.lang.Object overrides --------------------------------------------------------------------------------------

    /**
     * Creates a String of regular expression format, e.g. '{2,}', '{1,3}', '+', '?'
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public String toString() {
        if (max != null && min == max)
            return (min == 1 ? "" : '{' + String.valueOf(min) + '}');
        else if (min == 0) {
            if (max == null)
            	return "*";
            else if (max == 1)
            	return "?";
            else 
            	return "{0," + max + '}';
        } else if (min == 1)
            return (max == null ? "+" : "{1," + max + "}");
        else
            return (max == null ? "{" + min + ",}" : "{1," + "}");
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        final Quantifier that = (Quantifier) o;
        if (max != that.max)
            return false;
        return min == that.min;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public int hashCode() {
        return 29 * min + (max != null ? max : 0);
    }
    
}
