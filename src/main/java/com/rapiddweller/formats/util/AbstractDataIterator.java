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
package com.rapiddweller.formats.util;

import com.rapiddweller.formats.DataIterator;

/**
 * Abstract implementation of the {@link DataIterator} interface. 
 * It holds type info and provides an empty implementation of the close() method.
 * Created: 06.03.2012 21:55:57
 * @param <E> the type of data to iterate
 * @since 0.6.7
 * @author Volker Bergmann
 */
public abstract class AbstractDataIterator<E> implements DataIterator<E> {
	
	protected Class<E> type;
	
	public AbstractDataIterator(Class<E> type) {
		this.type = type;
	}

	@Override
	public Class<E> getType() {
		return type;
	}

	@Override
	public void close() {
		// empty default implementation
	}

}
