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

import com.rapiddweller.commons.HeavyweightIterable;
import com.rapiddweller.commons.HeavyweightIterator;
import com.rapiddweller.formats.DataSource;

/**
 * Wraps a {@link DataSource} with a Java {@link Iterable}.
 * Created: 25.01.2012 15:20:26
 * @param <E> the type of data to iterate
 * @since 0.6.6
 * @author Volker Bergmann
 */
public class JavaIterableFromDataSource<E> implements HeavyweightIterable<E> {
	
	protected DataSource<E> source;

	public JavaIterableFromDataSource(DataSource<E> source) {
		this.source = source;
	}

	@Override
	public HeavyweightIterator<E> iterator() {
		return new JavaIteratorFromDataIterator<E>(source.iterator());
	}

}
