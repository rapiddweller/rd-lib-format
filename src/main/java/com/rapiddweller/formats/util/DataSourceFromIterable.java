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

import java.io.Closeable;

import com.rapiddweller.commons.IOUtil;
import com.rapiddweller.formats.DataIterator;
import com.rapiddweller.formats.DataSource;

/**
 * Provides {@link DataSource}-style access to a Java-SDK-{@link Iterable}.
 * Created: 24.07.2011 11:07:04
 * @param <E> the type of data to iterate
 * @since 0.6.0
 * @author Volker Bergmann
 */
public class DataSourceFromIterable<E> extends AbstractDataSource<E> {
	
	protected Iterable<E> source;

	public DataSourceFromIterable(Iterable<E> source, Class<E> type) {
		super(type);
		this.source = source;
	}

	@Override
	public DataIterator<E> iterator() {
		return new DataIteratorFromJavaIterator<E>(source.iterator(), type);
	}

	@Override
	public void close() {
		if (source instanceof Closeable)
			IOUtil.close((Closeable) source);
		super.close();
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + source + "]";
	}
	
}
