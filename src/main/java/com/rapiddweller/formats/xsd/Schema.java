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

import java.util.Map;

import com.rapiddweller.commons.Named;
import com.rapiddweller.commons.Visitor;
import com.rapiddweller.commons.collection.OrderedNameMap;

/**
 * Represents an XML schema.
 * Created: 16.05.2014 18:30:35
 * @since 0.8.2
 * @author Volker Bergmann
 */

public class Schema extends SchemaElement implements Named {
	
	private Map<String, SimpleType> simpleTypes;
	private Map<String, ComplexType> complexTypes;
	private ComplexMember member;
	
	public Schema() {
		this.simpleTypes = new OrderedNameMap<SimpleType>();
		this.complexTypes = new OrderedNameMap<ComplexType>();
	}
	
	@Override
	public String getName() {
		return (member != null ? member.getName() : null);
	}

	public void addSimpleType(SimpleType simpleType) {
		this.simpleTypes.put(simpleType.getName(), simpleType);
	}

	public void addComplexType(ComplexType complexType) {
		this.complexTypes.put(complexType.getName(), complexType);
	}
	
	public ComplexType getComplexType(String name) {
		return complexTypes.get(name);
	}
	
	public ComplexMember getMember() {
		return member;
	}
	
	public void setMember(ComplexMember member) {
		this.member = member;
	}
	
	public void printContent() {
		System.out.println("Schema" + renderShortDocumentation());
		for (SimpleType simpleType : simpleTypes.values())
			simpleType.printContent("  ");
		for (ComplexType complexType : complexTypes.values())
			complexType.printContent("  ");
		member.printContent("  ");
	}
	
	@Override
	public void accept(Visitor<SchemaElement> visitor) {
		super.accept(visitor);
		for (SimpleType type : simpleTypes.values())
			type.accept(visitor);
		for (ComplexType type : complexTypes.values())
			type.accept(visitor);
		member.accept(visitor);
	}
	
}
