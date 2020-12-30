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

import com.rapiddweller.format.DataIterator;
import com.rapiddweller.format.DataSource;

/**
 * Proxy for a {@link DataSource}.
 * Created: 24.07.2011 10:02:59
 * @param <E> the type of data to iterate
 * @since 0.6.0
 * @author Volker Bergmann
 */
public class DataSourceProxy<E> extends DataSourceAdapter<E, E> {

	public DataSourceProxy(DataSource<E> source) {
		super(source, source.getType());
	}

	@Override
	public DataIterator<E> iterator() {
		return source.iterator();
	}

}
