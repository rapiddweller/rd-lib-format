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
package com.rapiddweller.format.xls;

import java.util.Iterator;

import com.rapiddweller.format.DataIterator;
import com.rapiddweller.format.util.AbstractDataSource;
import com.rapiddweller.format.xls.XLSLineIterator;

/**
 * {@link Iterable} implementation which creates {@link Iterator}s 
 * that provide lines of XLS files as array objects.
 * Created: 19.07.2011 08:36:18
 * @since 0.6.5
 * @author Volker Bergmann
 */
public class XLSLineSource extends AbstractDataSource<Object[]> {
	
	private final String uri;
	private final String sheetName;
	private final boolean formatted;

	public XLSLineSource(String uri) {
		this(uri, null, false);
	}

	public XLSLineSource(String uri, String sheetName, boolean formatted) {
		super(Object[].class);
		this.uri = uri;
		this.sheetName = sheetName;
		this.formatted = formatted;
	}

	@Override
	public DataIterator<Object[]> iterator() {
		try {
			return new XLSLineIterator(uri, sheetName, false, formatted, null);
		} catch (Exception e) {
			throw new RuntimeException("Unable to create iterator for URI " + uri, e);
		}
	}

}
