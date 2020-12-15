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
package com.rapiddweller.formats.xsd;

import com.rapiddweller.commons.Visitor;

/**
 * Represents a member of a {@link ComplexType}, having a name, 
 * a type and a permitted cardinality range.
 * Created: 16.05.2014 20:15:57
 * @since 0.8.2
 * @author Volker Bergmann
 */

public class ComplexMember extends NamedSchemaElement {
	
	private ComplexType type;
	private Integer minCardinality;
	private Integer maxCardinality;
	
	public ComplexMember(String name, ComplexType complexType) {
		super(name);
		this.type = complexType;
	}
	
	public ComplexType getType() {
		return type;
	}
	
	public void setType(ComplexType complexType) {
		this.type = complexType;
	}
	
	public Integer getMinCardinality() {
		return minCardinality;
	}

	public void setMinCardinality(Integer minCardinality) {
		this.minCardinality = minCardinality;
	}

	public Integer getMaxCardinality() {
		return maxCardinality;
	}

	public void setMaxCardinality(Integer maxCardinality) {
		this.maxCardinality = maxCardinality;
	}

	public void printContent(String indent) {
		System.out.println(indent + name + renderShortDocumentation() + ":");
		type.printContent(indent + "  ");
	}

	@Override
	public void accept(Visitor<SchemaElement> visitor) {
		super.accept(visitor);
		type.accept(visitor);
	}

}
