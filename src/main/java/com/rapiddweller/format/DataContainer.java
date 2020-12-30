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
package com.rapiddweller.format;

/**
 * Container object for data items.
 * Created: 24.07.2011 14:40:21
 * @param <E> the type of the objects to wrap
 * @since 0.6.0
 * @author Volker Bergmann
 */
public class DataContainer<E> {

	protected E data;

	public DataContainer() {
		this(null);
	}
	
	public DataContainer(E data) {
		this.data = data;
	}
	
	public E getData() {
		return data;
	}
	
	public DataContainer<E> setData(E data) {
		this.data = data;
		return this;
	}
	
	@Override
	public String toString() {
		return String.valueOf(data);
	}
	
}
