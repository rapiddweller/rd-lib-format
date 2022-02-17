/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.format.array;

import com.rapiddweller.common.ThreadAware;
import com.rapiddweller.format.DataIterator;
import com.rapiddweller.format.util.AbstractDataSource;

/**
 * Thread safe {@link com.rapiddweller.format.DataSource} which creates
 * thread safe {@link DataIterator}s for an array.<br/><br/>
 * Created: 17.02.2022 13:24:10
 * @author Volker Bergmann
 * @since 1.1.5
 */
public class ArrayDataSource<E> extends AbstractDataSource<E> implements ThreadAware {

	private final E[] array;

	public ArrayDataSource(E[] array, Class<E> type) {
		super(type);
		this.array = array;
	}

	@Override
	public boolean isThreadSafe() {
		return true;
	}

	@Override
	public boolean isParallelizable() {
		return false;
	}

	@Override
	public DataIterator<E> iterator() {
		return new ArrayDataIterator<>(array);
	}

}
