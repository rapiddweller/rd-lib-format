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

import com.rapiddweller.commons.Converter;
import com.rapiddweller.commons.IOUtil;
import com.rapiddweller.formats.DataIterator;
import com.rapiddweller.formats.DataSource;

/**
 * {@link DataSource} proxy which applies a {@link Converter} to the source's data.
 * Created: 24.07.2011 10:06:31
 * @param <S> the type of data to iterate from the source
 * @param <T> the type of data to provide to the client
 * @since 0.6.0
 * @author Volker Bergmann
 */
public class ConvertingDataSource<S, T> extends DataSourceAdapter<S, T> {

	protected Converter<S, T> converter;
	
	public ConvertingDataSource(DataSource<S> source, Converter<S, T> converter) {
		super(source, converter.getTargetType());
		this.converter = converter;
	}

	@Override
	public Class<T> getType() {
		return converter.getTargetType();
	}

	@Override
	public DataIterator<T> iterator() {
		return new ConvertingDataIterator<S, T>(source.iterator(), converter);
	}
	
	@Override
	public void close() {
		if (converter instanceof Closeable)
			IOUtil.close((Closeable) converter);
		super.close();
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + '[' + source + " -> " + converter + ']';
	}

}
