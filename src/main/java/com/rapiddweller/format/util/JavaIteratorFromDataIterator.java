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
package com.rapiddweller.format.util;

import com.rapiddweller.common.HeavyweightIterator;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.DataIterator;

import java.util.Iterator;

/**
 * Adapter class that provides Java-SDK-style {@link Iterator} access to a {@link DataIterator}.
 * Created: 03.08.2011 19:04:58
 * @param <E> the type of data to iterate
 * @since 0.6.0
 * @author Volker Bergmann
 */
public class JavaIteratorFromDataIterator<E> implements HeavyweightIterator<E> {
	
	private DataIterator<E> source;
	private DataContainer<E> next;
	private boolean initialized;
	
	public JavaIteratorFromDataIterator(DataIterator<E> source) {
		this.source = source;
		this.next = new DataContainer<E>();
		this.initialized = false;
	}

	@Override
	public boolean hasNext() {
		if (!initialized) {
			next = source.next(next);
			initialized = true;
		}
		return (next != null);
	}

	@Override
	public E next() {
		if (!hasNext())
			throw new IllegalStateException("Not available. Check hasNext() before calling next()");
		E result = next.getData();
		next = source.next(next);
		if (next == null)
			close();
		return result;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Iterator<E>.remove() is not supported");
	}

	@Override
	public void close() {
		IOUtil.close(source);
		source = null;
	}
	
	@Override
	public String toString() {
		return source.toString();
	}
	
}
