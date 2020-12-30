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
package com.rapiddweller.format.xsd;

/**
 * Represents a plain {@link ComplexType} of an XML schema.
 * Created: 16.05.2014 20:01:00
 * @since 0.8.2
 * @author Volker Bergmann
 */

public class PlainComplexType extends ComplexType {
	
	private Integer minLength;
	private Integer maxLength;

	public PlainComplexType(String name) {
		super(name);
	}
	
	public Integer getMinLength() {
		return minLength;
	}
	
	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}
	
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	@Override
	public void printContent(String indent) {
		System.out.println(indent + super.toString());
		indent += "  ";
		for (Attribute attribute : attributes.values())
			attribute.printContent(indent);
	}

}
