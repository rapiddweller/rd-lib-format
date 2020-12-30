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

import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.DataIterator;

/**
 * Uses a {@link DataIterator} which provides data rows and forwards its data in form of columns.
 * Created: 08.12.2011 13:45:37
 * @param <E> the type of data to iterate
 * @since 0.6.5
 * @author Volker Bergmann
 */
public class OrthogonalArrayIterator<E> implements DataIterator<E[]> {

	private final DataIterator<E[]> source;
	
	private List<E[]> rows;
	private int columnIndex;

	public OrthogonalArrayIterator(DataIterator<E[]> source) {
		this.source = source;
	}

	@Override
	public Class<E[]> getType() {
		return source.getType();
	}

	@Override
	public DataContainer<E[]> next(DataContainer<E[]> container) {
		beInitialized(container);
		if (rows.size() == 0 || columnIndex >= rows.get(0).length)
			return null;
		@SuppressWarnings("unchecked")
		E[] column = ArrayUtil.newInstance((Class<E>) source.getType().getComponentType(), rows.size());
		for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
			E[] row = rows.get(rowIndex);
			column[rowIndex] = (columnIndex < row.length ? row[columnIndex] : null);
		}
		columnIndex++;
		return container.setData(column);
	}

	@Override
	public void close() {
		// nothing to do
	}
	
	// private helpers -------------------------------------------------------------------------------------------------
	
	private void beInitialized(DataContainer<E[]> container) {
		if (rows == null) { // initialize on the first invocation
			rows = new ArrayList<E[]>();
			while (source.next(container) != null)
				rows.add(container.getData());
			columnIndex = 0;
		}
	}

}
