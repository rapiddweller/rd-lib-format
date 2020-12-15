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
package com.rapiddweller.formats.fixedwidth;

/**
 * Helper class for testing.
 * Created: 14.03.2014 16:30:40
 * @since 0.7.2
 * @author Volker Bergmann
 */

public class FWPerson {
	
	private String name;
	private int age;
	private FWPet pet;
	
	public FWPerson(String name, int age, FWPet pet) {
		this.name = name;
		this.age = age;
		this.pet = pet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public FWPet getPet() {
		return pet;
	}

	public void setPet(FWPet pet) {
		this.pet = pet;
	}
	
}
