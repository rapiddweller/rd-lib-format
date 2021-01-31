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

import com.rapiddweller.common.Visitor;
import com.rapiddweller.common.collection.OrderedNameMap;

import java.util.Collection;
import java.util.Map;

/**
 * Represents a {@link ComplexType} which composes other ComplexTypes.
 * Created: 16.05.2014 19:59:51
 * @since 0.8.2
 * @author Volker Bergmann
 */

public class CompositeComplexType extends ComplexType {
	
	private final Map<String, ComplexMember> members;
	
	public CompositeComplexType(String name) {
		super(name);
		this.members = new OrderedNameMap<ComplexMember>();
	}
	
	public void addMember(ComplexMember member) {
		this.members.put(member.getName(), member);
	}
	
	public Collection<ComplexMember> getMembers() {
		return members.values();
	}
	
	@Override
	public void printContent(String indent) {
		System.out.println(indent + super.toString());
		indent += "  ";
		for (Attribute attribute : attributes.values())
			attribute.printContent(indent);
		for (ComplexMember member : members.values())
			member.printContent(indent);
	}

	@Override
	public void accept(Visitor<SchemaElement> visitor) {
		super.accept(visitor);
		for (ComplexMember type : members.values())
			type.accept(visitor);
	}

}
