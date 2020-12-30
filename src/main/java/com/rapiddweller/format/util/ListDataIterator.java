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

import java.util.ArrayList;
import java.util.List;

import com.rapiddweller.common.CollectionUtil;
import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.DataIterator;

/**
 * {@link List}-based implementation of the {@link DataIterator} interface.
 * Created: 08.12.2011 14:36:08
 * @param <E> the type of data to iterate
 * @since 0.6.5
 * @author Volker Bergmann
 */
public class ListDataIterator<E> implements DataIterator<E> {
	
	private final Class<E> type;
	private final List<E> data;
	private int cursor;

	public ListDataIterator(Class<E> type, E... data) {
		this(type, CollectionUtil.toList(data));
	}

	public ListDataIterator(Class<E> type, List<E> data) {
		this.type = type;
		this.data = (data != null ? data : new ArrayList<E>());
		this.cursor = 0;
	}

	@Override
	public Class<E> getType() {
		return type;
	}

	@Override
	public DataContainer<E> next(DataContainer<E> container) {
		if (cursor >= data.size())
			return null;
		return container.setData(data.get(cursor++));
	}

	@Override
	public void close() {
		// nothing to do
	}

}
