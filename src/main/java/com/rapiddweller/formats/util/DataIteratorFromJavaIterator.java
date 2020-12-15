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
import java.util.Iterator;

import com.rapiddweller.commons.IOUtil;
import com.rapiddweller.formats.DataContainer;
import com.rapiddweller.formats.DataIterator;

/**
 * Provides {@link DataIterator}-style access to a Java {@link Iterator}.
 * Created: 24.07.2011 11:09:16
 * @param <E> the type of data to iterate
 * @since 0.6.0
 * @author Volker Bergmann
 */
public class DataIteratorFromJavaIterator<E> implements DataIterator<E> {

	protected Iterator<E> source;
	protected Class<E> type;
	
	public DataIteratorFromJavaIterator(Iterator<E> source, Class<E> type) {
		this.source = source;
		this.type = type;
	}

	@Override
	public Class<E> getType() {
		return type;
	}

	@Override
	public DataContainer<E> next(DataContainer<E> wrapper) {
		return (source.hasNext() ? wrapper.setData(source.next()) : null);
	}

	@Override
	public void close() {
		if (source instanceof Closeable)
			IOUtil.close((Closeable) source);
	}

}
