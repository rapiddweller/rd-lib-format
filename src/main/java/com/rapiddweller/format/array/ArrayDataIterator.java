/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.format.array;

import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.util.AbstractDataIterator;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread safe {@link com.rapiddweller.format.DataIterator} implementation which iterates through an array.<br/><br/>
 * Created: 17.02.2022 13:27:42
 * @author Volker Bergmann
 * @since 1.1.5
 */
public class ArrayDataIterator<E> extends AbstractDataIterator<E> {

	private final E[] array;
	private final AtomicInteger cursor;

	public ArrayDataIterator(E[] array) {
		super(ArrayUtil.componentType(array));
		this.array = array;
		this.cursor = new AtomicInteger();
	}

	@Override
	public DataContainer<E> next(DataContainer<E> container) throws ConfigurationError {
		int index = cursor.getAndIncrement();
		return (index < array.length ? container.setData(this.array[index]) : null);
	}

}
