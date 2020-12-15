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

import java.util.Collection;
import java.util.Map;

import com.rapiddweller.commons.collection.OrderedNameMap;

/**
 * Parent class for schema elements that represent complex types.
 * Created: 16.05.2014 19:21:44
 * @since 0.8.2
 * @author Volker Bergmann
 */

public abstract class ComplexType extends NamedSchemaElement {
	
	protected Map<String, Attribute> attributes;
	
	public ComplexType(String name) {
		super(name);
		this.attributes = new OrderedNameMap<Attribute>();
	}
	
	public void addAttribute(Attribute attribute) {
		this.attributes.put(attribute.getName(), attribute);
	}
	
	public Collection<Attribute> getAttributes() {
		return attributes.values();
	}
	
	public abstract void printContent(String string);
	
}
