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
package com.rapiddweller.formats;

import static org.junit.Assert.*;

import com.rapiddweller.formats.util.ListDataIterator;
import com.rapiddweller.formats.util.OrthogonalArrayIterator;
import org.junit.Test;

/**
 * Tests the {@link OrthogonalArrayIterator}.
 * Created: 08.12.2011 14:33:30
 * @since 0.6.5
 * @author Volker Bergmann
 */
public class OrthogonalArrayIteratorTest {

	@Test
	public void test() {
		DataIterator<Integer[]> source = new ListDataIterator<Integer[]>(Integer[].class, 
				new Integer[] { 1, 2 }, 
				new Integer[] { 3 });
		@SuppressWarnings("resource")
		DataIterator<Integer[]> iterator = new OrthogonalArrayIterator<Integer>(source);
		DataContainer<Integer[]> container = new DataContainer<Integer[]>();
		assertArrayEquals(new Integer[] { 1,    3 }, iterator.next(container).getData());
		assertArrayEquals(new Integer[] { 2, null }, iterator.next(container).getData());
		assertNull(iterator.next(container));
	}
	
}
